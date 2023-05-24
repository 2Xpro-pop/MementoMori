using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Models;
public class Schedule : IHaveBranchOfficeId
{
    public int Id
    {
        get; set;
    }
    public Patient Patient
    {
        get; set;
    }
    public Personal Personal
    {
        get; set;
    }

    public string Caption => $"{Patient.FullName} - {Personal.FullName}";
    public DateTime Date
    {
        get; set;
    }
    public DateTime EndDate => Date.AddHours(1);
    public double Price
    {
        get; set;
    }
    public int BranchOfficeId
    {
        get; set;
    }
}
