using Castle.DynamicProxy.Generators.Emitters.SimpleAST;
using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public class FunnRepository : FunnRepositoryInterface 
    {
        private readonly FunnDB _db;

        public FunnRepository(FunnDB db)
        {
            _db = db;
        }

        [HttpPost]
        public async Task<bool> RegistrerFunn(Funn nyttFunn, String brukernavn)

        {
            try
            {

                Bruker realUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if(realUser!=null) // user found
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
            catch(Exception e)
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

        public async Task<bool> DeleteFunn(int funnID)
        {
            try
            {
                Funn real_funn = await _db.funn.FindAsync(funnID);
                if(real_funn != null)
                {
                    // funn is found
                    _db.funn.Remove(real_funn);
                    await _db.SaveChangesAsync();
                    return true;
                }
                return false; // funn is not found
            }
            catch(Exception e)
            {
                return false;
            }

        }

        public async Task<bool> EditFunn(Funn f)
        {
            try
            {
                var etFunn = await _db.funn.FindAsync(f.FunnID);
                if(etFunn != null)
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
            catch(Exception e)
            {
                return false;
            }

        }
    }
}
