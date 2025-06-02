use GLOVO 
go

CREATE OR ALTER PROCEDURE PhantomReads2_Mancare AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    BEGIN TRANSACTION;
    BEGIN TRY
        -- T2 face prima citire (inainte ca T1 sa insereze)
        SELECT * FROM MANCARE;

        -- Pauza pentru a permite lui T1 sa insereze
        WAITFOR DELAY '00:00:15';

        -- T2 face a doua citire: poate vedea randul nou (phantom read)
        SELECT * FROM MANCARE;

        -- Finalizarea tranzactiei
        COMMIT TRANSACTION;
        SELECT 'Transaction committed' AS [Message];

        -- Log pentru phantom read
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T2_MANCARE', 'Phantom read on MANCARE (REPEATABLE READ)', 'Completed');
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO

CREATE OR ALTER PROCEDURE PhantomReads2_Mancare_Correct AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;
    BEGIN TRANSACTION;
    BEGIN TRY
        -- T2 face prima citire (blocheaza range-ul de date)
        SELECT * FROM MANCARE;

        -- Pauza: T1 va incerca sa insereze, dar va fi blocat pana T2 termina
        WAITFOR DELAY '00:00:15';

        -- A doua citire: nu va aparea randul nou
        SELECT * FROM MANCARE;

        -- Finalizare
        COMMIT TRANSACTION;
        SELECT 'Transaction committed' AS [Message];

        -- Log pentru comportament corect
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T2_MANCARE_CORRECT', 'No phantom read on MANCARE (SERIALIZABLE)', 'Completed');
    END TRY
    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO

EXEC PhantomReads2_Mancare
EXEC PhantomReads2_Mancare_Correct


SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;