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
        public int UserID { get; set; }
        //Might be that username = Email
        public string Brukernavn { get; set; }
        public byte[] Passord { get; set; }
        //Salt is used to hash passwords with unique functions
        public byte[] Salt { get; set; }
        public string Fornavn { get; set; }
        public string Etternavn { get; set; }
        public string Adresse { get; set; }
        public virtual Postadresse Postnr { get; set; }// egen klasse
        public string Tlf { get; set; }
        public string Epost { get; set; }
        public virtual List<Funn> MineFunn { get; set; }
        public bool LoggetInn { get; set; } // log-in check 

    }
}
