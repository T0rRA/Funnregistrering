using System;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using FunnregistreringsAPI.DAL;

using FunnregistreringsAPI.Models;
using Newtonsoft.Json;
using System.IO;
using System.Collections.Generic;
using System.Web.Http;
using System.Net.Http;
using System.Net;
using System.Web;
using Microsoft.AspNetCore.Http;
using Funnregistrering.Models;

namespace FunnregistreringsAPI.Controllers
{
    [Microsoft.AspNetCore.Mvc.Route("[controller]/[action]")]
    public class FunnController : ControllerBase
    {
        private readonly FunnRepositoryInterface _db;

        public FunnController(FunnRepositoryInterface db)
        {
            _db = db;
        }
        //We have to make these functions secure. Right now these can be injected if they have the webserver API and the function. 
        //Is it feasible to every time person logs in the "password" is physically saved on the device so that we can confirm their status?

        [System.Web.Http.HttpPost]
        public async Task<ActionResult> RegistrerFunn([System.Web.Http.FromBody] InnFunn nyttFunn, string brukernavn)
        {
            try
            {
                //if (ModelState.IsValid)
                //{
                if (nyttFunn == null)
                {
                    return BadRequest("There is no funn to be registered");
                }

                string regOK = await _db.RegistrerFunn(nyttFunn, brukernavn);
                if (regOK != "") return NotFound(regOK);
                return Ok("Funn is registered!");
                //}
                //return BadRequest("Bad Request 400");
            } catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }

        [System.Web.Http.HttpGet]
        public async Task<ActionResult> GetAllUserFunn(String brukernavn, String passord)
        {
            
            try
            {
                //if (ModelState.IsValid)
                //{
                var getListOk = await _db.GetAllUserFunn(brukernavn, passord);
                if (getListOk == null) return NotFound("No list exists");
                return Ok(getListOk);
                //}
            }
            catch(Exception e)
            {
                return BadRequest(e.Message);
            }
        }

        [System.Web.Http.HttpDelete]
        public async Task<ActionResult> DeleteFunn(int funnID)
        {
            try
            {
                var deleteOK = await _db.DeleteFunn(funnID);
                if (deleteOK != "") return NotFound(deleteOK);
                return Ok("Funn was successfully deleted");
            }
            catch(Exception e)
            {
                return BadRequest(e.Message);
            }
        } 

        [System.Web.Http.HttpPut]
        public async Task<ActionResult> EditFunn(Funn f)
        {
            try
            {
                if (f == null)
                {
                    return NotFound("Funn is empty/not found");
                }
                var editOK = await _db.EditFunn(f);
                if (editOK != "") return NotFound(editOK);
                return Ok("Funn was edited");
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }

        }

        [System.Web.Http.HttpGet]
        public async Task<ActionResult> GetFunn(String brukernavn, int funnID)
        {
            try
            {
                if (brukernavn == null)
                {
                    return NotFound("User does not exist");
                }
                if (funnID == 0)
                {
                    return NotFound("Funn does not exist");
                }
                var getOk = await _db.GetFunn(brukernavn, funnID);
                if (getOk == null) return NotFound("Could not get funn, your list might be empty");
                return Ok(getOk);
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }

        }

        public async Task<ActionResult> GetGBNr(string gbnr)
        {
            try
            {
                var getNr = await _db.GetGBNr(gbnr);
                if (getNr != null) return Ok(getNr);
                return NotFound("Fant ikke gårdsbrukseier");
            }
            catch (Exception e)
            {
                return BadRequest(e.Message);
            }
        }

    }
}
