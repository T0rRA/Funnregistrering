using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class Postadresse
    { 
        [Key]
        public int PostadresseID { get; set; }
        public string Postnr { get; set; }
        public string Poststed { get; set; }
       
    }
}
