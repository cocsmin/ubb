using System;
using System.Collections.ObjectModel;
using Avalonia.Controls;
using Avalonia.Interactivity;
using Avalonia.Markup.Xaml;
using modelnet;
using persistancenet;
using servicesnet;

namespace clientavalonia.Gui
{
    public partial class DashboardView : Window,IProjectObserver
    {
        private IProjectServices server;
        private Voluntar user;
        public ObservableCollection<CazDTO> CazList { get; } = new ObservableCollection<CazDTO>();

        // Constructorul pentru a primi serviciile
        public DashboardView(IProjectServices server,Voluntar user )
        {
            InitializeComponent();
            this.server = server;
            this.user = user;
            DataContext = this;
            LoadTableData();
        }

        // Încărcarea datelor în tabel
        private void LoadTableData()
        {

            var cazuri = server.createCazDTOList();
            CazList.Clear();
            foreach (var caz in cazuri)
            {
                CazList.Add(caz);
            }
        }

        /*
        private void OpenCautare(object sender, RoutedEventArgs e)
        {
            var cautareView = new CautareView(server);
            cautareView.Show();
             //this.Close();
        }
        */

        // Evenimentul pentru butonul "Înscriere Participant"
        private void OpenDonation(object sender, RoutedEventArgs e)
        {
            var donationView = new DonationView(server);
            donationView.Show();
        }

        // Evenimentul pentru butonul "Logout"
        private void HandleLogout(object sender, RoutedEventArgs e)
        {
            //this.Close();
            server.logout(user.Username, this);
            this.Hide();
        }

        public void UpdateTable()
        {
            LoadTableData();
        }

        public void notifyObservers(Donatie dono)
        {
            Avalonia.Threading.Dispatcher.UIThread.Post(() =>
            {
                UpdateTable(); // Actualizează tabela UI
            });

        }
    }
}
