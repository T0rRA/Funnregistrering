using FunnregistreringsAPI.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    /*
    public class Brukers
    {
        public int UserID { get; set; }
        //Might be that username = Email
        public string Brukernavn { get; set; }
        public byte[] Passord { get; set; }
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

    public class Funns
    {

        public int FunnID { get; set; }
        public string image { get; set; }
        public string funndato { get; set; }
        public string kommune { get; set; }
        public string fylke { get; set; }
        public string funndybde { get; set; }
        public string gjenstand_markert_med { get; set; }
        public string koordinat { get; set; }
        public string datum { get; set; }
        public string areal_type { get; set; }
    }

    public class PwResets
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string TokenHash { get; set; }
        public DateTime BestFor { get; set; }
        public bool TokenBrukt { get; set; }
    }
    */

    public class FunnDB : DbContext
    {
        public FunnDB(DbContextOptions<FunnDB> options) : base(options)
        {
            Database.EnsureCreated();
        }

        public DbSet<Bruker> brukere { get; set; }
        public DbSet<Funn> funn { get; set; }
        public DbSet<PwReset> passordReset { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseLazyLoadingProxies();
        }
    }
}
