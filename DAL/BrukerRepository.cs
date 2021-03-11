using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
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
                Bruker funnetBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == bruker.Brukernavn);
                // Checks password in DB
                byte[] hash = CreateHash(bruker.Passord, funnetBruker.Salt);
                //makes a hash out of the salt and the input password

                //compares the hash in the DB and the hashcombination of Salt and Pword-input.
                bool ok = hash.SequenceEqual(funnetBruker.Passord);
                
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

        public async Task<bool> ChangePassword(InnBruker bruker, string new_password)
        {

            // click change password
            // skriv inn gamle passordet ditt // ELLER E-MAILEN
            // --> Bruker skal være logget inn, så man skal skulle vite hvem som er logget inn og hente den infoen fra sessions?
            // sjekk om gammelt passord samsvarer med input
            // eller generer en link som sendes til mailen du har oppgitt? der du kan endre passord
            // skriv nytt passord to ganger
            // sammenlign de to nye passordene
            // finn bruker i db
            // overskriv passord
            try
            {
                // Oppgi e-post addresse
                Bruker enBruker = new Bruker();
                enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == bruker.Brukernavn);
                if(enBruker.Equals(null)) // Hvis brukeren IKKE finnes
                {
                    // feilmelding om at brukeren koblet til den epost adressen ikke finnes
                    return false;
                }
                else // hvis brukeren finnes
                {
                    // send mail om link til å endre passord
                    var epostMelding = new MailMessage()
                    {
                        From = new MailAddress("s333752@oslomet.no"),
                        Subject = "Endre passord",
                        Body = "<h2>Hei, " + enBruker.Fornavn
                    };
                    return true;

                }
            }
            catch (Exception e)
            {
                return false;
            }

        }

        //Might want to change this to return string? Ask frontend boys
        public async Task<bool> CreateUser(InnBruker bruker)
        {
            var ny_bruker = new Bruker();

            //checks db for usernames that are the same
            Bruker potentiallyOldUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == bruker.Brukernavn);
            if (potentiallyOldUser.Equals(null))
            {
                ny_bruker.Brukernavn = bruker.Brukernavn;
            }
            else 
            {
                return false;
            }
            
            
            string passord = bruker.Passord;
            byte[] salt = BrukerRepository.CreateSalt();
            byte[] hash = BrukerRepository.CreateHash(passord, salt);
            ny_bruker.Passord = hash;
            ny_bruker.Salt = salt;
            _db.brukere.Add(ny_bruker);
            _db.SaveChanges();

            return true;
        }
    }
}
