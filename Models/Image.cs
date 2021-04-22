using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class Image
    {
        public int Id { get; set; }
        public string ImageTitle { get; set; }
        public string ImageData { get; set; }
        public string ImagePath { get; set; }
    }
}
