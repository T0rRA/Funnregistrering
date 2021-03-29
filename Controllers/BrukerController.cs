using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FunnregistreringsAPI.DAL;
using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace FunnregistreringsAPI.Controllers
{


    [Route("[controller]/[action]")]
    public class BrukerController : ControllerBase
    {

        private readonly BrukerRepositoryInterface _db;

        public BrukerController(BrukerRepositoryInterface db)
        {
            _db = db;
        }

        public async Task<ActionResult> CreateUser(InnBruker bruker, string pw)
        {
            bool createOk = await _db.CreateUser(bruker, pw);
            if(!createOk) { return NotFound("Bruker ble ikke opprettet"); }
            return Ok("Bruker er opprettet.");
        }

        public async Task<ActionResult> SendResetPwLink(InnBruker bruker)
        {
            var loggedIn = await _db.CheckIfUserLoggedIn(bruker);
            if (loggedIn)
            {
                bool sendOk = await _db.SendPwResetLink(bruker);
                if(!sendOk) { return NotFound("Kunne ikke sende lenke."); }
                return Ok("Du har fått en epost med en lenke for å endre passord.");
            }
            return BadRequest("Feil på server");
        }

        public async Task<ActionResult> ChangePassword(InnBruker bruker, String token, string newPassword, string newPassword2)
        {
            bool changeOk = await _db.ChangePassword(bruker, token, newPassword, newPassword2);
            if(!changeOk) { return NotFound("Kunne ikke endre passord."); }
            return Ok("Passordet er endret.");
        }

        public async Task<ActionResult> EditUser(InnBruker bruker)
        {
            var loggedIn = await _db.CheckIfUserLoggedIn(bruker);
            if (loggedIn)
            {
                bool editOk = await _db.EditUser(bruker);
                if(!editOk) { return NotFound("Endring av bruker ikke lagret"); }
                return Ok("Bruker endringer lagret");
            }
            return BadRequest("Feil på server");
        }

        public async Task<ActionResult> DeleteUser(InnBruker bruker, string pw)
        {
            var loggedIn = await _db.CheckIfUserLoggedIn(bruker);
            if (loggedIn)
            {
                bool deleteOk = await _db.DeleteUser(bruker, pw);
                if(!deleteOk) { return NotFound("Kunne ikke slette brukeren"); }
                return Ok("Bruker er slettet");
            }
            return BadRequest("Error på server");
        }

        public async Task<ActionResult> GetUser(InnBruker bruker)
        {

            var loggedIn = await _db.CheckIfUserLoggedIn(bruker);
            if (loggedIn) 
            {
                InnBruker enBruker = await _db.GetUser(bruker);
                if(enBruker == null)
                {
                    // User not found
                    return NotFound("Bruker ikke funnet");
                }
                return Ok(enBruker);
            }
            return BadRequest("Error på server");
        }
        
        public async Task<ActionResult> LogIn(string brukernavn, string passord)     
        {
           // var loggedIn = await _db.CheckIfUserLoggedIn(bruker);
            // Check if user is logged in
           // if (!loggedIn)
            //{
                // User is not logged in
                bool success = await _db.LogIn(brukernavn, passord);
                if (!success) { return Ok(false); } // log-in info is incorrect
                return Ok(true); // user logged in
            //}
            // Or a redirection? 
            // Since a user shouldnt be able to log in when theyre already logged in
            //return BadRequest("400 error på server i guess");
        }

        public async Task<ActionResult> LogOut(InnBruker bruker)
        {
            var loggedIn = await _db.CheckIfUserLoggedIn(bruker);
            if(loggedIn)
            {
                // user is logged in
                bool loggedOut = await _db.LogOut(bruker);
                if(!loggedOut) 
                { 
                    return NotFound("Bruker kunne ikke logges ut"); 
                }
                return Ok(true); // User logged out
            }
            return BadRequest("Feil på server.");
        }

        public async Task<ActionResult> CheckIfUserLoggedIn(InnBruker bruker)
        {
            bool checkOk = await _db.CheckIfUserLoggedIn(bruker);
            if (!checkOk) return Ok(false);
            return Ok(true);
        }
    }
}
