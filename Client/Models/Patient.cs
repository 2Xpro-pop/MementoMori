using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Models;
public class Patient : IHaveBranchOfficeId
{
    public int Id
    {
        get; set;
    }
    public string Name
    {
        get; set;
    }
    public string Surname
    {
        get; set;
    }
    public string FullName => $"{Name} {Surname}";
    public string Address
    {
        get; set;
    }
    public string Email
    {
        get; set;
    }
    public string PhoneNumber
    {
        get; set;
    }
    public int BranchOfficeId
    {
        get; set;
    }
}
