using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;
using Client.Models;
using Client.Services.HostedService;

namespace Client.Services;
public class ScheduleService : ModelWatcherService<Schedule>
{
    private readonly Receptionist _receptionist;
    private readonly ICommandInvoker _invoker;
    public ScheduleService(ConnectionContext connectionContext, Receptionist receptionist, ICommandInvoker invoker) : base(connectionContext)
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

        if (cmdId == 53)
        {

            var json = Encoding.UTF8.GetString(data);
            var options = new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true,
            };
            options.Converters.Add(new CustomDateFormmatter("yyyy-MM-dd HH:mm:ss.f"));
            var list = JsonSerializer.Deserialize<List<Schedule>>(json, options);
            list.RemoveAll(x => x.BranchOfficeId != branchOfficeId);
            Models.Clear();
            Models.AddRange(list);
            modelChangeHandler?.Invoke();
        }

        Add(cmdId == 50, data);
        Remove(cmdId == 51, data);
        Update(cmdId == 52, data);
    }

}


