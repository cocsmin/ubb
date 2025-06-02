use GLOVO
go

CREATE OR ALTER PROCEDURE NonRepeatableReads1_Mancare AS
BEGIN
    BEGIN TRAN;
    BEGIN TRY
        -- Log: T1 incepe si asteapta ca T2 sa faca prima citire
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T1_Mancare', 'Start and wait before update', 'Started');

        -- Asteptam ca T2 sa faca prima citire
        WAITFOR DELAY '00:00:10';

        -- T1 face UPDATE asupra unei valori din MANCARE (ex: mancare cu id = 1)
        UPDATE MANCARE SET PRET = 999 WHERE ID_MANCARE = 1;

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

EXEC NonRepeatableReads1_Mancare

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;