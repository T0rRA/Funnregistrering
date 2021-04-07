using Castle.DynamicProxy.Generators.Emitters.SimpleAST;
using FunnregistreringsAPI.Models;
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

        public async Task<bool> RegistrerFunn(InnFunn nyttFunn, String brukernavn)

        {
            try
            {

                Bruker realUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if(realUser != null) // user found
                {
                    var nf = new Funn
                    {
                        //Koordinat = nyttFunn.Koordinat,
                        Kommune = nyttFunn.Kommune,
                        Image = nyttFunn.Image,
                       /* Gjenstand_markert_med = nyttFunn.Gjenstand_markert_med,
                        Fylke = nyttFunn.Fylke,
                        Funndybde = nyttFunn.Funndybde,
                        Datum = nyttFunn.Datum,
                        Areal_type = nyttFunn.Areal_type,*/
                        Funndato = nyttFunn.Funndato,
                        //BrukerUserID = realUser.UserID
                    };

                    //_db.funn.Add(nf);
                    //await _db.SaveChangesAsync();
                    Debug.Write("hello");
                    return true;
                }
                return false; // user not found
            }
            catch (Exception)
            {
                return false;
            }
        }

        public async Task<Funn> GetFunn(List<Funn> funnListe, int funnID)
        {
            try
            {
                // find specific funn
                foreach(Funn funn in funnListe)
                {
                    if(funn.FunnID == funnID)
                    {
                        // funn is found in users funnliste, now find it in the funn db
                        var funnIDb = await _db.funn.FindAsync(funnID); 
                        if(funnIDb != null) { return funnIDb; }
                        return null; // funn not found in db
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
                    if (!ex_funn_list.Any()) { return null; } //if returned list is empty. Can maybe throw exception so that we can differentiate
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
                   // etFunn.Koordinat = f.Koordinat;
                    etFunn.Kommune = f.Kommune;
                    etFunn.Image = f.Image;
                   /* etFunn.Gjenstand_markert_med = f.Gjenstand_markert_med;
                    etFunn.Fylke = f.Fylke;
                    etFunn.Funndybde = f.Funndybde;
                    etFunn.Datum = f.Datum;
                    etFunn.Areal_type = f.Areal_type;*/
                    etFunn.Funndato = f.Funndato;

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

        public async Task<string> GeneratePdf(string f)
        {
            try
            {
                
                return f + " - nice";
            }
            catch(Exception e)
            {
                return "Oh no";
            }

        }
    }
}
