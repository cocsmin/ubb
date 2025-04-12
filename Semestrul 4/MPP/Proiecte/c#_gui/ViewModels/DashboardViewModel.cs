using System;
using System.Collections.ObjectModel;
using System.Reactive;
using Avalonia.Controls;
using Avalonia.Controls.ApplicationLifetimes;
using csharp_avalonia.domain;
using csharp_avalonia.service;
using ReactiveUI;

namespace csharp_avalonia.ViewModels;

public class DashboardViewModel : ReactiveObject
{
    private readonly CazService cazService;
    private readonly DonatieService donatieService;
    private readonly DonatorService donatorService;

    private StackPanel CazuriContainer;
    // Poți adăuga și alte servicii dacă este nevoie
    public ObservableCollection<CazDTO> CazuriDTO { get; } = new ObservableCollection<CazDTO>();

    public ReactiveCommand<Unit, Unit> LogoutCommand { get; }
    public ReactiveCommand<Unit, Unit> OpenDonationCommand { get; }

    public DashboardViewModel(CazService cazService1, DonatieService donatieService1, DonatorService donatorService1)
    {
        cazService = cazService1;
        donatieService = donatieService1;
        donatorService = donatorService1;

        LogoutCommand = ReactiveCommand.Create(Logout);
        OpenDonationCommand = ReactiveCommand.Create(OpenDonation);

        LoadData();
    }

    private void LoadData()
    {
        var cazuri = cazService.FindAll();
        var dtoList = cazService.CreateCazDTOList(cazuri, donatieService);
        Console.WriteLine($"Found {dtoList.Count} cazuri");
        CazuriDTO.Clear();
        foreach (var dto in dtoList)
        {
            
            CazuriDTO.Add(dto);
        }
    }

    private void Logout()
    {
        if (App.Current.ApplicationLifetime is IClassicDesktopStyleApplicationLifetime desktop)
        {
            desktop.MainWindow.Close();
            var loginView = new Views.LoginView();
            desktop.MainWindow = new MainWindow
            {
                Content = loginView,
                Title = "Login",
                Width = 400,
                Height = 300
            };
            desktop.MainWindow.Show();
        }
    }

    private void OpenDonation()
    {
        var donationView = new Views.DonationView();
        donationView.DataContext = new DonationViewModel(donatieService, donatorService, cazService);
        var donationWindow = new Avalonia.Controls.Window
        {
            Title = "Inregistrare Donatie",
            Content = donationView,
            Width = 600,
            Height = 400
        };
        donationWindow.Show();
    }
}