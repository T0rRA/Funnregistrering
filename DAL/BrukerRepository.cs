using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Reflection.Metadata;
using System.Security.Cryptography;
using System.Security.Policy;
using System.Text;
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

        public async Task<bool> ChangePassword()
        {

            // click change password
            // write email address

            /** generate link to change password and send
            * link must be one-time use AND have a time limit (e.g.: must be used within 30 minutes)
            * create a table with username (epost), tokenHash, token, expiration date
            * create random token with rngcryptoserviceprovider, hash it and add to db (also add date)
            * append token value to url to send to user
            * ON PASSWORD RESET PAGE:
            * take email and token input
            * hash token and compare to hashed token in db
            * check date and if the link has expired
            * take them to page to input new password
            * mark token as used
             */

            // skriv nytt passord to ganger
            // sammenlign de to nye passordene
            // finn bruker i db
            // overskriv passord
            try
            {
                /*
                // Input e-mail addres
                Bruker enBruker = new Bruker();
                enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == epost);
                if(enBruker.Equals(null)) // If user does NOT exist
                {
                    // Error message saying this user does not exist
                    return false;
                }
                else // If user exists, send mail with a link to change password
                { */

                // Generate random token
                var rngCsp = new RNGCryptoServiceProvider();
                var token = new byte[8];
                rngCsp.GetBytes(token);
                byte[] hashedToken = CreateHash("", token); // 32 byte hash token

                // DateTime - where expDate is an hour after now
                DateTime date = DateTime.Now;
                DateTime expDate = new DateTime(date.Year, date.Month, date.Day, (date.Hour + 1), date.Minute, date.Second);

                // Add to db
                PwReset pwReset = new PwReset();
                pwReset.Username = "epost";
                pwReset.TokenHash = hashedToken;
                pwReset.BestFor = expDate;
                pwReset.TokenBrukt = false;
                //_db.passordReset.Add(pwReset);
                //_db.SaveChanges();

                // Creates a string with the hashed token to be used in the link
                StringBuilder hT = new StringBuilder();
                foreach(byte b in hashedToken) hT.Append(b);

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
                    // Body = "<h2>Hei "+ enBruker.Fornavn +", </h2>"
                    Body = "<h2>Hei brukernavn, </h2>"
                    + "<br/><br/><p>"
                    + "For å endre passordet ditt kan du trykke <a href='/passordReset?brukernavn&"+ hT 
                    +"'>her.</a> <br/>"
                    + "Hvis du ikke har bedt om å endre passord, kan du ignorere denne e-posten.<br/><br/>"
                    + "Ha en fin dag videre!<br/><br/>"
                    + "Med vennlig hilsen,<br/>"
                    + "Finnerlønn-teamet.</p>",
                    IsBodyHtml = true,
                };

                //string mottaker = enBruker.Brukernavn;
                string mottaker = "s333752@oslomet.no";
                epostMelding.To.Add(mottaker); 
                await smtpClient.SendMailAsync(epostMelding); // sends mail
                 
                return true;

               // }
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
