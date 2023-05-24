using System;
using System.Collections.Generic;
using System.Diagnostics.Metrics;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using Client.Services;
using Microsoft.AspNetCore.Components.WebView.Wpf;
using Microsoft.Extensions.DependencyInjection;

namespace Client;
/// <summary>
/// Interaction logic for MainWindow.xaml
/// </summary>
public partial class MainWindow : Window
{
    public static MainWindow Instance
    {
        get;
        private set;
    }
    public MainWindow()
    {
        InitializeComponent();
        Instance = this;
    }


    public static void ShowPopup<T,U>(T data) where U : Window, IPopup<T>, new()
    {
        var window = new U();
        window.Send(data);
        window.Owner = Instance;
        window.ShowDialog();
    }

}
