using Avalonia.Controls;
using Avalonia.Interactivity;
using System;
using System.Linq;
using System.Threading.Tasks;
using modelnet;
using MsBox.Avalonia;
using MsBox.Avalonia.Enums;
using servicesnet;

namespace clientavalonia.Gui
{
    public partial class LoginView : Window
    {
        private Voluntar loggedUser;
        private IProjectServices server;

        public LoginView(IProjectServices server)
        {
            InitializeComponent();
            this.server = server;
        }
        
        
        

        // Evenimentul pentru butonul de login
        private void OnLoginClick(object sender, RoutedEventArgs e)
        {
            
            string username = Username.Text.Trim();
            string password = Password.Text.Trim();

            Console.WriteLine(username, password);
            // Verificare dacă utilizatorul și parola nu sunt goale
            if (string.IsNullOrEmpty(username) || string.IsNullOrEmpty(password))
            {
                ErrorLabel.Text = "Completati toate campurile!";
                return;
            }

            if (Authenticate(username, password))
            {
                OpenMainWindow();
            }
            else
            {
                ErrorLabel.Text = "Utilizator sau parola incorecta!";
            }
        }

        // Funcția de autentificare
        private bool Authenticate(string username, string password)
        {
            var utilizatori = server.findAllVoluntari();

            foreach (var utilizator in utilizatori)
            {
                if (utilizator.Username == username)
                {
                    // Compară parola introdusă cu parola salvată (hashată)
                    if (BCrypt.Net.BCrypt.Verify(password, utilizator.Password))
                    {
                        loggedUser = utilizator;
                        return true;
                    }
                }
            }
            return false;
        }

        // Deschiderea ferestrei principale
        private async void OpenMainWindow()
        {
            DashboardView mainView = new DashboardView(server,loggedUser);
            try
            {
                try
                {
                    server.login(loggedUser.Username, loggedUser.Password, mainView);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                }
            }
            catch (ProjectException e)
            {
                MessageBoxManager
                    .GetMessageBoxStandard("Login Eroare", e.Message, ButtonEnum.Ok).ShowWindowDialogAsync(this);
            }

            mainView.Show();
            this.Close();
        }
    }
}
