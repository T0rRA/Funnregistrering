using FunnregistreringsAPI.Controllers;
using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public class DBInit
    {
        public static Boolean Initialize(IApplicationBuilder app)
        {
            var serviceScope = app.ApplicationServices.CreateScope();
            var context = serviceScope.ServiceProvider.GetService<FunnDB>();
            var result = context.brukere.Where(b => b.UserID.Equals(1));

            if (!result.Any())
            {
                context.Database.EnsureDeleted();
                context.Database.EnsureCreated();


                // BRUKERE
                var bruker1 = new Bruker();
                bruker1.Brukernavn = "s333752@oslomet.no";
                byte[] salt = BrukerRepository.CreateSalt();
                byte[] pw = BrukerRepository.CreateHash("Fatima123", salt);
                bruker1.Salt = salt;
                bruker1.Passord = pw;
                bruker1.Fornavn = "Fatima";
                bruker1.Etternavn = "Ahmad";
                bruker1.Adresse = "Osloveien 2b";

                Postadresse poststed = new Postadresse();
                poststed.Postnr = "0457";
                poststed.Poststed = "Oslo";
                context.postadresser.Add(poststed);

                bruker1.Postnr = poststed; // blir postnr1 i db idk why:(
                

                bruker1.Tlf = "75849384";
                bruker1.Epost = "s333752@oslomet.no";
                bruker1.MineFunn = new List<Funn>();

                
                // FUNN
                var funn1 = new Funn();
                funn1.image = "123";
                funn1.funndato = "Kanskje bør dette være en DateTime";
                funn1.kommune = "Oslo";
                funn1.fylke = "Akershus";
                funn1.funndybde = "idk 5cm?";
                funn1.gjenstand_markert_med = "oh4o5kf";
                funn1.koordinat = "5943058390494";
                funn1.datum = "???";
                funn1.areal_type = "...sirkel";
                
                var funn2 = new Funn
                {
                    image = "544399123123sdadaffdgwqe",
                    areal_type = "jorde",
                    datum = "12.03.2021",
                    funndato = "11.02.2021",
                    funndybde = "420m",
                    fylke = "Viken",
                    gjenstand_markert_med = "funnID'en",
                    kommune = "Sarpsborg",
                    koordinat = "12 03 12N, 54 12 65W",
                };
                var funn3 = new Funn
                {
                    image = "owoew",
                    areal_type = "fjell",
                    datum = "12.03.2021",
                    funndato = "11.02.2021",
                    funndybde = "69m",
                    fylke = "Viken",
                    gjenstand_markert_med = "funnID'en",
                    kommune = "Sarpsborg",
                    koordinat = "12 03 17N, 54 12 62W",
                };
                
                bruker1.MineFunn.Add(funn1);
                bruker1.MineFunn.Add(funn2);
                bruker1.MineFunn.Add(funn3);

                context.brukere.Add(bruker1);
                context.SaveChanges();
                Console.WriteLine("Funn added");
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
