CREATE DATABASE alegeri
GO

USE alegeri
GO

CREATE TABLE cetateni(
    id int PRIMARY key IDENTITY (1,1),
    nume varchar(50),
    prenume varchar(50),
    cnp varchar(50)
)

CREATE TABLE candidat(
    id int PRIMARY key IDENTITY (1,1),
    nume varchar(50),
    prenume varchar(50),
    data_nasterii DATE,
)

CREATE TABLE sectie(
    id int PRIMARY key IDENTITY (1,1),
    numar int,

)

CREATE TABLE vot(
    id_cet int FOREIGN KEY REFERENCES cetateni(id) UNIQUE,
    id_sectie int FOREIGN KEY REFERENCES sectie(id),
    id_candidat int FOREIGN KEY REFERENCES candidat(id),
    CONSTRAINT pk_vot PRIMARY KEY (id_cet, id_sectie, id_canditat),

    ora TIME,
    lista_supl BIT DEFAULT 0
)

CREATE TABLE echipaje(
    numar int PRIMARY key,
    numar_membrii int,
)

CREATE TABLE echipaje_pe_sectii(
    id_echipaj int FOREIGN KEY REFERENCES echipaje(numar),
    id_sectie int FOREIGN KEY REFERENCES sectie(id),
    CONSTRAINT pk_e_s PRIMARY KEY (id_echipaj, id_sectie)
)

GO

CREATE OR ALTER PROCEDURE calculeaza (@numar_sectie INT, @id_castigator INT OUTPUT) as
    BEGIN
        DECLARE @nr_voturi INT
        DECLARE @total_voturi INT

        SELECT @total_voturi = COUNT(*)
        FROM vot where id_sectie = @numar_sectie

        SELECT @nr_voturi = COUNT(id_canditat)
        FROM vot GROUP BY id_canditat
        ORDER BY COUNT(id_canditat) DESC

        SELECT TOP 1 @id_castigator = vot.id_canditat 
        FROM vot GROUP BY vot.id_canditat 
        ORDER BY COUNT(id_canditat) DESC

        RETURN @nr_voturi

    END






GO

CREATE OR ALTER VIEW voturi_cg as 
    SELECT S.numar as [NR_SECTIE] , COUNT(V.id_canditat) as [NR_VOTURI]
    FROM vot V
    JOIN sectie S on S.id = V.id_sectie
    JOIN candidat C ON V.id_canditat = C.id
    WHERE C.nume = 'Georgescu' AND C.prenume = 'Calin'
    GROUP BY S.numar
    HAVING (COUNT(V.id_canditat) > 500)
