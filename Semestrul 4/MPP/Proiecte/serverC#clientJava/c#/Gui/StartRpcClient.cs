using Avalonia;
using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using System;
using System.Configuration;
using Avalonia.Controls.ApplicationLifetimes;
using clientavalonia;
using networkingnet.RpcProtocol; // Adăugați pentru citirea setărilor din App.config


namespace clientavalonia.Gui
{
    public class StartRpcClient : Application
    {
        private static int defaultChatPort = 55555;
        private static string defaultServer = "localhost";

        public override void Initialize()
        {
             AvaloniaXamlLoader.Load(this); // Încarcă resursele XAML
        }

        public override void OnFrameworkInitializationCompleted()
        {
            base.OnFrameworkInitializationCompleted();

            // Încărcarea setărilor clientului din App.config
            string serverIP = ConfigurationManager.AppSettings["server.host"] ?? defaultServer;
            int serverPort = defaultChatPort;

            // Verifică dacă există portul în fișierul de configurare
            string portValue = ConfigurationManager.AppSettings["server.port"];
            if (portValue != null && int.TryParse(portValue, out int parsedPort))
            {
                serverPort = parsedPort;
            }

            Console.WriteLine($"Using server IP: {serverIP}, port: {serverPort}");

            // Crearea serverului RPC
            var server = new ServicesJsonProxy(serverIP, serverPort);

            // Deschiderea ferestrei de Login
            var loginWindow = new LoginView(server); // presupun că LoginView primește serverul în constructor

            if (ApplicationLifetime is IClassicDesktopStyleApplicationLifetime desktop)
            {
                desktop.MainWindow = loginWindow;
            }
        }

        // Metoda Main pentru a porni aplicația
        [STAThread] // Necesare pentru aplicațiile Windows
        public static void Main(string[] args)
        {
            // Se începe aplicația Avalonia
            BuildAvaloniaApp().StartWithClassicDesktopLifetime(args);
        }

        // Metoda de configurare a aplicației
        public static AppBuilder BuildAvaloniaApp()
            => AppBuilder.Configure<StartRpcClient>()
                .UsePlatformDetect();

    }
}