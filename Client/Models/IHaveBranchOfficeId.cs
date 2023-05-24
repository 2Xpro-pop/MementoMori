using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Models;
public interface IHaveBranchOfficeId
{
    public int Id
    {
        get; set;
    }
    public int BranchOfficeId
    {
        get; set;
    }
}
