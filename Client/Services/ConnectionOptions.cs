using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.Extensions.Options;
using YamlDotNet.Serialization;

namespace Client.Services;

public class ConnectionOptions
{
    public string Host
    {
        get; set;
    } = "localhost";

    public int Port
    {
        get; set;
    } = 5000;

    public Dictionary<short, string> Commands
    {
        get; set;
    } = new Dictionary<short, string>();
}
