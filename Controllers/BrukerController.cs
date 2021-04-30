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

        public async Task<ActionResult> CreateUser(InnBruker bruker)
        {
            try
            {
                //if (ModelState.IsValid)
                //{
                string createOk = await _db.CreateUser(bruker);
                if (createOk != "") { return NotFound(createOk); }
                return Ok("Bruker er opprettet.");
                // }
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<ActionResult> SendPwResetLink(String brukernavn)
        {
            try
            {
                var loggedIn = await _db.CheckIfUserLoggedIn(brukernavn);
                if (loggedIn)
                {
                    int sendOk = await _db.SendPwResetLink(brukernavn);
                    if (sendOk == 1) return Ok("Du har fått en epost med en lenke for å endre passord.");
                    if (sendOk == 2) { return NotFound("Bruker finnes ikke"); }
                    if (sendOk == 3) { return NotFound("Kunne ikke sende lenke (finner ikke epost)"); }
                    if (sendOk == 4) { return NotFound("Feil i kobling til SMTPClient"); }
                }
                return BadRequest();
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
            
        }

        public async Task<ActionResult> ChangePassword(String brukernavn, String token, String newPassword, String newPassword2)
        {
            bool changeOk = await _db.ChangePassword(brukernavn, token, newPassword, newPassword2);
            if(!changeOk) { return NotFound("Kunne ikke endre passord."); }
            return Ok("Passordet er endret.");
        }

        public async Task<ActionResult> EditUser(InnBruker bruker)
        {
            try
            {
                var loggedIn = await _db.CheckIfUserLoggedIn(bruker.Brukernavn);
                if (loggedIn)
                {
                    string editOk = await _db.EditUser(bruker);
                    if (editOk != "") { return NotFound(editOk); }
                    return Ok("Bruker endringer lagret");
                }
                return BadRequest("Bruker er ikke logget inn");
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<ActionResult> DeleteUser(string brukernavn, string passord)
        {
            try
            {
                var loggedIn = await _db.CheckIfUserLoggedIn(brukernavn);
                if (loggedIn)
                {
                    var deleteOk = await _db.DeleteUser(brukernavn, passord);
                    if (deleteOk != "") { return NotFound(deleteOk); }
                    return Ok("Bruker er slettet");
                }
                return BadRequest("Bruker er ikke logget inn");
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<ActionResult> GetUser(String brukernavn)
        {
            try
            {
                var loggedIn = await _db.CheckIfUserLoggedIn(brukernavn);
                //if (loggedIn) 
                //{
                Bruker enBruker = await _db.GetUser(brukernavn);
                if (enBruker == null)
                {
                    // User not found
                    return NotFound("Bruker ikke funnet");
                }
                return Ok(enBruker);
                //}
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }
        
        public async Task<ActionResult> LogIn(string brukernavn, string passord)     
        {
            try
            {
                var loggedIn = await _db.CheckIfUserLoggedIn(brukernavn);
                // Check if user is logged in
                if (!loggedIn)
                {
                    // User is not logged in
                    int logInOK = await _db.LogIn(brukernavn, passord);
                    if (logInOK == 1) return Ok(true);  // user logged in
                    else if (logInOK == 2) return NotFound("Feil passord.");
                    else if (logInOK == 3) return NotFound("Bruker finnes ikke.");
                    else if (logInOK == 4) return NotFound("Kunne ikke logge inn (bruker er allerede logget inn kanskje)");
                }
                // Or a redirection? 
                // Since a user shouldnt be able to log in when theyre already logged in
                return BadRequest();
            }
            catch (Exception e)
            {
                return BadRequest("Prøv å logg ut. - Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<ActionResult> LogOut(string brukernavn)
        {
            try
            {
                var loggedIn = await _db.CheckIfUserLoggedIn(brukernavn);
                if (loggedIn)
                {
                    // user is logged in
                    int loggedOut = await _db.LogOut(brukernavn);
                    if (loggedOut == 1) return Ok(true);
                    else if (loggedOut == 2) return NotFound("Bruker er ikke logget inn"); //redirect
                    else if (loggedOut == 3) return NotFound("Bruker ikke funnet");
                    else if (loggedOut == 4) return NotFound("Kunne ikke logge ut.");
                }
                return BadRequest();
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<ActionResult> CheckIfUserLoggedIn(string brukernavn)
        {
            try
            {
                bool checkOk = await _db.CheckIfUserLoggedIn(brukernavn);
                if (!checkOk) return Ok(false);
                return Ok(true);
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }
    }
}
