using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class InnFunn : FunnInterface
    {

        public string image { get; set; } 
        public string funndato { get; set; }
        public string kommune { get; set; }
        public string fylke { get; set; }
        public string funndybde { get; set; }
        public string gjenstand_markert_med { get; set; }
        public string koordinat { get; set; }
        public string datum { get; set; }
        public string areal_type { get; set; }
    }
}
