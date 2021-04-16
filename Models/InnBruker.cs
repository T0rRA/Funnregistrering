using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class InnBruker
    {
        //[RegularExpression(@"^[a-zA-Z0-9\-._ ]+@[a-zA-Z.\-]+\.[a-zA-Z]{2,20}$")]
        public String Brukernavn { get; set; }
        // [RegularExpression(@"^[A-ZÅÆØÅ])(?=.*\d)[A-ZÆØÅa-zæøå\d]{6,}$")]
        public String Passord { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ\- ]{2,20}$")]
        public string Fornavn { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ+\- ]{2,20}$")]
        public string Etternavn { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ0-9\-. ]{2,40}$")]
        public string Adresse { get; set; }
        //[RegularExpression(@"^[0-9]{4}$")]
        public string Postnr { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ\- ]{2,20}$")]
        public string Poststed { get; set; }
        //[RegularExpression(@"^[0-9+]{8,12}$")]
        public string Tlf { get; set; }
        //[RegularExpression(@"^[a-zA-Z0-9\-._ ]+@[a-zA-Z.\-]+\.[a-zA-Z]{2,20}$")]
        public string Epost { get; set; }
    }
}
