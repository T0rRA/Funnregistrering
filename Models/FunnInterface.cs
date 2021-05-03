using Funnregistrering.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    interface FunnInterface
    {
        //GRUNNEIER GOES HERE?? Potentially a DB for Grunneiere - or previously registered grunneiere for each G/Bnr
        //public string image { get; set; }
        public string funndato { get; set; }
        public string kommune { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ-._ ]{2,20}$")]
        public string fylke { get; set; }
        //[RegularExpression(@"^[0-9 ]+(cm|mm|centimeter|m|meter|millimeter|km|kilometer)$")]
        public string funndybde { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ0-9-._ ]{2,20}$")]
        public string gjenstand_markert_med { get; set; }
        //[RegularExpression(@"^[0-9.- ]{2,40}$")]
        public string koordinat { get; set; }
        public string datum { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ-._ ]{2,20}$")]
        public string areal_type { get; set; }
        public string tittel { get; set; }
        public string beskrivelse { get; set; }
    }
}
