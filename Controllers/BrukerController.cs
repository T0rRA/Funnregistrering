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

        public async Task<ActionResult> EditUser(InnBruker bruker)
        {
            try
            {
                    string editOk = await _db.EditUser(bruker);
                    if (editOk != "") { return NotFound(editOk); }
                    return Ok("Bruker endringer lagret");
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
                    var deleteOk = await _db.DeleteUser(brukernavn, passord);
                    if (deleteOk != "") { return NotFound(deleteOk); }
                    return Ok("Bruker er slettet");
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
                //if (ModelState.IsValid) 
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
                    var logInOK = await _db.LogIn(brukernavn, passord);
                    if (logInOK != "") return NotFound(logInOK);  // user logged in
                return Ok("User was logged in");
            }
            catch (Exception e)
            {
                return BadRequest("Prøv å logge ut. - Stack Trace: " + e.StackTrace);
            }
        }

        public async Task<ActionResult> LogOut(string brukernavn)
        {
            try
            {
                    // user is logged in
                    var loggedOut = await _db.LogOut(brukernavn);
                    if (loggedOut != "") return NotFound(loggedOut);
                return Ok("User was logged out");
            }
            catch (Exception e)
            {
                return BadRequest("Stack Trace: " + e.StackTrace);
            }
        }
    }
}
