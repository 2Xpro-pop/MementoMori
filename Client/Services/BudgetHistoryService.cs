using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Models;
using Client.Services.HostedService;

namespace Client.Services;
public class BudgetHistoryService : ModelWatcherService<BudgetHistory>
{
    private readonly Receptionist _receptionist;
    public BudgetHistoryService(ConnectionContext connectionContext, Receptionist receptionist): base(connectionContext)
    {
        _receptionist = receptionist;
    }

    protected override void OnCommandReceived(short cmdId, byte[] data)
    {
        branchOfficeId = _receptionist.BranchOfficeId;

        base.OnCommandReceived(cmdId, data);

        if (branchOfficeId is null)
        {
            return;
        }

        Add(cmdId == 10, data);
        Remove(cmdId == 11, data);
        Update(cmdId == 12, data);
    }
}
