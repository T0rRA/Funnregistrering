using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class Funn : FunnInterface
    {
        [Key]
        public int FunnID { get; set; }
 
        public int BrukerUserID { get; set; }
        public string image { get; set; }
        public string funndato { get; set; } //datetime?
        public string kommune { get; set; }
        public string fylke { get; set; }
        public string funndybde { get; set; }
        public string gjenstand_markert_med { get; set; }
        public string koordinat { get; set; }
        public string datum { get; set; }
        public string areal_type { get; set; }
    }
}
