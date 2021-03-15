using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    
    public class PwReset
    {
        [Key]
        public int Id { get; set; }
        public string Username { get; set; }
        public string TokenHash { get; set; }
        public DateTime BestFor { get; set; }
        public bool TokenBrukt { get; set; }

    }
}
