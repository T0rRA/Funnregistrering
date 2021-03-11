using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class InnFunn : FunnInterface
    {

        //MORE DATA HERE

        //JSON CAN'T SEND IMAGES, NEEDS TO BE CONVERTED TO A STRING WITH BASE64 AND THEN REVERT
        //public string image 
        public string Funndato { get; set; }
        public string Kommune { get; set; }
        public string Fylke { get; set; }
        public string Funndybde { get; set; }
        public string Gjenstand_markert_med { get; set; }
        public string Koordinat { get; set; }
        public string Datum { get; set; }
        public string Areal_type { get; set; }
    }
}
