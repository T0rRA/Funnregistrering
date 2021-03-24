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

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseLazyLoadingProxies();
        }
    }
}
