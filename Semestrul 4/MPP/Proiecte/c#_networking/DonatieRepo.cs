using System;
using System.Collections.Generic;
using System.Configuration;
using Microsoft.Data.SqlClient;
using modelnet;
using log4net;

namespace persistancenet
{
    public class DonatieRepo : DonatieRepo0
    {
        private static readonly ILog logger = LogManager.GetLogger("DonatiiRepo");
        private readonly string _connectionString;

        public DonatieRepo(string connectionString)
        {
            this._connectionString = connectionString;
        }

        public Donatie findOne(int id)
        {
            logger.Info("Donatie - FindOne - Enter");
            Donatie donatie = null;

            const string sql = @"
                SELECT d.id                  AS DonatieId,
                       d.data_donatie        AS DataDonatie,
                       d.suma_donata         AS SumaDonata,
                       don.id_donator        AS DonatorId,
                       don.nume_donator      AS DonatorNume,
                       don.adresa            AS DonatorAdresa,
                       don.telefon           AS DonatorTelefon,
                       c.id_caz              AS CazId,
                       c.nume_caz            AS CazNume,
                       c.descriere_caz       AS CazDescriere
                  FROM DONATII d
                  JOIN DONATORI   don ON don.id_donator = d.id_donator
                  JOIN CAZURI     c  ON c.id_caz      = d.id_caz
                 WHERE d.id = @id_donatie;";

            using (var conn = new SqlConnection(_connectionString))
            using (var cmd  = new SqlCommand(sql, conn))
            {
                cmd.Parameters.AddWithValue("@id_donatie", id);
                conn.Open();

                using (var rdr = cmd.ExecuteReader())
                {
                    if (rdr.Read())
                    {
                        var donator = new Donator(
                            id:     rdr.GetInt32(rdr.GetOrdinal("DonatorId")),
                            nume_donator:   rdr.GetString(rdr.GetOrdinal("DonatorNume")),
                            adresa: rdr.GetString(rdr.GetOrdinal("DonatorAdresa")),
                            telefon: rdr.GetString(rdr.GetOrdinal("DonatorTelefon"))
                        );

                        var caz = new Caz(
                            id:         rdr.GetInt32(rdr.GetOrdinal("CazId")),
                            nume_caz:       rdr.GetString(rdr.GetOrdinal("CazNume")),
                            descriere:  rdr.GetString(rdr.GetOrdinal("CazDescriere"))
                        );

                        donatie = new Donatie(
                            donator,
                            caz,
                            rdr.GetDateTime(rdr.GetOrdinal("DataDonatie")),
                            rdr.GetInt32(rdr.GetOrdinal("SumaDonata"))
                        )
                        {
                            Id = rdr.GetInt32(rdr.GetOrdinal("DonatieId"))
                        };
                    }
                }
            }

            logger.Info("Donatie - FindOne - Exit");
            return donatie;
        }

        public IEnumerable<Donatie> findAll()
        {
            logger.Info("Donatie - FindAll - Enter");
            var lista = new List<Donatie>();

            const string sql = @"
                SELECT d.id                  AS DonatieId,
                       d.data_donatie        AS DataDonatie,
                       d.suma_donata         AS SumaDonata,
                       don.id_donator        AS DonatorId,
                       don.nume_donator      AS DonatorNume,
                       don.adresa            AS DonatorAdresa,
                       don.telefon           AS DonatorTelefon,
                       c.id_caz              AS CazId,
                       c.nume_caz            AS CazNume,
                       c.descriere_caz       AS CazDescriere
                  FROM DONATII d
                  JOIN DONATORI   don ON don.id_donator = d.id_donator
                  JOIN CAZURI     c  ON c.id_caz      = d.id_caz;";

            using (var conn = new SqlConnection(_connectionString))
            using (var cmd  = new SqlCommand(sql, conn))
            {
                conn.Open();
                using (var rdr = cmd.ExecuteReader())
                {
                    while (rdr.Read())
                    {
                        var donator = new Donator(
                            id:     rdr.GetInt32(rdr.GetOrdinal("DonatorId")),
                            nume_donator:   rdr.GetString(rdr.GetOrdinal("DonatorNume")),
                            adresa: rdr.GetString(rdr.GetOrdinal("DonatorAdresa")),
                            telefon: rdr.GetString(rdr.GetOrdinal("DonatorTelefon"))
                        );

                        var caz = new Caz(
                            id:         rdr.GetInt32(rdr.GetOrdinal("CazId")),
                            nume_caz:       rdr.GetString(rdr.GetOrdinal("CazNume")),
                            descriere:  rdr.GetString(rdr.GetOrdinal("CazDescriere"))
                        );

                        var donatie = new Donatie(
                            donator,
                            caz,
                            rdr.GetDateTime(rdr.GetOrdinal("DataDonatie")),
                            rdr.GetInt32(rdr.GetOrdinal("SumaDonata"))
                        )
                        {
                            Id = rdr.GetInt32(rdr.GetOrdinal("DonatieId"))
                        };

                        lista.Add(donatie);
                    }
                }
            }

            logger.Info("Donatie - FindAll - Exit");
            return lista;
        }

        public int GetSumaDonatiiPentruCaz(int cazId)
        {
            logger.Info("Donatie - GetSumaDonatiiPentruCaz - Enter");
            int suma = 0;

            const string sql = @"
                SELECT SUM(d.suma_donata)
                  FROM DONATII d
                 WHERE d.id_caz = @cazId;";

            using (var conn = new SqlConnection(_connectionString))
            using (var cmd  = new SqlCommand(sql, conn))
            {
                cmd.Parameters.AddWithValue("@cazId", cazId);
                conn.Open();

                var result = cmd.ExecuteScalar();
                if (result != DBNull.Value && result != null)
                    suma = Convert.ToInt32(result);
            }

            logger.Info("Donatie - GetSumaDonatiiPentruCaz - Exit");
            return suma;
        }

        public Donatie save(Donatie donatie)
        {
            logger.Info("Donatie - Save - Enter");

            const string sql = @"
                INSERT INTO DONATII (id_donator, id_caz, data_donatie, suma_donata)
                VALUES (@id_donator, @id_caz, @data_donatie, @suma_donata);
                SELECT CAST(SCOPE_IDENTITY() AS int);";

            using (var conn = new SqlConnection(_connectionString))
            using (var cmd  = new SqlCommand(sql, conn))
            {
                cmd.Parameters.AddWithValue("@id_donator",   donatie.Donator.Id);
                cmd.Parameters.AddWithValue("@id_caz",       donatie.Caz.Id);
                cmd.Parameters.AddWithValue("@data_donatie", donatie.Data_donatie);
                cmd.Parameters.AddWithValue("@suma_donata",  donatie.Suma_donata);

                conn.Open();
                var obj = cmd.ExecuteScalar();
                if (obj == null)
                    throw new Exception("Insert n o dat niciun id!");
                donatie.Id = Convert.ToInt32(obj);
            }

            logger.Info("Donatie - Save - Exit");
            return donatie;
        }
    }
}
