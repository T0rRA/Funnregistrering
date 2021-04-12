using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    interface FunnInterface
    {

        //GRUNNEIER GOES HERE?? Potentially a DB for Grunneiere - or previously registered grunneiere for each G/Bnr
        public byte[] image { get; set; }
        public DateTime funndato { get; set; }
        public string kommune { get; set; }
        public string fylke { get; set; }
        public string funndybde { get; set; }
        public string gjenstand_markert_med { get; set; }
        public string koordinat { get; set; }
        public DateTime datum { get; set; }
        public string areal_type { get; set; }
    }
}
