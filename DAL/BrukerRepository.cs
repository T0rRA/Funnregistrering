using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Linq;
using System.Net;
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

        public async Task<bool> ChangePassword(InnBruker bruker, string nytt_passord)
        {

            // click change password
            // write email address
            // generate link to change password and send
            // skriv nytt passord to ganger
            // sammenlign de to nye passordene
            // finn bruker i db
            // overskriv passord
            try
            {
                // Input e-mail addres
                Bruker enBruker = new Bruker();
                enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == bruker.Brukernavn);
                if(enBruker.Equals(null)) // If user does NOT exist
                {
                    // Error message saying this user does not exist
                    return false;
                }
                else // If user exists
                {
                // send mail with a link to change password

                // Connect to the SMTP-setup in appsettings.json
                var builder = new ConfigurationBuilder().AddJsonFile("appsettings.json");
                var config = builder.Build();

                // Set up SMTP client to communicate with SMTP servers
                var smtpClient = new SmtpClient(config["Smtp:Host"])
                {
                    Port = int.Parse(config["Smtp:Port"]),
                    Credentials = new NetworkCredential(config["Smtp:Username"], config["Smtp:Password"]),
                    EnableSsl = true,
                };

                
                // Construct e-mail-string
                var epostMelding = new MailMessage()
                {
                    From = new MailAddress("losfunnregistrering@gmail.com"),
                    Subject = "Endre passord",
                    Body = "<h2>Hei Fatima, </h2>"
                    + "<br/><br/><p>"
                    + "For å endre passordet ditt kan du trykke <a href='#'>her.</a><br/>"
                    + "Hvis du ikke har bedt om å endre passord, kan du ignorere denne eposten.<br/><br/>"
                    + "Ha en fin dag videre!<br/><br/>"
                    + "Med vennelig hilsen,<br/>"
                    + "Finnerlønn-teamet.</p>",
                    IsBodyHtml = true,
                };

                string mottaker = enBruker.Brukernavn;
                epostMelding.To.Add(mottaker); // hardcoded for now 
                await smtpClient.SendMailAsync(epostMelding);
                 
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
