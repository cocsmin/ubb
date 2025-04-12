using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using csharp_avalonia.domain;
using log4net;
using Microsoft.Data.SqlClient;

namespace csharp_avalonia.repository;

public class VoluntarRepo : VoluntarRepo0
{
    private static readonly ILog logger = LogManager.GetLogger("VoluntarRepo");

    public Voluntar findOne(int id)
    {
        logger.Info("Voluntar - findOne - Enter");
        Voluntar voluntar = null;
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "SELECT * FROM VOLUNTARI WHERE id_voluntar = @id_voluntar";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@id_voluntar", id);
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        int id_voluntar = reader.GetInt32(0);
                        string username = reader.GetString(1);
                        string password = reader.GetString(2);
                        string nume = reader.GetString(3);
                        
                        voluntar = new Voluntar(username, password, nume);
                        voluntar.Id = id_voluntar;
                    }
                }
            }
        }
        logger.Info("Voluntar - findOne - Exit");
        return voluntar;
    }

    public IEnumerable<Voluntar> findAll()
    {
        logger.Info("Voluntar - findAll - Enter");
        List<Voluntar> voluntari = new List<Voluntar>();
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            var sql = "SELECT * FROM VOLUNTARI";
            using (var cmd = new SqlCommand(sql, connection))
            {
                using (var reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int id_voluntar = reader.GetInt32(0);
                        string username = reader.GetString(1);
                        string password = reader.GetString(2);
                        string nume = reader.GetString(3);
                        
                        Voluntar voluntar = new Voluntar(username, password, nume);
                        voluntar.Id = id_voluntar;
                        
                        voluntari.Add(voluntar);
                    }
                }
            }
        }
        logger.Info("Voluntar - findAll - Exit");
        return voluntari;
    }

    public Voluntar FindByUsername(string username)
    {
        Voluntar voluntar = null;
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            string sql = "SELECT * FROM VOLUNTARI WHERE username = @username";
            using (var command = new SqlCommand(sql, connection))
            {
                command.Parameters.AddWithValue("@username", username);
                using (var reader = command.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        int id = reader.GetInt32(reader.GetOrdinal("id_voluntar"));
                        string user = reader.GetString(reader.GetOrdinal("username"));
                        string parola = reader.GetString(reader.GetOrdinal("parola"));
                        string nume = reader.GetString(reader.GetOrdinal("nume_voluntar"));
                        voluntar = new Voluntar(id, user, parola, nume);
                    }
                }
            }
        }
        return voluntar;
    }
    public Voluntar save(Voluntar voluntar)
    {
        logger.Info("Voluntar - save - Enter");
        string hashedPassword = BCrypt.Net.BCrypt.HashPassword(voluntar.Password);
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            
            var sql = "INSERT INTO VOLUNTARI (username, parola, nume_voluntar) VALUES (@username, @parola, @nume_voluntar)";
            using (var cmd = new SqlCommand(sql, connection))
            {
                cmd.Parameters.AddWithValue("@username", voluntar.Username);
                cmd.Parameters.AddWithValue("@parola", hashedPassword);
                cmd.Parameters.AddWithValue("@nume_voluntar", voluntar.Nume_voluntar);
                
                int rowsAffected = cmd.ExecuteNonQuery();
                if (rowsAffected != 0)
                {
                    voluntar = null;
                }
            }
        }
        logger.Info("Voluntar - save - Exit");
        return voluntar;
    }
    
    public Voluntar Login(string username, string password)
    {
        Voluntar voluntar = null;
        using (var connection = new SqlConnection(ConfigurationManager.ConnectionStrings["Test"].ConnectionString))
        {
            connection.Open();
            using (var ps = new SqlCommand("SELECT * FROM VOLUNTARI WHERE username = @username", connection))
            {
                ps.Parameters.AddWithValue("@username", username);
                using (var rs = ps.ExecuteReader())
                {
                    if (rs.Read())
                    {
                        int id = rs.GetInt32(rs.GetOrdinal("id_voluntar"));
                        string user = rs.GetString(rs.GetOrdinal("username"));
                        string storedHash = rs.GetString(rs.GetOrdinal("parola"));
                        string nume = rs.GetString(rs.GetOrdinal("nume_voluntar"));
                            
                        //muta in service
                        if (BCrypt.Net.BCrypt.Verify(password, storedHash))
                        {
                            voluntar = new Voluntar(user, storedHash, nume);
                            voluntar.Id = id;
                        }
                        else
                        {
                            logger.Warn("Incorrect password for user: " + username);
                        }
                    }
                }
            }
        }
        logger.Info($"Login {voluntar.ToString()} - Exit");
        return voluntar;
    }
}
