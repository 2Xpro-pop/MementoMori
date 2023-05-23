using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Services.HostedService;
public class CommandToken
{
    public short Id
    {
        get; set;
    }

    public byte[]? Bytes
    {
        get; set;
    }
}
