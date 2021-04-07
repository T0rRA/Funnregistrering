using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class Funn
    {
        [Key]
        public int FunnID { get; set; }
        public int BrukerUserID { get; set; }
        public string Image { get; set; }
        public string Funndato { get; set; } //datetime?
        public string Kommune { get; set; }
       /* public string Fylke { get; set; }
        public string Funndybde { get; set; }
        public string Gjenstand_markert_med { get; set; }
        public string Koordinat { get; set; }
        public string Datum { get; set; }
        public string Areal_type { get; set; }*/

        //MORE DATA HERE

        //GRUNNEIER GOES HERE?? Potentially a DB for Grunneiere - or previously registered grunneiere for each G/Bnr

        //The image has to be saved as a Base64 string. This will have to be converted on the frontend. 
    }
}
