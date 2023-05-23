using System;
using System.Buffers.Binary;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Services.CommandInvoker;
public static class BinaryMessageEncoder
{
    public static byte[] EncodeParameters(params object[] parameters)
    {
        var list = new List<byte>();

        foreach(var param in parameters)
        {
            switch (param)
            {
                case int i:
                    list.AddRange(Int(i));
                    break;
                case short s:
                    list.AddRange(Short(s));
                    break;
                case long l:
                    list.AddRange(Long(l));
                    break;
                case float f:
                    list.AddRange(Float(f));
                    break;
                case double d:
                    list.AddRange(Double(d));
                    break;
                case bool b:
                    list.AddRange(Bool(b));
                    break;
                case string s:
                    list.AddRange(String(s));
                    break;
                default:
                    throw new ArgumentException($"Unknown parameter type: {param.GetType()}");
            }
        }

        return list.ToArray();
    }
    public static byte[] Int(int value)
    {
        var bytes = new byte[4];
        BinaryPrimitives.WriteInt32BigEndian(bytes, value);
        return bytes;
    }

    public static byte[] Short(short value)
    {
        var bytes = new byte[2];
        BinaryPrimitives.WriteInt16BigEndian(bytes, value);
        return bytes;
    }

    public static byte[] Long(long value)
    {
        var bytes = new byte[8];
        BinaryPrimitives.WriteInt64BigEndian(bytes, value);
        return bytes;
    }

    public static byte[] Float(float value)
    {
        var bytes = new byte[4];
        BinaryPrimitives.WriteSingleBigEndian(bytes, value);
        return bytes;
    }

    public static byte[] Double(double value)
    {
        var bytes = new byte[8];
        BinaryPrimitives.WriteDoubleBigEndian(bytes, value);
        return bytes;
    }

    public static byte[] Bool(bool value)
    {
        var bytes = new byte[1];
        bytes[0] = value ? (byte)1 : (byte)0;
        return bytes;
    }

    public static byte[] String(string value)
    {
        var data = Encoding.UTF8.GetBytes(value);
        var size = Int(data.Length);
        var bytes = new byte[size.Length + data.Length];
        size.CopyTo(bytes, 0);
        data.CopyTo(bytes, size.Length);
        return bytes;
    }
}