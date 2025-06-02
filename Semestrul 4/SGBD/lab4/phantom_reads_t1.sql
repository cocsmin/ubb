use GLOVO
go

CREATE OR ALTER PROCEDURE PhantomReads1_Mancare AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        -- Log: T1 incepe si asteapta pentru a permite T2 sa faca prima citire
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T1_MANCARE', 'Insert new MANCARE', 'Started');

        -- Pauza pentru ca T2 sa ruleze in paralel
        WAITFOR DELAY '00:00:10';

        -- Inseram o noua mancare care va cauza un phantom read
        INSERT INTO MANCARE (ID_MENIU, DENUMIRE, CANTITATE, PRET)
        VALUES (1, 'Shaorma fantoma', 5, 17);

        -- Confirmam tranzactia
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO

EXEC PhantomReads1_Mancare

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;