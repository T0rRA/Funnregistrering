using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class InnBruker
    {
        [RegularExpression(@"^[a-zA-ZæøåÆØÅ. \-]{2,20}$")]
        public String Brukernavn { get; set; }
        [RegularExpression(@"^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$")]
        public String Passord { get; set; }
        public string navn { get; set; }
        public string adresse { get; set; }
        public string postnr { get; set; }
        public string poststed { get; set; }
        public string tlf { get; set; }
        public string epost { get; set; }
    }
}
