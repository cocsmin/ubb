using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using csharp_avalonia.domain;
using log4net;
using Microsoft.Data.SqlClient;

namespace csharp_avalonia.repository;

public class DonatorRepo : DonatorRepo0
{
    private static readonly ILog logger = LogManager.GetLogger("DonatorRepo");

    public Donator findOne(int id)
    {
        logger.Info("Donator - findOne - Enter");
        Donator donator = new Donator("", "", "");
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "select * from DONATORI where id = @id_donator";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("id_donator", id);
                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    int id_donator = reader.GetInt32(0);
                    string nume = reader.GetString(1);
                    string adresa = reader.GetString(2);
                    string telefon = reader.GetString(3);
                    
                    donator = new Donator(nume, adresa, telefon);
                    donator.Id = id_donator;
                }
            }
        }
        logger.Info("Donator - findOne - Exit");
        return donator;
    }

    public IEnumerable<Donator> findAll()
    {
        logger.Info("Donator - findAll - Enter");
        List<Donator> donatori = new List<Donator>();
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            var sql = "select * from DONATORI";
            using (var cmd = new SqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    int id_donator = reader.GetInt32(0);
                    string nume = reader.GetString(1);
                    string adresa = reader.GetString(2);
                    string telefon = reader.GetString(3);
                    
                    Donator donator = new Donator(nume, adresa, telefon);
                    donator.Id = id_donator;
                    
                    donatori.Add(donator);
                }
            }
        }
        logger.Info("Donator - findAll - Exit");
        return donatori;
    }

    public List<Donator> FindByNameContaining(string partialName)
    {
        logger.Info("Donator - FindByNameContaining - Enter");
        var result = new List<Donator>();
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            string sql = "SELECT * FROM DONATORI WHERE nume_donator LIKE @partialName";
            using (var command = new SqlCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@partialName", "%" + partialName + "%");
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int id = reader.GetInt32(reader.GetOrdinal("id_donator"));
                        string nume = reader.GetString(reader.GetOrdinal("nume_donator"));
                        string adresa = reader.GetString(reader.GetOrdinal("adresa"));
                        string telefon = reader.GetString(reader.GetOrdinal("telefon"));
                        result.Add(new Donator(id, nume, adresa, telefon));
                    }
                }
            }
        }
        logger.Info("Donator - FindByNameContaining - Exit");
        logger.Info($"Found {result.Count} Donatori");
        return result;
    }

    public Donator FindByFullName(string donorName)
    {
        logger.Info("Donator - FindByFullName - Enter");
        Donator result = null;
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            string sql = "SELECT * FROM DONATORI WHERE nume_donator = @donorName";
            using (var command = new SqlCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@donorName", donorName);
                using (var reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int id = reader.GetInt32(reader.GetOrdinal("id_donator"));
                        string nume = reader.GetString(reader.GetOrdinal("nume_donator"));
                        string adresa = reader.GetString(reader.GetOrdinal("adresa"));
                        string telefon = reader.GetString(reader.GetOrdinal("telefon"));
                        result = new Donator(id, nume, adresa, telefon);
                    }
                }
            }
        }
        return result;    
    }

    public Donator save(Donator donator)
    {
        logger.Info("Donator - save - Enter");
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "insert into DONATORI values (@nume_donator, @adresa, @telefon)";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("nume_donator", donator.Nume_donator);
                cmd.Parameters.AddWithValue("adresa", donator.Adresa);
                cmd.Parameters.AddWithValue("telefon", donator.Telefon);
                
                int rowsAffected = cmd.ExecuteNonQuery();
                if (rowsAffected != 0)
                {
                    donator = null!;
                }
            }
        }
        logger.Info("Donator - save - Exit");
        return donator;
    }
    
    
    
}