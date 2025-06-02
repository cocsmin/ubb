use GLOVO
go

CREATE OR ALTER PROCEDURE Deadlock1_MancareMeniu AS
BEGIN
    BEGIN TRAN;

    -- T1 blocheaza MANCARE 
    UPDATE MANCARE SET DENUMIRE = 'Pizza cu threaduri - T1' WHERE ID_MANCARE = 1;

    -- Pauza pentru ca T2 sa porneasca intre timp
    WAITFOR DELAY '00:00:10';

    -- T1 incearca apoi sa blocheze MENIU
    UPDATE MENIU SET NR_PAGINI = 100 WHERE ID_MENIU = 1;

    COMMIT TRAN;
    SELECT 'Transaction committed' AS [Message];

    -- Log pentru T1
    INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
    VALUES ('T1_MANCARE_MENIU', 'Update MANCARE then MENIU', 'Committed');
END;
GO

EXEC Deadlock1_MancareMeniu

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;