using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Funnregistrering.Models
{
    public class GBNr
    {
        [Key]
        public int GBNrID { get; set; }
        public string gb_nr { get; set; }
        public string funnsted { get; set; }
        public string gaard { get; set; }
        public virtual Grunneier grunneier { get; set; }
    }
}