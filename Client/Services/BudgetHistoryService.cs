﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Client.Models;
using Client.Services.HostedService;
using Microsoft.EntityFrameworkCore.Metadata;

namespace Client.Services;
public class BudgetHistoryService : ModelWatcherService<BudgetHistory>
{
    private readonly Receptionist _receptionist;
    private readonly ICommandInvoker _invoker; 
    public BudgetHistoryService(ConnectionContext connectionContext, Receptionist receptionist, ICommandInvoker invoker): base(connectionContext)
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

        if(cmdId == 13)
        {
            var json = Encoding.UTF8.GetString(data);
            var list = JsonSerializer.Deserialize<List<BudgetHistory>>(json, new JsonSerializerOptions
            {
                PropertyNameCaseInsensitive = true
            });
            list.RemoveAll(x => x.BranchOfficeId != branchOfficeId);
            Models.Clear();
            Models.AddRange(list);
            modelChangeHandler?.Invoke();
        }

        Add(cmdId == 10, data);
        Remove(cmdId == 11, data);
        Update(cmdId == 12, data);
    }
}
