use GLOVO
go

CREATE OR ALTER PROCEDURE Deadlock2_MancareMeniu AS
BEGIN
    BEGIN TRAN;

    -- T2 blocheaza MENIU 
    UPDATE MENIU SET NR_PAGINI = 200 WHERE ID_MENIU = 1;

    -- Pauza pentru ca T1 sa ruleze si sa blocheze MANCARE
    WAITFOR DELAY '00:00:10';

    -- T2 incearca sa blocheze MENIU 
    UPDATE MANCARE SET DENUMIRE = 'Pizza cu threaduri - T2' WHERE ID_MANCARE = 1;


    COMMIT TRAN;
    SELECT 'Transaction committed' AS [Message];

    -- Log pentru T2
    INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
    VALUES ('T2_MANCARE_MENIU', 'Update MENIU then MANCARE', 'Committed');
END;
GO

CREATE OR ALTER PROCEDURE Deadlock2_MancareMeniu_Correct AS
BEGIN
    BEGIN TRAN;

    -- T2 blocheaza MANCARE 
    UPDATE MANCARE SET DENUMIRE = 'Pizza cu threaduri - T2 fixed' WHERE ID_MANCARE = 1;

    -- Pauza pentru sincronizare
    WAITFOR DELAY '00:00:10';

    -- T2 actualizeaza MENIU 
    UPDATE MENIU SET NR_PAGINI = 299 WHERE ID_MENIU = 1;

    COMMIT TRAN;
    SELECT 'Transaction committed' AS [Message];

    -- Log pentru varianta corecta
    INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
    VALUES ('T2_MANCARE_MENIU_CORRECT', 'Correct update order', 'Committed');
END;
GO

EXEC Deadlock2_MancareMeniu
EXEC Deadlock2_MancareMeniu_Correct

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;