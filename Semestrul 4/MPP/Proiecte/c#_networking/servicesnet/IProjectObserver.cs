using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using modelnet;

namespace servicesnet
{
    public interface IProjectObserver
    {
        void notifyObservers(Donatie donatie);
    }
}