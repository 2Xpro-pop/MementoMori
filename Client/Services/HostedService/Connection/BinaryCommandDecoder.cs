using System;
using System.Buffers.Binary;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace Client.Services.HostedService;
public static class BinaryCommandDecoder
{
    public static CommandToken ReadCommand(this NetworkStream stream)
    {
        var commandIdBytes = new byte[2];
        stream.Read(commandIdBytes, 0, 2);

        var commandId = Short(commandIdBytes);

        var commandLengthBytes = new byte[4];
        stream.Read(commandLengthBytes, 0, 4);

        var commandLength = Int(commandLengthBytes);

        var commandBytes = new byte[commandLength];
        stream.Read(commandBytes, 0, commandLength);

        return new CommandToken
        {
            Id = commandId,
            Bytes = commandBytes
        };
    }

    public static short Short(byte[] bytes)
    {
        return BinaryPrimitives.ReadInt16BigEndian(bytes);
    }

    public static int Int(byte[] bytes)
    {
        return BinaryPrimitives.ReadInt32BigEndian(bytes);
    }

    public static long Long(byte[] bytes)
    {
        return BinaryPrimitives.ReadInt64BigEndian(bytes);
    }

    public static float Float(byte[] bytes)
    {
        return BinaryPrimitives.ReadSingleBigEndian(bytes);
    }

    public static double Double(byte[] bytes)
    {
        return BinaryPrimitives.ReadDoubleBigEndian(bytes);
    }

    public static bool Bool(byte[] bytes)
    {
        return bytes[0] == 1;
    }

    public static string String(byte[] bytes)
    {
        var size = Int(bytes.Take(4).ToArray());
        return Encoding.UTF8.GetString(bytes.Skip(4).Take(size).ToArray());
    }
}
