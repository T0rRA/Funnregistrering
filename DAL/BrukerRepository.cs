using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Net.Mail;
using System.Reflection.Metadata;
using System.Security.Cryptography;
using System.Security.Policy;
using System.Text;
using System.Threading.Tasks;
using FunnregistreringsAPI.DAL;

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

        public static SmtpClient SmtpClient()
        {
            try
            {
                // Connect to the SMTP-setup in appsettings.json
                var builder = new ConfigurationBuilder().AddJsonFile("appsettings.json");
                var config = builder.Build();

                // Set up SMTP client to communicate with SMTP servers
                var smtpClient = new SmtpClient(config["Smtp:Host"])
                {
                    Port = int.Parse(config["Smtp:Port"]),
                    UseDefaultCredentials = false,
                    Credentials = new NetworkCredential(config["Smtp:Username"], config["Smtp:Password"]),
                    DeliveryMethod = SmtpDeliveryMethod.Network,
                    EnableSsl = true,
                };

                return smtpClient;
            }
            catch(Exception e)
            {
                return null;
            }
        }

        public async Task<string> CreateUser(InnBruker bruker)
        {
            try
            {
                var ny_bruker = new Bruker();

                //checks db for usernames that are the same
                Bruker potentiallyOldUser = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == bruker.Brukernavn);
                if (potentiallyOldUser == null)
                {
                    // create new user
                    ny_bruker.Brukernavn = bruker.Brukernavn;
                    string passord = bruker.Passord;
                    byte[] salt = BrukerRepository.CreateSalt();
                    byte[] hash = BrukerRepository.CreateHash(passord, salt);
                    ny_bruker.Passord = hash;
                    ny_bruker.Salt = salt;

                    ny_bruker.Fornavn = bruker.Fornavn;
                    ny_bruker.Etternavn = bruker.Etternavn;
                    ny_bruker.Adresse = bruker.Adresse;


                    // find postal address
                    var finnPostadr = await _db.postadresser.FindAsync(bruker.Postnr);
                    if (finnPostadr == null)
                    {
                        // Post code is not in the database
                        var ny_postadresse = new Postadresse();
                        ny_postadresse.Postnr = bruker.Postnr;
                        ny_postadresse.Poststed = bruker.Poststed;
                        _db.postadresser.Add(ny_postadresse); // add postal code to db

                        ny_bruker.Postnr = ny_postadresse;
                    }
                    else
                    {
                        // Post code is found
                        ny_bruker.Postnr = finnPostadr;

                    }
                    ny_bruker.Tlf = bruker.Tlf;
                    ny_bruker.Epost = bruker.Epost;


                    _db.brukere.Add(ny_bruker);
                    await _db.SaveChangesAsync();


                    // Sends welcome-email to new user
                    /*
                    var smtpClient = SmtpClient();

                    var emailMessage = new MailMessage()
                    {
                        From = new MailAddress("losfunnregistrering@gmail.com"),
                        Subject = "Si hallo til effektivisert metallsøking!",
                        Body = "<h2>Hei " + ny_bruker.Fornavn + ", </h2>"
                        + "<br/><br/><p>"
                        + "Velkommen til Finnerlønn! Vi er kjempeglade for å ha deg med på teamet, "
                        + "og ønsker deg lykke til med metallsøkingen."
                        + "Ha en fin dag videre!<br/><br/>"
                        + "Med vennlig hilsen,<br/>"
                        + "Finnerlønn-teamet.</p>",
                        IsBodyHtml = true,
                    };

                    string mottaker = ny_bruker.Epost;
                    emailMessage.To.Add(mottaker);
                    await smtpClient.SendMailAsync(emailMessage); // sends mail*/
                    
                    return "";

                }
                else
                {
                    // User already exists
                    return "Bruker finnes allerede";
                }

            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                   "Inner Exception: " + e.InnerException +
                   "Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<string> EditUser(InnBruker bruker)
        {
            // InnBruker bruker contains edited user information
            // The user will only send inn InnBruker-object that have no null-values
            try
            {
                var enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == bruker.Brukernavn);
                if (enBruker != null)
                {
                    // bruker exists
                    // check if new postnr has changed
                    if (enBruker.Postnr.Postnr != bruker.Postnr || enBruker.Postnr.Postnr == null)
                    {
                        // postnr has changed
                        // does it exist in our db?
                        var postNr = await _db.postadresser.FindAsync(bruker.Postnr);
                        if (postNr == null)
                        {
                            // it does not exist and will be added
                            var nyPostadr = new Postadresse();
                            nyPostadr.Postnr = bruker.Postnr;
                            nyPostadr.Poststed = bruker.Poststed;
                            _db.postadresser.Add(nyPostadr); // add to db


                            enBruker.Postnr = nyPostadr;
                        }
                        else
                        {
                            // it exists in our db
                            enBruker.Postnr = postNr;
                        }
                    }
                    enBruker.Fornavn = bruker.Fornavn;
                    enBruker.Etternavn = bruker.Etternavn;
                    enBruker.Adresse = bruker.Adresse;
                    enBruker.Tlf = bruker.Tlf;
                    enBruker.Epost = bruker.Epost;
                    await _db.SaveChangesAsync();
                    return "";
                }
                else
                {
                    // bruker does not exist
                    return "Bruker finnes ikke";
                }
            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                    "Inner Exception: " + e.InnerException +
                    "Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<string> DeleteUser(string brukernavn, string passord)
        {
            // asks user to input their password
            // done in backend as we need the user's salt for comparing hashes
            try
            {
                var enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (enBruker != null)
                {
                    // user exists, so compare passwords
                    var hash = CreateHash(passord, enBruker.Salt);
                    if (hash.SequenceEqual(enBruker.Passord))
                    {
                        // password is correct, so delete all funn attached to the user
                        _db.funn.FromSqlRaw("DELETE FROM funn WHERE BrukerUserID = " + enBruker.UserID);

                        // delete user
                        _db.brukere.Remove(enBruker);
                        await _db.SaveChangesAsync();

                        return "";
                    }
                    else
                    {
                        // password is incorrect, try again
                        return "Wrong password";
                    }
                }
                else
                {
                    // user does not exist
                    return "User does not exist";
                }
            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                    "Inner Exception: " + e.InnerException +
                    "Stack Trace: " + e.StackTrace);
            }
        }
        public async Task<Bruker> GetUser(String brukernavn)
        {
            // Get one user's information

            try
            {
                Bruker enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (enBruker != null)
                {
                    // user exists
                    return enBruker;
                }
                else
                {
                    // user does not exist
                    return null;
                }
            }
            catch (Exception e)
            {
                return null;
            }
        }

        public async Task<string> LogIn(string brukernavn, string passord)
        {
            try
            {
                Bruker enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (enBruker != null)
                {
                    // user exists, check password
                    byte[] hash = CreateHash(passord, enBruker.Salt);
                    if (hash.SequenceEqual(enBruker.Passord))
                    {
                        // hash = the hash in our db. password is correct
                        enBruker.LoggetInn = true;
                        await _db.SaveChangesAsync();

                        return "";
                    }
                    else
                    {
                        // wrong password
                        return "Feil passord";
                    }
                }
                else
                {
                    // user does not exist, try again
                    return "Bruker finnes ikke";
                }
            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                    "Inner Exception: " + e.InnerException +
                    "Stack Trace: " + e.StackTrace);
            }

        }

        public async Task<string> LogOut(string brukernavn)
        {
            try
            {
                Bruker enBruker = await _db.brukere.FirstOrDefaultAsync(b => b.Brukernavn == brukernavn);
                if (enBruker != null)
                {
                    // user is found
                    if (enBruker.LoggetInn)
                    {
                        // user is logged in, so log out
                        enBruker.LoggetInn = false;
                        await _db.SaveChangesAsync();
                        return "";
                    }
                    // user found, but not logged in
                    return "Bruker er ikke logget inn";
                }
                // user is not found
                return "Bruker finnes ikke";
            }
            catch (Exception e)
            {
                return ("Message: " + e.Message +
                     "Inner Exception: " + e.InnerException +
                     "Stack Trace: " + e.StackTrace);
            }
        }
    }
}
