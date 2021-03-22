using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public class FunnRepository : FunnRepositoryInterface
    {
        private readonly FunnDB _db;

        public async Task<bool> RegistrerFunn(InnFunn nyttFunn, InnBruker ib)
        {
            try
            {
                Bruker realUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == ib.Brukernavn);

                Funn nf = new Funn
                {
                    koordinat = nyttFunn.koordinat,
                    kommune = nyttFunn.kommune,
                    image = nyttFunn.image,
                    gjenstand_markert_med = nyttFunn.gjenstand_markert_med,
                    fylke = nyttFunn.fylke,
                    funndybde = nyttFunn.funndybde,
                    datum = nyttFunn.datum,
                    areal_type = nyttFunn.areal_type,
                    funndato = nyttFunn.funndato,
                    BrukerUserID = realUser.UserID
                };

                await _db.funn.AddAsync(nf);
                await _db.SaveChangesAsync();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        //NOTE!!! THIS MUST BE SECURE 
        public List<Funn> GetAllUserFunn()
        {
            //DECLARE A LIST TO RETURN
            try
            {
                List<Funn> ex_funn_list = new List<Funn>();
                //Note to self or other funn repo lads - let's make a generator this was horrible to write.
                ex_funn_list.Add(new Funn
                {
                    FunnID = 1,
                    image = "544399123123sdadaffdgwqe",
                    areal_type = "jorde",
                    datum = "12.03.2021",
                    funndato = "11.02.2021",
                    funndybde = "420m",
                    fylke = "Viken",
                    gjenstand_markert_med = "funnID'en",
                    kommune = "Sarpsborg",
                    koordinat = "12 03 12N, 54 12 65W",
                    BrukerUserID = 1
                });
                ex_funn_list.Add(new Funn
                {
                    FunnID = 2,
                    image = "owoew",
                    areal_type = "fjell",
                    datum = "12.03.2021",
                    funndato = "11.02.2021",
                    funndybde = "69m",
                    fylke = "Viken",
                    gjenstand_markert_med = "funnID'en",
                    kommune = "Sarpsborg",
                    koordinat = "12 03 17N, 54 12 62W",
                    BrukerUserID = 1
                });

                return ex_funn_list;
            } catch(Exception)
            {
                return null;
            }
        }
    }
}
