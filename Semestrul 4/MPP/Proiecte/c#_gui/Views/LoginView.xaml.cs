using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using csharp_avalonia.ViewModels;
using csharp_avalonia.repository;
using csharp_avalonia.service;

namespace csharp_avalonia.Views
{
    public partial class LoginView : UserControl
    {
        public LoginView()
        {
            InitializeComponent();
            DataContext = new LoginViewModel(new VoluntarService(new VoluntarRepo()), new StackPanel());
        }

        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }
    }
}