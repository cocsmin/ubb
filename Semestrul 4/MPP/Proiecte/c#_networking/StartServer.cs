using System.Configuration;
using persistancenet;
using servicesnet;
using networkingnet;
using System.Net.Sockets;
using networkingnet.Utils;

namespace servernet
{

    public class StartServer
    {
        private static int defaultPort = 55555;

        public static void Main(string[] args)
        {
            var serverProps = new Dictionary<string, string>();
            try
            {
                var config = ConfigurationManager.OpenExeConfiguration(ConfigurationUserLevel.None);
                var appSettings = config.AppSettings.Settings;
                foreach (var key in appSettings.AllKeys)
                {
                    serverProps[key] = appSettings[key].Value;
                }

                Console.WriteLine("Server properties set.");
                foreach (var key in serverProps.Keys)
                {
                    Console.WriteLine($"Key: {serverProps[key]}");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine($"Cannot find server properties: {e}");
                return;
            }

            var voluntarRepo = new VoluntarRepo(serverProps["jdbc.url"]);
            var donatorRepo = new DonatorRepo(serverProps["jdbc.url"]);
            var cazRepo = new CazRepo(serverProps["jdbc.url"]);
            var donatieRepo = new DonatieRepo(serverProps["jdbc.url"]);

            var serverImpl = new ProjectServicesImplementation(voluntarRepo, donatorRepo, cazRepo, donatieRepo);

            int serverPort = defaultPort;
            try
            {
                if (serverProps.ContainsKey("port"))
                {
                    serverPort = int.Parse(serverProps["port"]);
                }
            }
            catch (FormatException ex)
            {
                Console.WriteLine($"Wrong port number: {ex.Message}");
                Console.WriteLine($"Using default port {defaultPort}");
            }

            Console.WriteLine($"Starting server on port: {serverPort}");

            var server = new RpcConcurrentServer(serverPort, serverImpl);
            try
            {
                server.Start();
            }
            catch (Exception e)
            {
                Console.WriteLine($"Failed to start server: {e.Message}");
            }
            finally
            {
                try
                {
                    server.Stop();
                }
                catch (Exception e)
                {
                    Console.WriteLine($"Failed to stop server: {e.Message}");
                }
            }

        }
    }
}
