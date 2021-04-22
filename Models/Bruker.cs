using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    //HER ER DET MYE BTW
    public class Bruker
    { 
        [Key]

        //[RegularExpression(@"^[0-9]+$")]
        public int UserID { get; set; }
        //Might be that username = Email
        //[RegularExpression(@"^[a-zA-Z0-9\-._ ]+@[a-zA-Z.\-]+\.[a-zA-Z]{2,20}$")]
        public String Brukernavn { get; set; }
        public byte[] Passord { get; set; }
        public byte[] Salt { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ\- ]{2,20}$")]
        public string Fornavn { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ+\- ]{2,20}$")]
        public string Etternavn { get; set; }
        //[RegularExpression(@"^[a-zA-ZæøåÆØÅ0-9\-. ]{2,40}$")]
        public string Adresse { get; set; }
        //[RegularExpression(@"^[0-9]{4}$")]
        public virtual Postadresse Postnr { get; set; }

        //[RegularExpression(@"^[0-9+]{8,12}$")]
        public string Tlf { get; set; }
        //[RegularExpression(@"^[a-zA-Z0-9\-._ ]+@[a-zA-Z.\-]+\.[a-zA-Z]{2,20}$")]
        public string Epost { get; set; }
        public virtual List<Funn> MineFunn { get; set; }
        public bool LoggetInn { get; set; } // log-in check 

    }
}
