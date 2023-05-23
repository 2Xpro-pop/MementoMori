using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Client.Models;
using Client.Services;
using Client.Services.HostedService;
using Client.View;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using MudBlazor.Services;

namespace Client;
public class Program
{
    [STAThread]
    public static void Main(string[] args)
    {
        var builder = new HostApplicationBuilder(args);

        builder.Configuration.AddYamlFile("config/connection.yaml", true, true);
        builder.Services.Configure<ConnectionOptions>(builder.Configuration.GetSection("connection"));

        builder.Configuration.AddYamlFile("config/theme.yaml", true, true);
        builder.Services.Configure<Theme>(builder.Configuration.GetSection("theme"));

        builder.Services.AddSingleton<App>();
        builder.Services.AddSingleton<LoginService>();
        builder.Services.AddSingleton<BudgetHistoryService>();
        builder.Services.AddSingleton<ConnectionContext>();
        builder.Services.AddSingleton<Receptionist>();

        builder.Services.AddTransient<ICommandInvoker, BinaryCommandInvoker>();

        builder.Services.AddBlazorWebView();
        builder.Services.AddWpfBlazorWebView();
        builder.Services.AddMudServices(options =>
        {
        });

        builder.Services.AddHostedService<HostedAppService>();

        var host = builder.Build();

        host.StartAsync(App.CancellationTokenSource.Token).Wait();
    }
}
