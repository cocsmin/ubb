use GLOVO
GO
INSERT INTO Tables(Name) VALUES
('CURIER'), ('RESTAURANT'), ('PRODUSE');
--e,p,ambele 

SELECT * FROM Tables


-- creare VIEW-uri

GO

CREATE OR ALTER VIEW VRESTAURANT AS
	SELECT R.NUME_RES AS NumeR, M.NR_PAGINI AS NrPag, M.ID_MENIU, MA.ID_MEN_ALC
	FROM RESTAURANT R 
	JOIN MENIU M ON M.ID_MENIU = R.ID_MENIU
	JOIN MENIU_ALC MA ON MA.ID_MEN_ALC = R.ID_MEN_ALC

GO


CREATE OR ALTER VIEW VCURIER AS
    SELECT NUME as NumeCurier,
           PRENUME as PrenumeCurier,
           EMAIL as EmailCurier        
	FROM CURIER
GO

CREATE OR ALTER VIEW VPRODUSE AS
	SELECT R.NUME_RES AS NumeRestaurant, COUNT(P.ID) AS NumarPRODUSE
	FROM RESTAURANT R
	JOIN PRODUSE P ON P.ID_RES = R.ID_RES
	GROUP BY R.NUME_RES
GO


SELECT * FROM VRESTAURANT

SELECT * FROM VCURIER

SELECT * FROM VPRODUSE


-- inserare in Tabela VIEWS

INSERT INTO Views VALUES ('VCURIER'), ('VRESTAURANT'), ('VPRODUSE');
SELECT * FROM Views

-- inserare in Tabela Tests

INSERT INTO Tests(Name) 
VALUES ('insert_table_10'),
       ('insert_table_100'),
	   ('insert_table_1000'),
	   ('delete_table_10'),
	   ('delete_table_100'),
	   ('delete_table_1000'),
	   ('select_view')
GO

SELECT * FROM Tests

-- inserare in Tabela TestViews

INSERT INTO TestViews(TestID, ViewID) 
VALUES (7, 1), (7, 2), (7, 3)
GO

SELECT * FROM TestViews

-- inserare in tabela TestTables
INSERT INTO TestTables VALUES
(1, 1, 10, 1),
(2, 1, 100, 1),
(3, 1, 1000, 1),
(1, 2, 10, 2),
(2, 2, 100, 2),
(3, 2, 1000, 2),
(1, 3, 10, 3),
(2, 3, 100, 3),
(3, 3, 1000, 3),

(4, 1, 10, 3),
(5, 1, 100, 3),
(6, 1, 1000, 3),
(4, 2, 10, 2),
(5, 2, 100, 2),
(6, 2, 1000, 2),
(4, 3, 10, 1),
(5, 3, 100, 1),
(6, 3, 1000, 1)
GO


