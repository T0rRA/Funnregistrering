using Castle.DynamicProxy.Generators.Emitters.SimpleAST;
using FunnregistreringsAPI.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;
using System.Drawing;
using Newtonsoft.Json;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Hosting.Internal;
using Microsoft.AspNetCore.Hosting;
using Paket;
using Funnregistrering.Models;

namespace FunnregistreringsAPI.DAL
{
    public class FunnRepository : FunnRepositoryInterface 
    {
        private readonly FunnDB _db;
        private readonly IWebHostEnvironment _appEnvironment;

        public FunnRepository(FunnDB db, IWebHostEnvironment appEnvironment)
        {
            _db = db;
            _appEnvironment = appEnvironment;
        }

        public async Task<string> RegistrerFunn(InnFunn nyttFunn, String brukernavn)
        {
            try
            {

                if (nyttFunn.innGBNr == null||nyttFunn.innGBNr.grunneier.Postnr == null || nyttFunn.innGBNr.grunneier.Poststed == null || nyttFunn.innGBNr.gb_nr == null)
                {
                    Bruker realUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);

                    //byte[] theImage = Convert.FromBase64String(nyttFunn.image);
                    if (realUser != null) // user found
                    {
                        var nf = new Funn
                        {
                            koordinat = nyttFunn.koordinat,
                            kommune = nyttFunn.kommune,
                            tittel = nyttFunn.tittel,
                            beskrivelse = nyttFunn.beskrivelse,
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
                        return "";
                    }
                    return "User not found. brukernavn: " + brukernavn + " and realUser: " + realUser.Brukernavn; // user not found
                }
                else
                {
                    //FIRST GBNr and grunneier checks!
                    //First check if the GBNr doesn't exist
                    GBNr checkGBNr = await _db.GBNr.FirstOrDefaultAsync(gb => gb.gb_nr == nyttFunn.innGBNr.gb_nr);
                    //worst path -> start creating new postnumber -> new grunneier -> new gbnr -> add gbnr to grunneier -> save changes
                    if (checkGBNr == null)
                    {

                        //If the GBNr doesn't exist, check if the Grunneier exists
                        Grunneier checkGrunneier = await _db.grunneiere.FirstOrDefaultAsync(ge => ge.Adresse == nyttFunn.innGBNr.grunneier.Adresse);
                        if (checkGrunneier == null)
                        {

                            //if it doesn't exist check if postnumber exists
                            Postadresse pa = await _db.postadresser.FirstOrDefaultAsync(post => post.Postnr == nyttFunn.innGBNr.grunneier.Postnr);
                            if (pa == null)
                            {
                                //start creating new postnumber -
                                Postadresse newPa = new Postadresse
                                {
                                    Postnr = nyttFunn.innGBNr.grunneier.Postnr,
                                    Poststed = nyttFunn.innGBNr.grunneier.Poststed
                                };

                                //prep for db
                                await _db.postadresser.AddAsync(newPa);
                                //to overwrite pa so that we can use pa as the common variable in line 69
                                pa = newPa;
                            }

                            //create new grunneier
                            Grunneier ge = new Grunneier
                            {
                                Fornavn = nyttFunn.innGBNr.grunneier.Fornavn,
                                Etternavn = nyttFunn.innGBNr.grunneier.Etternavn,
                                Epost = nyttFunn.innGBNr.grunneier.Epost,
                                Adresse = nyttFunn.innGBNr.grunneier.Adresse,
                                Tlf = nyttFunn.innGBNr.grunneier.Tlf,
                                Postnr = pa  //either the new post adress or an old one
                            };

                            //new gbnr
                            GBNr gb = new GBNr
                            {
                                gb_nr = nyttFunn.innGBNr.gb_nr,
                                grunneier = ge
                            };

                            //create list for grunneier and add the gbnr to it
                            ge.eideGBNR = new List<GBNr>();
                            ge.eideGBNR.Add(gb);

                            //add to db and save.
                            await _db.grunneiere.AddAsync(ge);
                            await _db.GBNr.AddAsync(gb);
                            await _db.SaveChangesAsync();

                            checkGrunneier = ge;
                            checkGBNr = gb;

                        }
                        //next path if GBNr is null but Grunneier is not.
                        else
                        {
                            GBNr gb = new GBNr
                            {
                                gb_nr = nyttFunn.innGBNr.gb_nr,
                                grunneier = checkGrunneier
                            };
                            await _db.GBNr.AddAsync(gb);

                            checkGrunneier.eideGBNR.Add(gb);

                            await _db.SaveChangesAsync();
                            checkGBNr = gb;
                        }
                    }
                    //GBNr og Grunneier håndtering FINISHED
                    Bruker realUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);

                    //byte[] theImage = Convert.FromBase64String(nyttFunn.image);
                    if (realUser != null) // user found
                    {
                        var nf = new Funn
                        {
                            koordinat = nyttFunn.koordinat,
                            kommune = nyttFunn.kommune,
                            tittel = nyttFunn.tittel,
                            beskrivelse = nyttFunn.beskrivelse,
                            image = nyttFunn.image,
                            gjenstand_markert_med = nyttFunn.gjenstand_markert_med,
                            fylke = nyttFunn.fylke,
                            funndybde = nyttFunn.funndybde,
                            datum = nyttFunn.datum,
                            areal_type = nyttFunn.areal_type,
                            funndato = nyttFunn.funndato,
                            BrukerUserID = realUser.UserID,
                            gbnr = checkGBNr
                        };

                        await _db.funn.AddAsync(nf);
                        await _db.SaveChangesAsync();
                        return "";
                    }
                    return "User not found. brukernavn: " + brukernavn + " and realUser: " + realUser.Brukernavn; // user not found
                }

            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                    "Inner Exception: " + e.InnerException +
                    "Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<Funn> GetFunn(String brukernavn, int funnID)
        {
            try
            {
                var bruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (bruker != null)
                {
                    // Get all user funn 
                    List<Funn> funnListe = await _db.funn.Where(funn => funn.BrukerUserID == bruker.UserID).ToListAsync();
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
        }

        //NOTE!!! THIS MUST BE SECURE 
        public async Task<List<Funn>> GetAllUserFunn(String brukernavn, String passord)
        {
            //DECLARE A LIST TO RETURN
            try
            {
                Bruker funnetBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (funnetBruker == null) return null;
                // Check pword
                byte[] hash = BrukerRepository.CreateHash(passord, funnetBruker.Salt);
                bool ok = hash.SequenceEqual(funnetBruker.Passord);

                //If ok use UserID from the found user to get the full list of "funn"
                if (ok)
                {
                    List<Funn> ex_funn_list = await _db.funn.Where(funn => funn.BrukerUserID == funnetBruker.UserID).ToListAsync();
                    // empty list is returned            
                    if (!ex_funn_list.Any()) { return ex_funn_list; } //if returned list is empty. Can maybe throw exception so that we can differentiate
                    else return ex_funn_list;
                } 
                else //if user is not correct
                {
                    return null; //throw exception here as well. 
                }
            }
            catch(Exception)
            {
                return null;
            }
        }


        public async Task<string> DeleteFunn(int funnID)
        {
            try
            {
                Funn real_funn = await _db.funn.FindAsync(funnID);

                if (real_funn != null)

                {
                    // funn is found
                    _db.funn.Remove(real_funn);
                    await _db.SaveChangesAsync();
                    return "";
                }
                return "User it not found"; // funn is not found
            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                    "Inner Exception: " + e.InnerException +
                    "Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<string> EditFunn(Funn f)
        {
            try
            {
                var etFunn = await _db.funn.FindAsync(f.FunnID);
                if (etFunn != null)
                {
                    //checks if gbnr is null
                    if(etFunn.gbnr.Equals(null))
                    {
                        etFunn.gbnr = f.gbnr;
                    }
                    // check if grunneier-postnr has changed
                    if(etFunn.gbnr.grunneier.Postnr.Postnr == null || etFunn.gbnr.grunneier.Postnr.Postnr != f.gbnr.grunneier.Postnr.Postnr )
                    {
                        // postnr has changed, does it exist in our db?
                        var postNr = await _db.postadresser.FindAsync(f.gbnr.grunneier.Postnr.Postnr);
                        if (postNr == null) // if postadresse does not exist
                        {
                            // create a new one
                            Postadresse postadresse = new Postadresse
                            {
                                Postnr = f.gbnr.grunneier.Postnr.Postnr,
                                Poststed = f.gbnr.grunneier.Postnr.Poststed
                            };
                            etFunn.gbnr.grunneier.Postnr = postadresse;
                            await _db.postadresser.AddAsync(postadresse);
                        }


                        /* if (postNr == null)
                        {
                            // it does not exist and will be added
                            var nyPostadr = new Postadresse();
                            nyPostadr.Postnr = f.gbnr.grunneier.Postnr.Postnr;
                            nyPostadr.Poststed = f.gbnr.grunneier.Postnr.Poststed;

                            etFunn.gbnr.grunneier.Postnr = nyPostadr;

                            _db.postadresser.Add(nyPostadr); // add to db
                        }
                        else
                        {
                            //it exists in our db
                            etFunn.gbnr.grunneier.Postnr = postNr;
                        }*/
                    }

                    // funn is found
                    etFunn.koordinat = f.koordinat;
                    etFunn.kommune = f.kommune;
                    etFunn.image = f.image;
                    etFunn.tittel = f.tittel;
                    etFunn.beskrivelse = f.beskrivelse;
                    etFunn.gjenstand_markert_med = f.gjenstand_markert_med;
                    etFunn.fylke = f.fylke;
                    etFunn.funndybde = f.funndybde;
                    etFunn.datum = f.datum;
                    etFunn.areal_type = f.areal_type;
                    etFunn.funndato = f.funndato;
                    etFunn.gbnr.gb_nr = f.gbnr.gb_nr;
                    etFunn.gbnr.grunneier.Fornavn = f.gbnr.grunneier.Fornavn;
                    etFunn.gbnr.grunneier.Etternavn = f.gbnr.grunneier.Etternavn;
                    etFunn.gbnr.grunneier.Adresse = f.gbnr.grunneier.Adresse;
                    etFunn.gbnr.grunneier.Epost = f.gbnr.grunneier.Epost;
                    etFunn.gbnr.grunneier.Postnr.Postnr = f.gbnr.grunneier.Postnr.Postnr;
                    etFunn.gbnr.grunneier.Postnr.Poststed = f.gbnr.grunneier.Postnr.Postnr;
                    etFunn.gbnr.grunneier.Tlf = f.gbnr.grunneier.Tlf;
                    
                    await _db.SaveChangesAsync();
                    return "";
                }
                return "Funn is not found"; // funn is not found
            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                    "Inner Exception: " + e.InnerException +
                    "Stack Trace: " + e.StackTrace);
            
            }
        }

        public async Task<GBNr> GetGBNr(string gbnr)
        {
            throw new NotImplementedException();
            try
            {
                var farm = await _db.GBNr.FirstOrDefaultAsync(g => g.gb_nr == gbnr);
                if (farm != null)
                {
                    return farm;
                }
                return null; // farm not found in list
            }

            catch (Exception e)
            {
                return null;
            }
        }

    }
}
