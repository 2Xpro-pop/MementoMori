using System;
using System.Collections.Generic;
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
using System.Windows.Shapes;
using Client.Models;
using Client.Services;
using Microsoft.Extensions.DependencyInjection;

namespace Client.Modals;
/// <summary>
/// Логика взаимодействия для SchedulePopup.xaml
/// </summary>
public partial class SchedulePopup : Window, IPopup<Schedule>
{
    public SchedulePopup()
    {
        InitializeComponent();
    }

    public void Send(Schedule data)
    {
        var services = App.Current.Services;
        var commandInvoker = services.GetRequiredService<ICommandInvoker>();

        var cancel = new Button()
        {
            Content = "Отменить"
        };
        cancel.Click += (s, e) =>
        {
            commandInvoker.InvokeCommand(51, data.Id);
            Close();
        };

        var ok = new Button()
        {
            Content = "OK"
        };
        ok.Click += (s, e) =>
        {
            Close();
        };

        var end = new Button()
        {
            Content = "Завершить"
        };
        end.Click += (s, e) =>
        {
            commandInvoker.InvokeCommand(51, data.Id);
            commandInvoker.InvokeCommand(10, data.Price, $"Оплата клиента '{data.Patient.FullName}' +${data.Price}. Принимающий врач '{data.Personal.FullName}'", data.BranchOfficeId);
            Close();
        };

        Content = new StackPanel()
        {
            Children =
            {
                new Label()
                {
                    Content = $"Время приема {data.Date:G}",
                    Foreground = Brushes.White
                    
                },
                new Label()
                {
                    Content = $"Пациент {data.Patient.FullName}",
                    Foreground = Brushes.White
                },
                new Label()
                {
                    Content = $"Врач {data.Personal.FullName}",
                    Foreground = Brushes.White
                },
                ok,
                cancel,
                end,
            }
        };
    }

}
