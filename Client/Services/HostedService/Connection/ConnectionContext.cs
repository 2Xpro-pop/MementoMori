using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using System.Threading;
using System.Threading.Tasks;
using Client.Models;
using Client.Services.CommandInvoker;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Options;

namespace Client.Services.HostedService;
public class ConnectionContext
{
    private readonly ConnectionOptions _connectionOptions;

    private TcpClient? _socket;

    public ConnectionContext(IOptionsMonitor<ConnectionOptions> connectionOptions)
    {
        _connectionOptions = connectionOptions.CurrentValue;

        OnCommandReceived = (commandId, _) =>
        {
            if (_connectionOptions.Commands.TryGetValue(commandId, out var commandName))
            {
                Console.WriteLine($"Command {commandName} received");
            }
        };
    }

    public event Action<short, byte[]> OnCommandReceived;

    public async void Connect()
    {
        try
        {
            _socket?.Dispose();
        }
        catch { }
        finally
        {
            _socket = new TcpClient(_connectionOptions.Host, _connectionOptions.Port);
        }
        var ns = _socket.GetStream();
        while (_socket.Connected)
        {
            await Task.Delay(100);
            try
            {
                
                if (!ns.DataAvailable)
                {
                    continue;
                }

                var commandToken = ns.ReadCommand();

                OnCommandReceived(commandToken.Id, commandToken.Bytes);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                if(e is SocketException || e is IOException)
                {
                    break;
                }
            }
        }
    }

    public TcpClient TcpClient => _socket ?? throw new InvalidOperationException("Connection is not established");


    public async Task ImmitedBudgetHistory()
    {
        var allBudgetHistory = new List<BudgetHistory>();
        for (var i = 0; i < 10; i++)
        {
            await Task.Delay(Random.Shared.Next(100, 370));
            var budgetHistory = GenerateRandomBudgetHistory();
            var json = JsonSerializer.Serialize(budgetHistory);
            allBudgetHistory.Add(budgetHistory);
            OnCommandReceived(10, BinaryMessageEncoder.String(json));
        }

        for (var i = 0; i < 15; i++)
        {
            await Task.Delay(Random.Shared.Next(1000, 2000));
            var budgetHistory = GenerateRandomBudgetHistory();
            var cmdId = (short)Random.Shared.Next(10, 13);
            if (cmdId > 10)
            {
                budgetHistory.Id = allBudgetHistory[Random.Shared.Next(0, allBudgetHistory.Count)].Id;
            }
            var json = JsonSerializer.Serialize(budgetHistory);
            OnCommandReceived(cmdId, BinaryMessageEncoder.String(json));
        }


    }

    private BudgetHistory GenerateRandomBudgetHistory() => new()
    {
        Id = Random.Shared.Next(1, 1_000_000_000),
        BranchOfficeId = 1,
        Action = (Random.Shared.NextDouble() - 0.5) * 100,
        Description = Random.Shared.Next(1, 100).ToString()
    };
}
