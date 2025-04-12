using System;
using System.ComponentModel;
using System.Reactive;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;
using System.Windows.Input;
using Avalonia.Controls;
using Avalonia.Controls.ApplicationLifetimes;
using csharp_avalonia.Views;
using ReactiveUI;
using csharp_avalonia.domain;
using csharp_avalonia.repository;
using csharp_avalonia.service;
using csharp_avalonia.utils;

namespace csharp_avalonia.ViewModels;

public class LoginViewModel : INotifyPropertyChanged
{
    private readonly VoluntarService voluntarService;

    private string username;

    public string Username
    {
        get => username;
        set
        {
            username = value;
            OnPropertyChanged();
            ((RelayCommand)LoginCommand).RaiseCanExecuteChanged(); // Update button state
        }
}

    private string password;

    public string Password
    {
        get => password;
        set
        {
            password = value;
            OnPropertyChanged();
            ((RelayCommand)LoginCommand).RaiseCanExecuteChanged(); // Update button state
        }    }

    public ICommand LoginCommand { get; }

    private StackPanel stackPanel;
    public LoginViewModel(VoluntarService voluntarService1, StackPanel stackPanel1)
    {
        voluntarService = voluntarService1;
        this.stackPanel = stackPanel1;
        LoginCommand = new RelayCommand(Login, CanLogin);
    }

    private void Login(object? parameter)
    {
        try
        {
            var voluntar = voluntarService.Login(Username, Password);
            if (voluntar != null)
            {
                Console.WriteLine($"Login succeeded for {Username}");
                var dashboard = new DashboardViewModel(new CazService(new CazRepo()),
                    new DonatieService(new DonatieRepo()), new DonatorService(new DonatorRepo()));
                var dashboardView = new DashboardView
                {
                    DataContext = dashboard
                };
                var window = new Window
                {
                    Title = "Dashboard",
                    Content = dashboardView,
                    Width = 800,
                    Height = 600,
                };
                window.Show();
                if (App.Current.ApplicationLifetime is IClassicDesktopStyleApplicationLifetime app)
                {
                    app.MainWindow.Close();
                    app.MainWindow = window;
                }
                
                
            }
            else
            {
                Console.WriteLine("Login failed");
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine("Login failed: " + ex.Message);
        }
    }
    private bool CanLogin(object? parameter)
    {
        return !string.IsNullOrEmpty(Username) && !string.IsNullOrEmpty(Password);
    }
    
    public event PropertyChangedEventHandler? PropertyChanged;

    protected void OnPropertyChanged([CallerMemberName] string propertyName = "")
    {
        PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
    }
}


