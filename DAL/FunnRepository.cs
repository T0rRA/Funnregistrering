using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;
<<<<<<< Updated upstream
=======
using System.Drawing;
using Newtonsoft.Json;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Hosting.Internal;
using Microsoft.AspNetCore.Hosting;
using Paket;
>>>>>>> Stashed changes

namespace FunnregistreringsAPI.DAL
{
    public class FunnRepository : FunnRepositoryInterface
    {
<<<<<<< Updated upstream
        public Task<bool> RegistrerFunn(Funn nyttFunn)
        {
            throw new NotImplementedException();
=======
        private readonly FunnDB _db;
        private readonly IWebHostEnvironment _appEnvironment;

        public FunnRepository(FunnDB db, IWebHostEnvironment appEnvironment)
        {
            _db = db;
            _appEnvironment = appEnvironment;
        }

        public async Task<bool> RegistrerFunn(InnFunn nyttFunn, String brukernavn)

        {
            try
            {

                Bruker realUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (realUser != null) // user found
                {
                    var nf = new Funn
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

                    _db.funn.Add(nf);
                    await _db.SaveChangesAsync();
                    return true;
                }
                return false; // user not found
            }
            catch (Exception)
            {
                return false;
            }
        }

        [HttpPost]
        public async Task<Funn> GetFunn(String brukernavn, int funnID)
        {
            try
            {
                var bruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (bruker != null)
                {
                    var funnListe = bruker.MineFunn;
                    // find specific funn
                    foreach (Funn funn in funnListe)
                    {
                        if (funn.FunnID == funnID)
                        {
                            // funn is found in users funnliste, now find it in the funn db
                            var funnIDb = await _db.funn.FindAsync(funnID);
                            if (funnIDb != null) { return funnIDb; }
                            return null; // funn not found in db
                        }
                    }
                }
                return null; // funn not found in list
            }
            catch (Exception e)
            {
                return null;
            }
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======

        public async Task<bool> DeleteFunn(int funnID)
        {
            try
            {
                Funn real_funn = await _db.funn.FindAsync(funnID);
                if (real_funn != null)
                {
                    // funn is found
                    _db.funn.Remove(real_funn);
                    await _db.SaveChangesAsync();
                    return true;
                }
                return false; // funn is not found
            }
            catch (Exception e)
            {
                return false;
            }

        }

        public async Task<bool> EditFunn(Funn f)
        {
            try
            {
                var etFunn = await _db.funn.FindAsync(f.FunnID);
                if (etFunn != null)
                {
                    // funn is found
                    etFunn.koordinat = f.koordinat;
                    etFunn.kommune = f.kommune;
                    etFunn.image = f.image;
                    etFunn.gjenstand_markert_med = f.gjenstand_markert_med;
                    etFunn.fylke = f.fylke;
                    etFunn.funndybde = f.funndybde;
                    etFunn.datum = f.datum;
                    etFunn.areal_type = f.areal_type;
                    etFunn.funndato = f.funndato;

                    await _db.SaveChangesAsync();
                    return true;
                }
                return false; // funn is not found
            }
            catch (Exception e)
            {
                return false;
            }

        }

        // TAR  JSON STRING, DESERIALIZE AND HOPEFULLY SAVE
        // TEST FIRST
        // ingen lagre-kode implementert ennå fordi jeg skulle teste om den tok input at all
        // trenger en slags json form for å teste med å sende json objekter
        [HttpPost]
        public bool dJ(String jsonStr)
        {
            try
            {
                var jImg = JsonConvert.DeserializeObject<dynamic>(jsonStr); // image from funn
                Debug.WriteLine(jsonStr.ToString());
                return true;
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.Message.ToString());
                return false;
            }
        }
>>>>>>> Stashed changes
    }
}
