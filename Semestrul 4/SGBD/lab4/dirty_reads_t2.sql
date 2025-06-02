use GLOVO
go

CREATE OR ALTER PROCEDURE DirtyReads2_Mancare AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
	BEGIN TRAN;
	BEGIN TRY
		-- T2: citeste toate inregistrarile din MANCARE, inclusiv modificarile neconfirmate facute de T1
		SELECT * FROM MANCARE;

		-- Pauza pentru a permite rularea simultana cu T1
		WAITFOR DELAY '00:00:15';

		-- T2 citeste din nou: daca T1 nu a dat rollback inca, modificarile sunt inca vizibile
		SELECT * FROM MANCARE;

		-- Finalizare tranzactie T2
		COMMIT TRAN;
		SELECT 'Transaction committed!' AS [Message];

		-- Log inregistrare finala pentru T2
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('T2_MANCARE', 'SELECT on MANCARE (READ UNCOMMITTED)', 'Completed');
	END TRY
	BEGIN CATCH
		-- In caz de eroare, anulam tranzactia
		ROLLBACK TRAN;
		SELECT 'Transaction rolled back!' AS [Message];
	END CATCH;
END;
GO

CREATE OR ALTER PROCEDURE DirtyReads2_Mancare_Correct AS
BEGIN
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
	BEGIN TRAN;
	BEGIN TRY
		-- T2: citeste doar datele care au fost deja confirmate 
		SELECT * FROM MANCARE;

		-- Pauza pentru a permite rularea paralela cu T1
		WAITFOR DELAY '00:00:15';

		-- Citire repetata: daca T1 a facut rollback intre timp, modificarile nu vor fi vizibile
		SELECT * FROM MANCARE;

		-- Finalizare tranzactie T2
		COMMIT TRAN;
		SELECT 'Transaction committed!' AS [Message];

		-- Log pentru comportamentul corect (nu vede datele neconfirmate)
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('T2_MANCARE_CORRECT', 'SELECT on MANCARE (READ COMMITTED)', 'Completed');
	END TRY
	BEGIN CATCH
		-- In caz de eroare, se face rollback
		ROLLBACK TRAN;
		SELECT 'Transaction rolled back!' AS [Message];
	END CATCH;
END;
GO


EXEC DirtyReads2_Mancare;
EXEC DirtyReads2_Mancare_Correct;

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;