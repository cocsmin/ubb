USE GLOVO
GO

-- DROP PROCEDURE edit_column
CREATE PROCEDURE edit_column AS
ALTER TABLE ADRESA
ALTER COLUMN TARA VARCHAR(100)
PRINT 'S-a modificat coloana [TARA] din tabelul [ADRESA]'
GO

-- DROP PROCEDURE reverse_edit_column
CREATE PROCEDURE reverse_edit_column AS
ALTER TABLE ADRESA
ALTER COLUMN TARA VARCHAR(50)
PRINT 'S-a modificat la loc coloana [TARA] din tabelul [ADRESA]'
GO


-- DROP PROCEDURE default_pret
CREATE PROCEDURE default_pret AS
ALTER TABLE MANCARE
ADD CONSTRAINT PRET_MIN DEFAULT 1 FOR PRET 
PRINT 'S-a adaugat constrangere pentru coloana [PRET] din tabelul [MANCARE]'
GO

-- DROP PROCEDURE remove_default_pret
CREATE PROCEDURE remove_default_pret AS
ALTER TABLE MANCARE
DROP CONSTRAINT PRET_MIN
PRINT 'S-a eliminat constrangerea pentru coloana [PRET] din tabelul [MANCARE]'
GO


-- DROP PROCEDURE create_new_table
CREATE PROCEDURE create_new_table AS
DROP TABLE IF EXISTS COMENZIFINALIZATE
CREATE TABLE COMENZIFINALIZATE(ID_C int PRIMARY KEY, FINALIZAT int NOT NULL)
PRINT 'S-a creat un nou tabel [COMENZIFINALIZATE]'
GO

-- DROP PROCEDURE delete_new_table
CREATE PROCEDURE delete_new_table AS
DROP TABLE COMENZIFINALIZATE
PRINT 'S-a sters tabelul [COMENZIFINALIZATE]'
GO


-- DROP PROCEDURE add_new_field
CREATE PROCEDURE add_new_field AS
ALTER TABLE COMENZIFINALIZATE
ADD CURIER VARCHAR(50)
PRINT 'S-a adaugat un camp nou in tabelul [COMENZIFINALIZATE]'
GO

-- DROP PROCEDURE delete_new_field
CREATE PROCEDURE delete_new_field AS
ALTER TABLE COMENZIFINALIZATE
DROP COLUMN CURIER
PRINT 'S-a eliminat coloana [CURIER] din tabelul [COMENZIFINALIZATE]'
GO

-- DROP PROCEDURE add_for_key
CREATE PROCEDURE add_for_key AS
ALTER TABLE COMENZIFINALIZATE
ADD CONSTRAINT FOR_ID_C FOREIGN KEY(ID_C) REFERENCES COMANDA(ID_COMANDA)
PRINT 'S-a adaugat cheie straina'
GO

-- DROP PROCEDURE delete_for_key
CREATE PROCEDURE delete_for_key AS
ALTER TABLE COMENZIFINALIZATE
DROP CONSTRAINT FOR_ID_C
PRINT 'S-a eliminat cheia straina'
GO

    DROP PROCEDURE edit_column
    DROP PROCEDURE reverse_edit_column
    DROP PROCEDURE default_pret
    DROP PROCEDURE remove_default_pret
    DROP PROCEDURE create_new_table
    DROP PROCEDURE delete_new_table
    DROP PROCEDURE add_new_field
    DROP PROCEDURE delete_new_field
    DROP PROCEDURE add_for_key
    DROP PROCEDURE delete_for_key

   -- EXECUTE edit_column
   -- EXECUTE reverse_edit_column
   -- EXECUTE default_pret
   -- EXECUTE remove_default_pret
   -- EXECUTE create_new_table
   -- EXECUTE delete_new_table
   -- EXECUTE add_new_field
   -- EXECUTE delete_new_field
   -- EXECUTE add_for_key
   -- EXECUTE delete_for_key

DROP TABLE IF EXISTS VersiuneDB
CREATE TABLE VersiuneDB(NUMAR_VERSIUNE int);
INSERT INTO VersiuneDB VALUES(0);

DROP TABLE IF EXISTS Procedura_Versiune
CREATE TABLE Procedura_Versiune(
    ID int PRIMARY KEY,
    procedura varchar(50)
);

DROP TABLE IF EXISTS Procedura_Versiune_Invers
CREATE TABLE Procedura_Versiune_Invers(
    ID int PRIMARY KEY,
    procedura varchar(50)
);

INSERT INTO Procedura_Versiune VALUES
    (0, 'edit_column'),
    (1, 'default_pret'),
    (2, 'create_new_table'),
    (3, 'add_new_field'),
    (4, 'add_for_key');

INSERT INTO Procedura_Versiune_Invers VALUES
    (1, 'reverse_edit_column'),
    (2, 'remove_default_pret'),
    (3, 'delete_new_table'),
    (4, 'delete_new_field'),
    (5, 'delete_for_key');

GO

-- DROP PROCEDURE main
CREATE OR ALTER PROCEDURE main
@Versiune int AS
BEGIN
    IF @Versiune>5 OR @Versiune<0
    BEGIN 
        RAISERROR('NU EXISTA VERSIUNI DECAT DE LA 0 LA 5', 16, 1);
        RETURN
    END

    DECLARE @Versiune_Actuala AS int
    SELECT @Versiune_Actuala = NUMAR_VERSIUNE
    FROM VersiuneDB;

    PRINT 'Versiune actuala este: ';
    PRINT @Versiune_Actuala;
    PRINT 'Trecem la versiunea: ';
    PRINT @Versiune;

    IF @Versiune=@Versiune_Actuala
    BEGIN
        PRINT 'Suntem deja la aceasta versiune!';
        RETURN;
    END

    DELETE FROM VersiuneDB
    INSERT INTO VersiuneDB VALUES(@Versiune)

    DECLARE @Functie AS varchar(50);    

    IF @Versiune>@Versiune_Actuala
    BEGIN
        WHILE @Versiune!=@Versiune_Actuala
        BEGIN
            SELECT @Functie=procedura FROM Procedura_Versiune
            WHERE @Versiune_Actuala=ID;
            EXECUTE @Functie;
            SET @Versiune_Actuala=@Versiune_Actuala+1;
        END
        UPDATE VersiuneDB
        SET NUMAR_VERSIUNE=@Versiune;

        RETURN;
    END    

    --aici automat @Versiune<@Versiune_Actuala
    WHILE @Versiune!=@Versiune_Actuala
        BEGIN
            SELECT @Functie=procedura FROM Procedura_Versiune_Invers
            WHERE @Versiune_Actuala=ID;
            EXECUTE @Functie;
            SET @Versiune_Actuala=@Versiune_Actuala-1;    
        END
END

GO
EXEC main 0
EXEC main 1
EXEC main 2
EXEC main 3
EXEC main 4
EXEC main 5



