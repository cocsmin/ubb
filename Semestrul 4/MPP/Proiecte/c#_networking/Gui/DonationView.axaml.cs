using System;
using System.Collections.Generic;
using Avalonia.Controls;
using Avalonia.Interactivity;
using modelnet;
using MsBox.Avalonia;
using MsBox.Avalonia.Dto;
using MsBox.Avalonia.Enums;
using servicesnet;

namespace clientavalonia.Gui;

public partial class DonationView : Window
{
    private DashboardView mainView;
    private IProjectServices server;
    public DonationView(IProjectServices server)
    {
        InitializeComponent();
        this.server = server;
        DataContext = this;
        LoadCazuri();
    }

    public void setMainController(DashboardView mainController)
    {
        this.mainView = mainController;
    }

    private void LoadCazuri()
    {
        var cazuri = server.findAllCaz();
        comboCazuri.ItemsSource = cazuri;
    }

    private void SaveDonationCommand(object sender, RoutedEventArgs e)
    {
        var caz = comboCazuri.SelectedItem as Caz;
        var numeD = txtNume.Text;
        var adresaD = txtAdresa.Text;
        var telefonD = txtTelefon.Text;
        int suma = int.Parse(txtSumaDonatie.Text);
        if (numeD.Length == 0 || adresaD.Length == 0 || telefonD.Length == 0)
        {
            Console.WriteLine("Toate campruile trebuie completate!");
            return;
        }

        Donator existingD = server.findByFullName(numeD);
        if (existingD != null)
        {
            var donatie = new Donatie(existingD, caz, DateTime.Now, suma);
            server.saveDonatie(donatie);
        }
        else
        {
            Donator donator = new Donator(numeD, adresaD, telefonD);
            Console.WriteLine(donator.Nume_donator);
            donator = server.saveDonator(donator);
            var donatie = new Donatie(donator, caz, DateTime.Now, suma);
            Console.WriteLine(donatie.Donator.Nume_donator);
            server.saveDonatie(donatie);
        }
        ErrorLabel1.Text = "Donatie adaugata cu succes!";
    }

    private void SearchDonorCommand(object sender, RoutedEventArgs e)
    {
        string partialName = txtCautareDonator.Text;
        List<Donator> Donatori = server.searchByName(partialName);
        DonatoriList.ItemsSource = Donatori;
    }

    private void OnCazChanged(object sender, SelectionChangedEventArgs e)
    {
        var selectedCaz = comboCazuri.SelectedItem as Caz;
    }
}