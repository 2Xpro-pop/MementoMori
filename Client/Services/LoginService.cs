using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Services;
public class LoginService
{
    public Action<string, string> Login
    {
        get; set;
    } = (login, password) => { };
}
