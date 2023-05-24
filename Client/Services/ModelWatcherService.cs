using System;
using System.Collections.Generic;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using Client.Models;
using Client.Services.HostedService;

namespace Client.Services;

public abstract class ModelWatcherService<T> where T : IHaveBranchOfficeId
{
    protected readonly ConnectionContext connectionContext;

    protected int? branchOfficeId = null;

    public event Action OnModelsChanged
    {
        add => modelChangeHandler += value;
        remove => modelChangeHandler -= value;
    }
    protected Action modelChangeHandler;

    public List<T> Models
    {
        get;
    }

    public ModelWatcherService(ConnectionContext connectionContext)
    {
        this.connectionContext = connectionContext;
        Models = new List<T>(256);
    }

    protected void ParseDataAndValidate(byte[] data, Action<T> action)
    {
        var json = Encoding.UTF8.GetString(data);
        var options = new JsonSerializerOptions
        {
            PropertyNameCaseInsensitive = true,
        };
        options.Converters.Add(new CustomDateFormmatter("yyyy-MM-dd HH:mm:ss.f"));
        var model = JsonSerializer.Deserialize<T>(json, options);

        if (branchOfficeId is null) throw new Exception("Branch office id is not set");

        if (model.BranchOfficeId != branchOfficeId && model is not Patient)
        {
            return;
        }
        
        action(model);
    }

    protected virtual void OnCommandReceived(short cmdId, byte[] data)
    {

    }

    protected void Remove(bool condition, byte[] data)
    {
        if (condition)
        {
            ParseDataAndValidate(data, model =>
            {
                var index = Models.FindIndex(m => m.Id == model.Id);
                var cur = Models[index];

                Models.Remove(cur);
                modelChangeHandler?.Invoke();
            });
        }

    }

    protected void Add(bool condition, byte[] data)
    {
        if (condition)
        {
            ParseDataAndValidate(data, Models.Add);
            modelChangeHandler?.Invoke();
        }
    }

    protected void Update(bool condition, byte[] data)
    {
        if (condition)
        {
            ParseDataAndValidate(data, model =>
            {
                var index = Models.FindIndex(m => m.Id == model.Id);
                Models[index] = model;
                modelChangeHandler?.Invoke();
            });
        }
    }

    public virtual void ListenBranchOffice(int id)
    {
        branchOfficeId = id;

        connectionContext.OnCommandReceived += OnCommandReceived;
    }


    ~ModelWatcherService()
    {
        connectionContext.OnCommandReceived -= OnCommandReceived;
    }
}

public class CustomDateFormmatter : JsonConverter<DateTime>
{
    private readonly string _format;
    public CustomDateFormmatter(string format)
    {
        _format = format;
    }
    public override DateTime Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        return DateTime.ParseExact(reader.GetString(), _format, null);
    }
    public override void Write(Utf8JsonWriter writer, DateTime value, JsonSerializerOptions options)
    {
        writer.WriteStringValue(value.ToString(_format));
    }
}