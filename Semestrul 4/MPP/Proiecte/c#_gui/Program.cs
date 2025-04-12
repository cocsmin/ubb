using System;
using System.IO;
using System.Reflection;
using System.Xml;
using csharp_avalonia.domain;
using csharp_avalonia.repository;
using log4net;

using Avalonia;
using Avalonia.ReactiveUI;
using System;

namespace csharp_avalonia
{
    class Program
    {
        [STAThread]
        public static void Main(string[] args)
        {
            var configFilePath =
                "log4netCustom.xml";
            var log4netConfig = new XmlDocument();
            log4netConfig.Load(File.OpenRead(configFilePath));

            var repo = LogManager.CreateRepository(Assembly.GetEntryAssembly(),
                typeof(log4net.Repository.Hierarchy.Hierarchy));

            var log4NetXml = log4netConfig["MyCustomSetting"]?["log4net"];
            log4net.Config.XmlConfigurator.Configure(repo, log4NetXml);
            
            
            BuildAvaloniaApp()
                .StartWithClassicDesktopLifetime(args);
        }

        public static AppBuilder BuildAvaloniaApp()
            => AppBuilder.Configure<App>()
                .UsePlatformDetect()       // Detectează platforma pe care rulează (macOS, Windows, etc.)
                .LogToTrace()              // Activează logarea
                .UseReactiveUI();          // Dacă folosești ReactiveUI (opțional)
    }
}



//etc
/*
VoluntarRepo repoV = new VoluntarRepo();

Console.WriteLine("Se caută voluntarul cu id 1..."); 
Voluntar voluntar = repoV.findOne(1);

Console.WriteLine("Voluntar găsit: " + voluntar);
Console.WriteLine("Se afișează toți voluntarii..."); 

var voluntari = repoV.findAll();
foreach (var vol in voluntari) 
    Console.WriteLine(vol);

Console.WriteLine("Se salvează un voluntar nou...");
Voluntar newVoluntar = new Voluntar("userTest", "passTest", "NumeTest");
repoV.save(newVoluntar);
Console.WriteLine("Test logger finalizat.");
*/
        
    

