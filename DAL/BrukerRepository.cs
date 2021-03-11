using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public class BrukerRepository : BrukerRepositoryInterface
    {
        private readonly FunnDB _db;

        public BrukerRepository(FunnDB db)
        {
            _db = db;
        }
        public static byte[] CreateHash(string passord, byte[] salt)
        {
            return KeyDerivation.Pbkdf2(
                                password: passord,
                                salt: salt,
                                prf: KeyDerivationPrf.HMACSHA512,
                                iterationCount: 1000,
                                numBytesRequested: 32);
        }

        //Creates a byteset we can combine with the registered password
        public static byte[] CreateSalt()
        {
            var csp = new RNGCryptoServiceProvider();
            var salt = new byte[24];
            csp.GetBytes(salt);
            return salt;
        }

        public async Task<bool> AttemptLogin(InnBruker bruker)
        {
            try
            {
                Bruker funnetBruker = await _db.brukere.FirstOrDefaultAsync(b => b.brukernavn == bruker.Brukernavn);
                // Checks password in DB
                byte[] hash = CreateHash(bruker.Passord, funnetBruker.Salt);
                //makes a hash out of the salt and the input password

                //compares the hash in the DB and the hashcombination of Salt and Pword-input.
                bool ok = hash.SequenceEqual(funnetBruker.passord);
                
                if (ok)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            //General exception - can specify.
            catch (Exception e)
            {

                return false;
            }

        }

        public Task<bool> ChangePassword(InnBruker bruker, string new_password)
        {
            throw new NotImplementedException();
        }

        //Might want to change this to return string? Ask frontend boys
        public async Task<bool> CreateUser(InnBruker bruker)
        {
            var ny_bruker = new Bruker();

            //checks db for usernames that are the same
            Bruker potentiallyOldUser = await _db.brukere.FirstOrDefaultAsync(b => b.brukernavn == bruker.Brukernavn);
            if (potentiallyOldUser.Equals(null))
            {
                ny_bruker.brukernavn = bruker.Brukernavn;
            }
            else 
            {
                return false;
            }
            
            
            string passord = bruker.Passord;
            byte[] salt = BrukerRepository.CreateSalt();
            byte[] hash = BrukerRepository.CreateHash(passord, salt);
            ny_bruker.passord = hash;
            ny_bruker.Salt = salt;
            _db.brukere.Add(ny_bruker);
            _db.SaveChanges();

            return true;
        }
    }
}
