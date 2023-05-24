using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Client.Services.CommandInvoker;
using Client.Services.HostedService;

namespace Client.Services;
internal class BinaryCommandInvoker : ICommandInvoker
{
    private readonly ConnectionContext _connectionContext;

    public BinaryCommandInvoker(ConnectionContext connectionContext)
    {
        _connectionContext = connectionContext;
    }

    public void InvokeCommand(short commandId, params object[] args)
    {
        var bytes = new List<byte>();
        bytes.AddRange(BinaryMessageEncoder.Short(commandId));
        bytes.AddRange(BinaryMessageEncoder.EncodeParameters(args));
        bytes.InsertRange(0, BinaryMessageEncoder.Int(bytes.Count));
        var ns = _connectionContext.TcpClient.GetStream();
        ns.Write(bytes.ToArray());
        ns.Flush();
    }
}
