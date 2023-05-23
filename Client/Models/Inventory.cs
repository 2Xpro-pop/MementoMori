using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Models;
public class Inventory : IHaveBranchOfficeId
{
    public int Id
    {
        get; set;
    }
    public int BrancheOfficeId
    {
        get; set;
    }
    public string Name
    {
        get; set;
    }
    public int Ammount
    {
        get; set;
    }
}
