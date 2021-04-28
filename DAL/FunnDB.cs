using Funnregistrering.Models;
using FunnregistreringsAPI.Models;
using Microsoft.EntityFrameworkCore;
using System;

namespace FunnregistreringsAPI.DAL
{
    public class FunnDB : DbContext
    {
        public FunnDB(DbContextOptions<FunnDB> options) : base(options)
        {
            Database.EnsureCreated();
        }

        public DbSet<Bruker> brukere { get; set; }
        public DbSet<Funn> funn { get; set; }

        public DbSet<PwReset> passordReset { get; set; }
        public DbSet<Postadresse> postadresser { get; set; }
        public DbSet<Image> Images { get; set; }
        public DbSet<Grunneier> grunneiere { get; set; }
        
        public DbSet<GBNr> GBNr { get; set; }


        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseLazyLoadingProxies();
        }
    }
}
