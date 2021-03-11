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
        [Required]
        [Key]
        public int UserID { get; set; }
        //Might be that username = Email
        [Required]
        public string Brukernavn { get; set; }
        public byte[] Passord { get; set; }
        //Salt is used to hash passwords with unique functions
        public byte[] Salt { get; set; }
        public string Fornavn { get; set; }
        public string Etternavn { get; set; }
        public string Adresse { get; set; }
        public string Postnr { get; set; }
        
        //OIDA DENNE MÅ KANSKJE HA SIN EGEN DB
        public string Poststed { get; set; }
        public string Tlf { get; set; }
        public string Epost { get; set; }

    }
}
