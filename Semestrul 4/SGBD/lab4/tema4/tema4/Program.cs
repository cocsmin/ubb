using System;
using System.Configuration;
using System.Data.SqlClient;
using System.Threading;
using Microsoft.Data.SqlClient;

namespace DeadlockDemo
{
    class Program
    {
        static string connectionString = ConfigurationManager.ConnectionStrings["cn"].ConnectionString;

        static int maxRetries = 3;

        static void Main(string[] args)
        {
            Console.WriteLine("DEADLOCK DEMO");

            Thread t1 = new Thread(new ThreadStart(Transaction1));
            Thread t2 = new Thread(new ThreadStart(Transaction2));

            t1.Start();
            t2.Start();

            t1.Join();
            t2.Join();

            Console.WriteLine("END");
        }

        static void Transaction1()
        {
            int retries = 0;
            while (!RunTransaction("Deadlock1_MancareMeniu", "Transaction 1") && retries < maxRetries)
            {
                retries++;
                Console.WriteLine($"[Transaction 1] Retry {retries}/{maxRetries}");
                Thread.Sleep(1000); 
            }

            if (retries == maxRetries)
                Console.WriteLine("[Transaction 1] Aborted after max retries.");
        }

        static void Transaction2()
        {
            int retries = 0;
            while (!RunTransaction("Deadlock2_MancareMeniu", "Transaction 2") && retries < maxRetries)
            {
                retries++;
                Console.WriteLine($"[Transaction 2] Retry {retries}/{maxRetries}");
                Thread.Sleep(1000);
            }

            if (retries == maxRetries)
                Console.WriteLine("[Transaction 2] Aborted after max retries.");
        }

        static bool RunTransaction(string procedureName, string label)
        {
            bool success = false;

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                try
                {
                    connection.Open();
                    SqlCommand command = new SqlCommand(procedureName, connection)
                    {
                        CommandType = System.Data.CommandType.StoredProcedure
                    };

                    Console.WriteLine($"{label} executing {procedureName}...");
                    command.ExecuteNonQuery();

                    Console.WriteLine($"{label} executed successfully.");
                    success = true;
                }
                catch (SqlException ex)
                {
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine($"{label} DEADLOCK detected: {ex.Message}");
                    }
                    else
                    {
                        Console.WriteLine($"{label} ERROR: {ex.Message}");
                    }
                }
            }

            return success;
        }
    }
}