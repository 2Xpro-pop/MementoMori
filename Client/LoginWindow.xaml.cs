using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using Client.Models;
using Client.Services;
using Client.Services.CommandInvoker;
using Client.Services.HostedService;
using Microsoft.Extensions.DependencyInjection;

namespace Client;
/// <summary>
/// Логика взаимодействия для LoginWindow.xaml
/// </summary>
public partial class LoginWindow : Window
{
    public event Action OnLogin;
    public LoginWindow()
    {
        InitializeComponent();

        var services = App.Current.Services;

        var login = services.GetRequiredService<LoginService>();


        login.Login = async (login, password) =>
        {
            var connectionContext = services.GetRequiredService<ConnectionContext>();
            var commandInvoker = services.GetRequiredService<ICommandInvoker>();

            commandInvoker.InvokeCommand(100, login, password);

            connectionContext.OnCommandReceived += (cmdId, data) =>
            {
                if (cmdId == 100)
                {
                    var json = Encoding.UTF8.GetString(data);
                    var result = JsonSerializer.Deserialize<Receptionist>(json, new JsonSerializerOptions()
                    {
                        PropertyNameCaseInsensitive = true
                    });
                    if (result.BranchOfficeId > 0)
                    {
                        var receptionist = services.GetRequiredService<Receptionist>();
                        receptionist.BranchOfficeId = result.BranchOfficeId;
                        receptionist.Login = result.Login;
                        receptionist.Password = result.Password;
                        Dispatcher.Invoke(() =>
                        {
                            OnLogin?.Invoke();
                            Close();
                            var mw = App.Current.MainWindow;
                            mw.Show();
                            mw.Activate();
                        });
                        
                    }
                    else
                    {
                        MessageBox.Show("Неверный логин или пароль");
                    }

                }
            };

        };

        OnLogin += () =>
        {

        };
    }

}
