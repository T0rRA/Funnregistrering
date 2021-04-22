using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    interface FunnInterface
    {
<<<<<<< Updated upstream

        //GRUNNEIER GOES HERE?? Potentially a DB for Grunneiere - or previously registered grunneiere for each G/Bnr
        public string image { get; set; }
        public string funndato { get; set; }
=======
        public string image { get; set; }
        public string funndato { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ-._ ]{2,20}$")]
>>>>>>> Stashed changes
        public string kommune { get; set; }
        public string fylke { get; set; }
        public string funndybde { get; set; }
        public string gjenstand_markert_med { get; set; }
        public string koordinat { get; set; }
        public string datum { get; set; }
<<<<<<< Updated upstream
=======
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ-._ ]{2,20}$")]
>>>>>>> Stashed changes
        public string areal_type { get; set; }
    }
}