-- inserare tabela CURIER
CREATE OR ALTER PROCEDURE InsertCURIER (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @nume VARCHAR(50)
	DECLARE @i int
	DECLARE @lastId int
	SET @nume = 'nume_curier'
	SET @id = 1000
	SET @i = 1
	
	SET IDENTITY_INSERT CURIER ON;

	WHILE @i <= @rows
	BEGIN
		SET @id = 1000 + @i
		SELECT TOP 1 @lastId =  CURIER.ID_CUR FROM dbo.CURIER ORDER BY CURIER.ID_CUR DESC
		IF @lastId > 2000
			SET @id = @lastId + 1
		INSERT INTO CURIER(ID_CUR, NUME) VALUES (@id, @nume)
		SET @i = @i + 1
	END

	SET IDENTITY_INSERT CURIER OFF;

END
GO

-- stergere din tabela CURIER
CREATE OR ALTER PROCEDURE DeleteCURIER (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int
	SET @id = 1000
	SET @i = @rows

	WHILE @i > 0
	BEGIN 
		SET @id = 1000 + @i
		SELECT TOP 1 @lastId = CURIER.ID_CUR FROM dbo.CURIER ORDER BY CURIER.ID_CUR DESC
		IF @lastId > @id
			SET @id = @lastId
		DELETE FROM CURIER WHERE CURIER.ID_CUR = @id
		SET @i = @i - 1
	END
END
GO

exec InsertCURIER 10
exec DeleteCURIER 10

SELECT * FROM CURIER

GO

-- inserare in tabela Restaurant
CREATE OR ALTER PROCEDURE InsertRESTAURANT (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @nume VARCHAR(100)
	DECLARE @i int
	DECLARE @lastId int

	DECLARE @fkMENIU int
	DECLARE @fkMENIU_ALC int

	SET @nume = 'nume_restaurant'
	SET @id = 1000
	SET @i = 1

	SET IDENTITY_INSERT RESTAURANT ON;

	WHILE @i <= @rows
	BEGIN
		SET @id = 1000+@i
		SELECT TOP 1 @lastId = RESTAURANT.ID_RES FROM dbo.RESTAURANT ORDER BY RESTAURANT.ID_RES DESC
		IF @lastId > 1000
			SET @id = @lastId + 1
		SELECT TOP 1 @fkMENIU = MENIU.ID_MENIU FROM dbo.MENIU ORDER BY NEWID()
		SELECT TOP 1 @fkMENIU_ALC = MENIU_ALC.ID_MEN_ALC FROM dbo.MENIU_ALC ORDER BY NEWID()

		INSERT INTO RESTAURANT(ID_RES, NUME_RES, ID_MENIU, ID_MEN_ALC) VALUES (@id, @nume, @fkMENIU, @fkMENIU_ALC)
		SET @i = @i + 1
	END

	SET IDENTITY_INSERT RESTAURANT OFF;

END
GO

-- stergere din tabela RESTAURANT
CREATE OR ALTER PROCEDURE DeleteRESTAURANT (@rows int)
AS
BEGIN
	DECLARE @id int
	DECLARE @i int
	DECLARE @lastId int

	SET @id = 1000
	SET @i = @rows

	WHILE @i > 0
	BEGIN
		SET @id = 1000 + @i
		SELECT TOP 1 @lastId = RESTAURANT.ID_RES FROM dbo.RESTAURANT ORDER BY RESTAURANT.ID_RES DESC
		IF @lastId > @id
			SET @id = @lastId
		DELETE FROM RESTAURANT WHERE RESTAURANT.ID_RES = @id 
		SET @i = @i - 1
	END
END
GO
 
exec InsertRESTAURANT 10
exec DeleteRESTAURANT 10

SELECT * FROM RESTAURANT

GO

-- inserare in tabela PRODUSE
CREATE OR ALTER PROCEDURE InsertPRODUSE (@rows int)
AS
BEGIN
	DECLARE @i int
	SET @i = @rows


	exec InsertRESTAURANT @rows
	
	DECLARE @idR int, @nume varchar(100)
	DECLARE cursorPRODUSE CURSOR SCROLL FOR
	SELECT ID_RES as ID, NUME_RES as Nume FROM RESTAURANT;
	OPEN cursorPRODUSE;
	FETCH LAST FROM cursorPRODUSE INTO @idR, @nume;
    IF NOT EXISTS (SELECT 1 FROM COMANDA WHERE ID_COMANDA = 17)
    BEGIN
        SET IDENTITY_INSERT COMANDA ON
        INSERT INTO COMANDA (ID_COMANDA) VALUES (17)
        SET IDENTITY_INSERT COMANDA OFF
    END
    -- SET IDENTITY_INSERT RESTAURANT ON
    -- INSERT INTO RESTAURANT(ID_RES) VALUES (@idR)
    -- SET IDENTITY_INSERT RESTAURANT OFF
	WHILE @i > 0 AND @@FETCH_STATUS = 0
	BEGIN
		INSERT INTO PRODUSE(ID_COM, ID_RES) VALUES (17, @idR)
		FETCH PRIOR FROM cursorPRODUSE INTO @idR, @nume
		SET @i = @i - 1
	END

	CLOSE cursorPRODUSE
	DEALLOCATE cursorPRODUSE

END
GO

-- stergere din tabel PRODUSE
CREATE OR ALTER PROCEDURE DeletePRODUSE (@rows int)
AS
BEGIN
	DECLARE @idR int
	DECLARE @idC int
	DECLARE @i int

	SET @i = @rows
	SET @idC = 17

	WHILE @i > 0
	BEGIN
		SELECT TOP 1 @idR = PRODUSE.ID_RES FROM dbo.PRODUSE ORDER BY PRODUSE.ID_RES DESC
		IF @idR > 1000
		BEGIN
			DELETE FROM PRODUSE WHERE PRODUSE.ID_COM = @idC AND PRODUSE.ID_RES = @idR
			exec DeleteRESTAURANT 1
		END
		SET @i = @i - 1
	END
    DELETE FROM RESTAURANT WHERE RESTAURANT.ID_RES = @idR
    DELETE FROM COMANDA WHERE COMANDA.ID_COMANDA = @idC
END
GO

exec InsertPRODUSE 10
exec DeletePRODUSE 10

SELECT * FROM PRODUSE

GO

-- inserarile
CREATE OR ALTER PROCEDURE insert_table_10 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='CURIER'
	exec InsertCURIER 10
	IF @Tabela='RESTAURANT'
	exec InsertRESTAURANT 10
	IF @Tabela='PRODUSE'
	exec InsertPRODUSE 10
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE insert_table_100 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='CURIER'
	exec InsertCURIER 100
	IF @Tabela='RESTAURANT'
	exec InsertRESTAURANT 100
	IF @Tabela='PRODUSE'
	exec InsertPRODUSE 100
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE insert_table_1000 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='CURIER'
	exec InsertCURIER 1000
	IF @Tabela='RESTAURANT'
	exec InsertRESTAURANT 1000
	IF @Tabela='PRODUSE'
	exec InsertPRODUSE 1000
	else PRINT 'Nume invalid'
END
GO

-- stergeri
CREATE OR ALTER PROCEDURE delete_table_10 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='CURIER'
	exec DeleteCURIER 10
	IF @Tabela='RESTAURANT'
	exec DeleteRESTAURANT 10
	IF @Tabela='PRODUSE'
	exec DeletePRODUSE 10
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE delete_table_100 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='CURIER'
	exec DeleteCURIER 100
	IF @Tabela='RESTAURANT'
	exec DeleteRESTAURANT 100
	IF @Tabela='PRODUSE'
	exec DeletePRODUSE 100
	else PRINT 'Nume invalid'
END
GO

CREATE OR ALTER PROCEDURE delete_table_1000 (@Tabela VARCHAR(20))
AS
BEGIN
	IF @Tabela='CURIER'
	exec DeleteCURIER 1000
	IF @Tabela='RESTAURANT'
	exec DeleteRESTAURANT 1000
	IF @Tabela='PRODUSE'
	exec DeletePRODUSE 1000
	else PRINT 'Nume invalid'
END
GO

-- view
CREATE OR ALTER PROCEDURE Evaluare (@View VARCHAR(20))
AS
BEGIN
	IF @View='VCURIER'
	select * from View1
	IF @View='VRESTAURANT'
	select * from View2
	IF @View='VPRODUSE'
	select * from View3
	else PRINT 'Nume invalid'
END
GO

-- main
CREATE OR ALTER PROCEDURE main (@Tabela VARCHAR(20), @rows int)
AS
BEGIN
	DECLARE @t1 datetime, @t2 datetime, @t3 datetime
	DECLARE @desc VARCHAR(2000)

	DECLARE @testInserare VARCHAR(20)
	DECLARE @testSterge VARCHAR(20)
	DECLARE @nrRows int
	DECLARE @idTest int

	DECLARE @id_table int
	SELECT @id_table = Tables.TableID FROM Tables WHERE Tables.Name = @Tabela

	DECLARE @id_view int
	SELECT @id_view = Views.ViewID FROM Views WHERE Views.Name = 'V'+@Tabela

	SET @testInserare = 'insert_table_' + CONVERT(VARCHAR(10),@rows)
	SET @testSterge = 'delete_table_' + CONVERT(VARCHAR(10),@rows)

		SET @t1 = GETDATE()
		exec @testInserare @Tabela
		exec @testSterge @Tabela
		SET @t2 = GETDATE()
		exec Evaluare @Tabela
		SET @t3 = GETDATE()
		SET @desc = @testInserare+' - '+@testSterge+' - '+@Tabela
		INSERT INTO TestRuns VALUES (@desc, @t1, @t2)
		SELECT TOP 1 @idTest=T.TestRunID FROM dbo.TestRuns T ORDER BY T.TestRunID DESC
		INSERT INTO TestRunTables VALUES (@idTest,@id_table,@t1,@t2)
		INSERT INTO TestRunViews VALUES (@idTest,@id_view,@t2,@t3)

END

exec main @Tabela = CURIER, @rows = 10
exec main @Tabela = RESTAURANT, @rows = 10
exec main @Tabela = PRODUSE, @rows = 10

exec main @Tabela = CURIER, @rows = 100
exec main @Tabela = RESTAURANT, @rows = 100
exec main @Tabela = PRODUSE, @rows = 100

exec main @Tabela = CURIER, @rows = 1000
exec main @Tabela = RESTAURANT, @rows = 1000
exec main @Tabela = PRODUSE, @rows = 1000


select * from TestRuns
select * from TestRunTables
select * from TestRunViews


SELECT * FROM CURIER
SELECT * FROM RESTAURANT
SELECT * FROM PRODUSE


exec InsertPRODUSE 10
exec DeletePRODUSE 10
SELECT * FROM PRODUSE


exec InsertRESTAURANT 10
exec DeleteRESTAURANT 10
SELECT * FROM RESTAURANT


exec InsertCURIER 10
exec DeleteCURIER 10
SELECT * FROM CURIER



DROP TABLE TestRunViews
DROP TABLE TestRunTables
DROP TABLE TestRuns 
DROP TABLE TestTables
DROP TABLE TestViews
DROP TABLE Tests
DROP TABLE Tables
DROP TABLE Views