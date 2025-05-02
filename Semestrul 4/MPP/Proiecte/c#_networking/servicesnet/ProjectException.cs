using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace servicesnet
{
    public class ProjectException : Exception
    {
        public ProjectException() : base() { }

        public ProjectException(string message) : base(message) { }

        public ProjectException(string message, Exception innerException) : base(message, innerException) { }
    }
}