using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    interface FunnInterface
    {

        //GRUNNEIER GOES HERE?? Potentially a DB for Grunneiere - or previously registered grunneiere for each G/Bnr
        public string Image { get; set; }
        public string Funndato { get; set; }
        public string Kommune { get; set; }
       /* public string Fylke { get; set; }
        public string Funndybde { get; set; }
        public string Gjenstand_markert_med { get; set; }
        public string Koordinat { get; set; }
        public string Datum { get; set; }
        public string Areal_type { get; set; }
        */
    }
}
