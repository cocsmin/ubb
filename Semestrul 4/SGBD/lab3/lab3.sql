USE GLOVO;
GO

-- 1) Funcții de validare

-- Validarea numărului de card: doar cifre, 8–16 caractere
CREATE OR ALTER FUNCTION uf_ValidateCardNumber(@nr_card VARCHAR(50))
RETURNS BIT
AS
BEGIN
    RETURN CASE 
      WHEN @nr_card NOT LIKE '%[^0-9]%' 
           AND LEN(@nr_card) BETWEEN 8 AND 16 
      THEN 1 ELSE 0 
    END;
END
GO

-- Validarea tipului de card: VISA sau MASTERCARD
CREATE OR ALTER FUNCTION uf_ValidateCardType(@tip_card VARCHAR(50))
RETURNS BIT
AS
BEGIN
    RETURN CASE 
      WHEN UPPER(@tip_card) IN ('VISA','MASTERCARD') 
      THEN 1 ELSE 0 
    END;
END
GO

-- Validarea datei de expirare: > data curentă
CREATE OR ALTER FUNCTION uf_ValidateExpiryDate(@exp DATE)
RETURNS BIT
AS
BEGIN
    RETURN CASE 
      WHEN @exp > CONVERT(date, GETDATE()) 
      THEN 1 ELSE 0 
    END;
END
GO

-- 2) Tabel de log
IF NOT EXISTS (SELECT 1 FROM sys.objects WHERE name = 'LogTable_GLOVO' AND type = 'U')
BEGIN
    CREATE TABLE LogTable_GLOVO (
        Lid INT IDENTITY PRIMARY KEY,
        TypeOperation VARCHAR(50),
        TableOperation VARCHAR(50),
        ExecutionDate DATETIME DEFAULT GETDATE()
    );
END
GO

-- 3) PROCEDURĂ cu TOTAL ROLLBACK
CREATE OR ALTER PROCEDURE AddClientCard_TotalRollback
    @email_client VARCHAR(50),
    @nr_card      VARCHAR(50),
    @tip_card     VARCHAR(50),
    @data_exp     DATE
AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        -- Rezolvare ID_CLIENT
        DECLARE @id_cl INT;
        SELECT @id_cl = ID_CL FROM CLIENT WHERE EMAIL = @email_client;
        IF @id_cl IS NULL
            THROW 50020, 'Client inexistent!', 1;

        -- Validări
        IF dbo.uf_ValidateCardNumber(@nr_card) = 0
            THROW 50021, 'Număr card invalid!', 1;
        IF dbo.uf_ValidateCardType(@tip_card) = 0
            THROW 50022, 'Tip card invalid!', 1;
        IF dbo.uf_ValidateExpiryDate(@data_exp) = 0
            THROW 50023, 'Data expirării invalidă!', 1;

        -- Inserare CARD
        INSERT INTO CARD(NUMAR_CARD, TIP_CARD, DATA_EXP)
        VALUES (@nr_card, @tip_card, @data_exp);
        DECLARE @new_card_id INT = SCOPE_IDENTITY();

        -- Inserare în relația M-N PLATA
        INSERT INTO PLATA(ID_CL, ID_CARD)
        VALUES (@id_cl, @new_card_id);

        -- Log
        INSERT INTO LogTable_GLOVO(TypeOperation, TableOperation)
        VALUES ('Insert', 'PLATA');

        COMMIT;
        PRINT 'Inserare completă (TOTAL ROLLBACK) realizată cu succes.';
    END TRY
    BEGIN CATCH
        ROLLBACK;
        PRINT 'Eroare: ' + ERROR_MESSAGE();
    END CATCH
END
GO

-- 4) PROCEDURĂ cu PARTIAL ROLLBACK
CREATE OR ALTER PROCEDURE AddClientCard_PartialRollback
    @email_client VARCHAR(50),
    @nr_card      VARCHAR(50),
    @tip_card     VARCHAR(50),
    @data_exp     DATE
