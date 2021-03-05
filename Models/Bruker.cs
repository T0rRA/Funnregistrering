using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class Bruker
    {
        [Required]
        public int UserID { get; set; }

        [Required]
        public string Brukernavn { get; set; }
        
        public byte[] Passord { get; set; }
        //Salt is used to hash passwords with unique functions
        public byte[] Salt { get; set; }
    }
}
