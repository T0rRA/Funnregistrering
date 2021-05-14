using FunnregistreringsAPI.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace Funnregistrering.Models
{
    public class Grunneier
    {
        [Key]
        public int GrunneierID { get; set; }
        public string Fornavn { get; set; }
        [RegularExpression(@"^[a-zA-ZæøåÆØÅ+\- ]{2,20}$")]
        public string Etternavn { get; set; }
        [RegularExpression(@"^[a-zA-ZæøåÆØÅ0-9\-. ]{2,40}$")]
        public string Adresse { get; set; }
        [RegularExpression(@"^[0-9]{4}$")]
        public virtual Postadresse Postnr { get; set; }
        [RegularExpression(@"^[0-9+]{8,12}$")]
        public string Tlf { get; set; }
        [RegularExpression(@"^[a-zA-Z0-9\-._ ]+@[a-zA-Z.\-]+\.[a-zA-Z]{2,20}$")]
        public string Epost { get; set; }
        public virtual List<GBNr> eideGBNR { get; set; }
    }
}
