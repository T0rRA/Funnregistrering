using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class InnGBNr
    {
        public string gb_nr { get; set; }
        public InnGrunneier grunneier { get; set; }
    }
}
