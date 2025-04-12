using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using csharp_avalonia.domain;
using log4net;
using Microsoft.Data.SqlClient;
using SQL;
namespace csharp_avalonia.repository;

public class CazRepo : CazRepo0
{
     private static readonly ILog logger = LogManager.GetLogger("CazRepo");

    public Caz findOne(int id)
    {
        logger.Info("Caz - findOne - Enter");
        Caz caz = new Caz("", "");
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "select * from CAZURI where id = @id_caz";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("id_caz", id);
                var reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    int id_caz = reader.GetInt32(0);
                    string nume_caz = reader.GetString(1);
                    string descriere_caz = reader.GetString(2);
                    
                    caz = new Caz(nume_caz, descriere_caz);
                    caz.Id = id_caz;
                }
            }
        }
        logger.Info("Caz - findOne - Exit");
        return caz;
    }

    public IEnumerable<Caz> findAll()
    {
        logger.Info("Caz - findAll - Enter");
        List<Caz> cazuri = new List<Caz>();
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            var sql = "select * from CAZURI";
            using (var cmd = new SqlCommand(sql, connection))
            {
                var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    int id_caz = reader.GetInt32(0);
                    string nume_caz = reader.GetString(1);
                    string descriere_caz = reader.GetString(2);
                    
                    Caz caz = new Caz(nume_caz, descriere_caz);
                    caz.Id = id_caz;
                    
                    cazuri.Add(caz);
                }
            }
        }
        logger.Info("Caz - findAll - Exit");
        return cazuri;
    }

    public Caz save(Caz caz)
    {
        logger.Info("Caz - save - Enter");
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "insert into CAZURI values (@nume_caz, @descriere_caz)";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("nume_caz", caz.Nume_caz);
                cmd.Parameters.AddWithValue("descriere_caz", caz.Descriere);
                
                int rowsAffected = cmd.ExecuteNonQuery();
                if (rowsAffected != 0)
                {
                    caz = null!;
                }
            }
        }
        logger.Info("Caz - save - Exit");
        return caz;
    }
    
    
    
}