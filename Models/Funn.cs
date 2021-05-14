﻿using Funnregistrering.Models;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.Models
{
    public class Funn : FunnInterface
    {
        [Key]
        public int FunnID { get; set; }
        [RegularExpression(@"^[0-9]+$")]
        public int BrukerUserID { get; set; }
        public string image { get; set; }
        public string funndato { get; set; }

        [RegularExpression(@"^[a-zA-ZæøåÆØÅ0-9\-._ ]{2,20}$")]
        public string tittel { get; set; }

        public string beskrivelse { get; set; }

        [RegularExpression(@"^[a-zA-ZæøåÆØÅ\-._ ]{2,20}$")]
        public string kommune { get; set; }
        [RegularExpression(@"^[a-zA-ZæøåÆØÅ\-._ ]{2,20}$")]
        public string fylke { get; set; }
        [RegularExpression(@"^[0-9 ]+(cm|mm|centimeter|m|meter|millimeter|km|kilometer)$")]
        public string funndybde { get; set; }
        [RegularExpression(@"^[a-zA-ZæøåÆØÅ0-9\-._ ]{2,20}$")]
        public string gjenstand_markert_med { get; set; }

        [RegularExpression(@"^[0-9A-Za-z.\- ]{2,40}$")]
        public string koordinat { get; set; }
        public string datum { get; set; }
        [RegularExpression(@"^[a-zA-ZæøåÆØÅ\-._ ]{2,20}$")]
        public string areal_type { get; set; }
        public string gjenstand { get; set; }
        public virtual GBNr gbnr { get; set; }
    }
}
