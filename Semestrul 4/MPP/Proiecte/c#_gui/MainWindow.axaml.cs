using Avalonia.Controls;
using Avalonia.Markup.Xaml;

namespace csharp_avalonia
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }
    
        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }
    }
}