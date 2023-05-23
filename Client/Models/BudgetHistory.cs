using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Models;
public class BudgetHistory: IHaveBranchOfficeId
{
    public int Id
    {
        get; set;
    }
    public string Description
    {
        get; set;
    }
    public double Action
    {
        get; set;
    }
    public int BrancheOfficeId
    {
        get; set;
    }
}
