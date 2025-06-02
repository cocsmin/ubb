use GLOVO
go

CREATE OR ALTER PROCEDURE NonRepeatableReads2_Mancare AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
    BEGIN TRAN;
    BEGIN TRY
        -- T2 face prima citire: va vedea valorile initiale din MANCARE
        SELECT * FROM MANCARE;

        -- Pauza pentru a permite lui T1 sa faca UPDATE
        WAITFOR DELAY '00:00:15';

        -- T2 face a doua citire: valorile pot fi diferite daca T1 a facut UPDATE
        SELECT * FROM MANCARE;

        -- Finalizare tranzactie
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];

        -- Log final
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T2_MANCARE', 'Non-repeatable read on MANCARE (READ COMMITTED)', 'Completed');
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO


CREATE OR ALTER PROCEDURE NonRepeatableReads2_Mancare_Correct AS
BEGIN
    SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
    BEGIN TRAN;
    BEGIN TRY
        -- T2 face prima citire si blocheaza modificarile asupra inregistrarilor citite
        SELECT * FROM MANCARE;

        -- Pauza pentru a da timp lui T1 sa incerce UPDATE (care va fi blocat pana T2 termina)
        WAITFOR DELAY '00:00:15';

        -- T2 face a doua citire: va vedea aceleasi date ca la prima citire
        SELECT * FROM MANCARE;

        -- Finalizam tranzactia
        COMMIT TRAN;
        SELECT 'Transaction committed' AS [Message];

        -- Log final
        INSERT INTO Transaction_Log (Transaction_Name, Action, Status) 
        VALUES ('T2_MANCARE_CORRECT', 'Repeatable read on MANCARE (REPEATABLE READ)', 'Completed');
    END TRY
    BEGIN CATCH
        ROLLBACK TRAN;
        SELECT 'Transaction rolled back' AS [Message];
    END CATCH;
END;
GO


EXEC NonRepeatableReads2_Mancare
EXEC NonRepeatableReads2_Mancare_Correct

SELECT * FROM Transaction_Log ORDER BY Timestamp DESC;