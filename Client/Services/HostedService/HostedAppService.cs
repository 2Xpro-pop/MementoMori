using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.Extensions.Hosting;

namespace Client.Services.HostedService;
public class HostedAppService : IHostedService
{
    private readonly App _app;
    private readonly IServiceProvider _serviceProvider;
    private readonly IHost _host;
    private readonly ConnectionContext _connectionContext;

    public HostedAppService(App app, IServiceProvider serviceProvider, IHost host, ConnectionContext connectionContext)
    {
        _app = app;
        _serviceProvider = serviceProvider;
        _host = host;
        _connectionContext = connectionContext;
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        _connectionContext.Connect();

        _app.InitializeComponent();
        _app.Resources.Add("services", _serviceProvider);
        _app.Run();

        _app.Exit += (sender, args) =>
        {
            App.CancellationTokenSource.Cancel();
        };

    }

    public Task StopAsync(CancellationToken cancellationToken)
    {
        _app.Shutdown();

        return Task.CompletedTask;
    }

}
