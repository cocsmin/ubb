using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using csharp_avalonia.domain;
using log4net;
using Microsoft.Data.SqlClient;

namespace csharp_avalonia.repository;

public class DonatieRepo : DonatieRepo0
{
    private static readonly ILog logger = LogManager.GetLogger("DonatiiRepo");
    
    //cu join uri pe data viitoare!!!!
    private VoluntarRepo voluntarRepo;
    private DonatorRepo donatorRepo;
    private CazRepo cazRepo;
    
    public Donatie findOne(int id)
    {
        logger.Info("Voluntar - findOne - Enter");
        Donatie donatie = null;
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "select * from DONATII where id = @id_donatie";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("id_donatie", id);
                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    int id_donatie = reader.GetInt32(0);
                    int id_donator = reader.GetInt32(1);
                    int id_caz = reader.GetInt32(2);
                    var date = reader.GetDateTime(3);
                    int suma = reader.GetInt32(4);

                    Donator donator = donatorRepo.findOne(id_donator);
                    Caz caz = cazRepo.findOne(id_caz);
                    donatie = new Donatie(donator, caz, date, suma);
                    donatie.Id = id_donatie;
                    
                }
            }
        }
        logger.Info("Donatie - findOne - Exit");
        return donatie;
    }

    public IEnumerable<Donatie> findAll()
    {
        logger.Info("Donatie - findAll - Enter");
        List<Donatie> donatii = new List<Donatie>();
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            var sql = "select * from DONATII";
            using (var cmd = new SqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    int id_donatie = reader.GetInt32(0);
                    int id_donator = reader.GetInt32(1);
                    int id_caz = reader.GetInt32(2);
                    var date = reader.GetDateTime(3);
                    int suma = reader.GetInt32(4);

                    Donator donator = donatorRepo.findOne(id_donator);
                    Caz caz = cazRepo.findOne(id_caz);
                    Donatie donatie = new Donatie(donator, caz, date, suma);
                    donatie.Id = id_donatie;
                    
                    donatii.Add(donatie);
                }
            }
        }
        logger.Info("Donatie - findAll - Exit");
        return donatii;
    }

    public int GetSumaDonatiiPentruCaz(int cazId)
    {
        int suma = 0;
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            string sql = "SELECT SUM(suma_donata) AS Total FROM DONATII WHERE id_caz = @cazId";
            using (var command = new SqlCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@cazId", cazId);
                object result = command.ExecuteScalar();
                if (result != DBNull.Value && result != null)
                {
                    suma = Convert.ToInt32(result);
                }
            }
        }
        return suma;
    }

    public Donatie save(Donatie donatie)
    {
        logger.Info("Donatie - save - Enter");
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "insert into DONATII values (@id_donator, @id_caz, @data_donatie, @suma_donata)";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("id_donator", donatie.Donator.Id);
                cmd.Parameters.AddWithValue("id_caz", donatie.Caz.Id);
                cmd.Parameters.AddWithValue("data_donatie", donatie.Data_donatie);
                cmd.Parameters.AddWithValue("suma_donata", donatie.Suma_donata);
                
                int rowsAffected = cmd.ExecuteNonQuery();
                if (rowsAffected != 0)
                {
                    donatie = null!;
                }
            }
        }
        logger.Info("Donatie - save - Exit");
        return donatie;
    }
}