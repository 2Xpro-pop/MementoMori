using System;
using System.Collections.Generic;
using System.Text.Json;
using Client.Models;
using Client.Services.HostedService;

namespace Client.Services;

public abstract class ModelWatcherService<T> where T : IHaveBranchOfficeId
{
    protected readonly ConnectionContext connectionContext;

    protected int? branchOfficeId = null;

    public event Action OnModelsChanged;

    public List<T> Models
    {
        get;
    }

    public ModelWatcherService(ConnectionContext connectionContext)
    {
        this.connectionContext = connectionContext;
        Models = new List<T>();
    }

    protected void ParseDataAndValidate(byte[] data, Action<T> action)
    {
        var json = BinaryCommandDecoder.String(data);
        var model = JsonSerializer.Deserialize<T>(json);

        if (branchOfficeId is null) throw new Exception("Branch office id is not set");

        if (model.BrancheOfficeId != branchOfficeId)
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
        ParseDataAndValidate(data, model =>
        {
            if (condition)
            {
                Models.Remove(model);
                OnModelsChanged?.Invoke();
            }
        });
    }

    protected void Add(bool condition, byte[] data)
    {
        if (condition)
        {
            ParseDataAndValidate(data, Models.Add);
            OnModelsChanged?.Invoke();
        }
    }

    protected void Update(bool condition, byte[] data)
    {
        ParseDataAndValidate(data, model =>
        {
            if (condition)
            {
                var index = Models.FindIndex(m => m.Id == model.Id);
                Models[index] = model;
                OnModelsChanged?.Invoke();
            }
        });
    }

    public void ListenBranchOffice(int id)
    {
        branchOfficeId = id;

        connectionContext.OnCommandReceived += OnCommandReceived;
    }

}