use GLOVO
go

CREATE OR ALTER PROCEDURE DirtyReads1_Mancare AS
BEGIN
	BEGIN TRAN;
	BEGIN TRY
		-- Log pentru inceputul tranzactiei MANCARE_T1
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('MANCARE_T1', 'Update MANCARE cu ID 1', 'Started');

		-- UPDATE pe mancare deja existenta
		UPDATE MANCARE SET PRET = 111 WHERE ID_MANCARE = 1;

		-- Pauza pentru simulare dirty read
		WAITFOR DELAY '00:00:10';

		-- Log pentru rollback
		INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
		VALUES ('MANCARE_T1', 'Rollback update MANCARE ID 1', 'Rolled Back');

		-- Anulam modificarile
		ROLLBACK TRAN;
		SELECT 'Transaction successfully rolled back!' AS [Message];
	END TRY
	BEGIN CATCH
		IF @@TRANCOUNT > 0
		BEGIN
			ROLLBACK TRANSACTION;
		END
		SELECT 'Transaction unsuccessfully rolled back!' AS [Message];
	END CATCH;
END;
GO

EXEC DirtyReads1_Mancare;


SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;