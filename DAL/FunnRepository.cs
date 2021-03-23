using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;

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
        public async Task<List<Funn>> GetAllUserFunn(InnBruker ib)
        {
            //DECLARE A LIST TO RETURN
            try
            {
                Bruker funnetBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == ib.Brukernavn);
                // Check pword
                byte[] hash = CreateHash(ib.Passord, funnetBruker.Salt);
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

                
            } catch(Exception)
            {
                return null;
            }
        }

        public async Task<bool> DeleteFunn(Funn f)
        {
            Funn real_funn = await _db.funn.FindAsync(f.FunnID);
            _db.funn.Remove(real_funn);
            await _db.SaveChangesAsync();
            return true;

        }
    }
}
