using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    //HER ER DET MYE BTW
    public class Postadresse
    { 
        [Key]
        public string Postnr { get; set; }
        //Might be that username = Email
        public string Poststed { get; set; }
       
    }
}