AS
BEGIN
    DECLARE @new_card_id INT = NULL;

    -- Rezolvare ID_CLIENT
    DECLARE @id_cl INT;
    SELECT @id_cl = ID_CL FROM CLIENT WHERE EMAIL = @email_client;
    IF @id_cl IS NULL
    BEGIN
        PRINT 'Client inexistent!';
        RETURN;
    END

    -- Validări carte (fără rollback global)
    IF dbo.uf_ValidateCardNumber(@nr_card) = 0
    BEGIN
        PRINT 'Număr card invalid!';
        RETURN;
    END
    IF dbo.uf_ValidateCardType(@tip_card) = 0
    BEGIN
        PRINT 'Tip card invalid!';
        RETURN;
    END
    IF dbo.uf_ValidateExpiryDate(@data_exp) = 0
    BEGIN
        PRINT 'Data expirării invalidă!';
        RETURN;
    END

    -- Inserare CARD
    BEGIN TRY
        BEGIN TRAN;
        INSERT INTO CARD(NUMAR_CARD, TIP_CARD, DATA_EXP)
        VALUES (@nr_card, @tip_card, @data_exp);
        SET @new_card_id = SCOPE_IDENTITY();
        COMMIT;

        INSERT INTO LogTable_GLOVO(TypeOperation, TableOperation)
        VALUES ('Insert', 'CARD');
    END TRY
    BEGIN CATCH
        ROLLBACK;
        PRINT 'Eroare la inserarea cardului: ' + ERROR_MESSAGE();
        RETURN;
    END CATCH

    -- Asociere CARD ↔ CLIENT
    BEGIN TRY
        BEGIN TRAN;
        INSERT INTO PLATA(ID_CL, ID_CARD)
        VALUES (@id_cl, @new_card_id);
        COMMIT;

        INSERT INTO LogTable_GLOVO(TypeOperation, TableOperation)
        VALUES ('Insert', 'PLATA');
        PRINT 'Asociere completă (PARTIAL ROLLBACK) realizată cu succes.';
    END TRY
    BEGIN CATCH
        ROLLBACK;
        PRINT 'Eroare la asocierea cardului cu clientul: ' + ERROR_MESSAGE();
    END CATCH
END
GO

-- 5) TESTARE

-- 5.1 Succes TOTAL ROLLBACK
EXEC AddClientCard_TotalRollback
    @email_client = 'csecrier@yahoo.com',
    @nr_card      = '9999000011112222',
    @tip_card     = 'VISA',
    @data_exp     = '2026-12-31';

-- 5.2 Eroare număr card (TOTAL ROLLBACK)
EXEC AddClientCard_TotalRollback
    @email_client = 'alevaleracz@yahoo.com',
    @nr_card      = 'ABCD1234',
    @tip_card     = 'VISA',
    @data_exp     = '2026-01-01';

-- 5.3 Client inexistent (TOTAL ROLLBACK)
EXEC AddClientCard_TotalRollback
    @email_client = 'nonexistent@glovo.com',
    @nr_card      = '12345678',
    @tip_card     = 'MASTERCARD',
    @data_exp     = '2025-08-01';

-- 5.4 Succes PARTIAL ROLLBACK
EXEC AddClientCard_PartialRollback
    @email_client = 'gigiregele@yahoo.com',
    @nr_card      = '1234432156788765',
    @tip_card     = 'MASTERCARD',
    @data_exp     = '2027-05-30';

-- 5.5 Data expirării trecută (PARTIAL ROLLBACK)
EXEC AddClientCard_PartialRollback
    @email_client = 'jurcanufur@gmail.com',
    @nr_card      = '8765432187654321',
    @tip_card     = 'VISA',
    @data_exp     = '2020-01-01';

-- 6) Verificări finale
SELECT * FROM CARD;
SELECT * FROM PLATA;
SELECT * 
FROM LogTable_GLOVO
ORDER BY ExecutionDate DESC;
