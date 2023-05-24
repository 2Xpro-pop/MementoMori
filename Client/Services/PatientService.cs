using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Models;
using Client.Services.HostedService;

namespace Client.Services;
public class PatientService: ModelWatcherService<Patient>
{
    private readonly Receptionist _receptionist;
    private readonly ICommandInvoker _invoker;
    public PatientService(ConnectionContext connectionContext, Receptionist receptionist, ICommandInvoker invoker) : base(connectionContext)
    {
        _receptionist = receptionist;
        _invoker = invoker;
    }

    public override void ListenBranchOffice(int id)
    {
        base.ListenBranchOffice(id);
    }

    protected override void OnCommandReceived(short cmdId, byte[] data)
    {
        branchOfficeId = _receptionist.BranchOfficeId;

        base.OnCommandReceived(cmdId, data);

        if (cmdId == 33)
        {
            var json = Encoding.UTF8.GetString(data);
            var list = JsonSerializer.Deserialize<List<Patient>>(json, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            });
            
            Models.Clear();
            Models.AddRange(list);
            modelChangeHandler?.Invoke();
        }

        Add(cmdId == 30, data);
        Remove(cmdId == 31, data);
        Update(cmdId == 32, data);
    }
}
