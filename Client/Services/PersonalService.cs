using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Models;
using Client.Services.HostedService;

namespace Client.Services;
public class PersonalService : ModelWatcherService<Personal>
{
    private readonly Receptionist _receptionist;
    private readonly ICommandInvoker _invoker;
    public PersonalService(ConnectionContext connectionContext, Receptionist receptionist, ICommandInvoker invoker) : base(connectionContext)
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

        if (branchOfficeId is null)
        {
            return;
        }

        if (cmdId == 43)
        {
            var json = Encoding.UTF8.GetString(data);
            var list = JsonSerializer.Deserialize<List<Personal>>(json, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            });
            list.RemoveAll(x => x.BranchOfficeId != branchOfficeId);
            Models.Clear();
            Models.AddRange(list);
            modelChangeHandler?.Invoke();
        }

        Add(cmdId == 40, data);
        Remove(cmdId == 41, data);
        Update(cmdId == 42, data);
    }
}

