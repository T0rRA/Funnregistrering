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

        // Kanskje kalle denne for sendPwResetLink ellerno og en annen for change password
        public async Task<bool> SendPwResetLink(String epost)
        {

            // click change password
            // write email address

            /** generate link to change password and send
            * link must be one-time use AND have a time limit (e.g.: must be used within 30 minutes)
            * create a table with username (epost), tokenHash, token, expiration date
            * create random token with rngcryptoserviceprovider, hash it and add to db (also add date)
            * append token value to url to send to user
           */
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

                // Creates a string with the hashed token to be used in the link
                StringBuilder hT = new StringBuilder();
                foreach (byte b in hashedToken) hT.Append(b);

                // Add to db
                PwReset pwReset = new PwReset
                {
                    Username = "epost",
                    //pwReset.Username = enBruker.Epost
                    TokenHash = hT.ToString(),
                    BestFor = expDate,
                    TokenBrukt = false
                };
                //_db.passordReset.Add(pwReset);
                //_db.SaveChanges();


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

                //string mottaker = enBruker.Brukernavn/epost;
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

        public async Task<bool> ChangePassword(InnBruker bruker, String token, string newPassword, string newPassword2)
        {
            /* Trykker på lenken
                 * ON PASSWORD RESET PAGE:
            * take email and token input
            *   get email and token from clicked link?
            *   get email from user info - get token from unique link
            * hash token and compare to hashed token in db
            * check date and if the link has expired
            * take them to page to input new password
            * mark token as used
             */
            try
            {
                
                PwReset enToken = await _db.passordReset.FirstOrDefaultAsync(t => t.TokenHash == token.ToString());
                if(enToken == null)
                {
                    // token does not exist/is used
                    return false;
                } 
                else
                {
                    // token exists

                    // check if token is overdue
                    DateTime rightNow = DateTime.Now;
                    if (DateTime.Compare(enToken.BestFor, rightNow) < 0)
                    {
                        // Token is not overdue
                        // check if token is used
                        if (enToken.TokenBrukt)
                        {
                            //token is used 
                            return false;
                        }
                        else
                        {
                            // token is not used
                            enToken.TokenBrukt = true; // set it to used
                            Bruker enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == enToken.Username); // find user
                            if (enBruker == null)
                            {
                                // user is not found
                                return false;
                            }
                            else
                            {
                                // user is found, compare the password inputs
                                if (newPassword.Equals(newPassword2))
                                {
                                    // the passwords are the same, so hash and overwrite
                                    byte[] salt = CreateSalt();
                                    byte[] hash = CreateHash(newPassword, salt);
                                    enBruker.Passord = hash;
                                    enBruker.Salt = salt;
                                    await _db.SaveChangesAsync();
                                    return true;
                                }
                                else
                                {
                                    // the passwords are not the same, try again.
                                    return false;
                                }

                            }
                        }
                    }
                    else
                    {
                        // token is overdue
                        enToken.TokenBrukt = true;
                        await _db.SaveChangesAsync();
                        return false;
                    }
                }
            }
            catch(Exception e)
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
