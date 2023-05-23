using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Client.Services;
public interface ICommandInvoker
{
    public void InvokeCommand(short commandId, params object[] args);
}
