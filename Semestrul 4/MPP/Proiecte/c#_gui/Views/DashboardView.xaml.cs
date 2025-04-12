using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using csharp_avalonia.repository;
using csharp_avalonia.service;
using csharp_avalonia.ViewModels;

namespace csharp_avalonia.Views;

public partial class DashboardView : UserControl
{
        

    public DashboardView()
    {
        InitializeComponent();
        DataContext = new DashboardViewModel(new CazService(new CazRepo()), new DonatieService(new DonatieRepo()), new DonatorService(new DonatorRepo()));
    }

    private void InitializeComponent()
    {
        AvaloniaXamlLoader.Load(this);
    }
}