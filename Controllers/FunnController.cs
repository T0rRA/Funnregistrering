using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using FunnregistreringsAPI.DAL;
using FunnregistreringsAPI.Models;

namespace FunnregistreringsAPI.Controllers
{
    [Route("[controller]/[action]")]
    public class FunnController : ControllerBase
    {
        private readonly FunnRepositoryInterface _db;

        public FunnController(FunnRepositoryInterface db)
        {
            _db = db;
        }
        //We have to make these functions secure. Right now these can be injected if they have the webserver API and the function. 
        //Is it feasible to every time person logs in the "password" is physically saved on the device so that we can confirm their status?
        public async Task<ActionResult> RegistrerFunn(Funn nyttFunn, String brukernavn)
        {
            bool regOK =  await _db.RegistrerFunn(nyttFunn, brukernavn);
            if(!regOK) { return NotFound("Funn ble ikke registrert"); }
            return Ok("Funn er opprettet");
        }
        public async Task<ActionResult> GetAllUserFunn(String brukernavn, String passord)
        {
            var getListOk = await _db.GetAllUserFunn(brukernavn, passord);
            if (getListOk == null) return NotFound("Kunne ikke hente funnliste");
            return Ok("Lista er hentet");
        }
        public async Task<ActionResult> DeleteFunn(int funnID)
        {
            bool deleteOK = await _db.DeleteFunn(funnID);
            if (!deleteOK) return NotFound("Funnet kunne ikke slettes");
            return Ok("Funnet ble slettet");
        } 
        public async Task<ActionResult> EditFunn(Funn f)
        {
            bool editOK = await _db.EditFunn(f);
            if (!editOK) return NotFound("Funnet ble ikke endret");
            return Ok("Funnet ble endret");
        }
        public async Task<ActionResult> GetFunn(String brukernavn, int funnID)
        {
            var getOk = await _db.GetFunn(brukernavn, funnID);
            if (getOk == null) return NotFound("Funnet kunne ikke hentes");
            return Ok("Funnet ble hentet");
        }

    }
}
