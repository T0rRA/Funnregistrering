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

namespace FunnregistreringsAPI.Controllers
{
    [Microsoft.AspNetCore.Mvc.Route("[controller]/[action]")]
    public class FunnController : ApiController
    {
        private readonly FunnRepositoryInterface _db;

        public FunnController(FunnRepositoryInterface db)
        {
            _db = db;
            Configuration = new System.Web.Http.HttpConfiguration();
            Request = new System.Net.Http.HttpRequestMessage();
        }


        //We have to make these functions secure. Right now these can be injected if they have the webserver API and the function. 
        //Is it feasible to every time person logs in the "password" is physically saved on the device so that we can confirm their status?

        [System.Web.Http.HttpPost]
        public async Task<IHttpActionResult> RegistrerFunn([System.Web.Http.FromBody] InnFunn nyttFunn, string brukernavn)
        {
            try
            {
                //if (ModelState.IsValid)
                //{
                if (nyttFunn == null)
                {
                    return BadRequest();
                }

                bool regOK = await _db.RegistrerFunn(nyttFunn, brukernavn);
                if (!regOK) return BadRequest("Did not work out well");
                return Ok("Funn is given!");
                //}
                //return BadRequest("Bad Request 400");
            } catch (Exception e)
            {
                return InternalServerError();
            }
        }

        [System.Web.Http.HttpGet]
        public async Task<IHttpActionResult> GetAllUserFunn(String brukernavn, String passord)
        {
            //if (ModelState.IsValid)
            //{
            var getListOk = await _db.GetAllUserFunn(brukernavn, passord);
            if (getListOk == null) return BadRequest();
            return Ok();
            //}
            //return BadRequest("BadRequest request 400");
        }

        [System.Web.Http.HttpDelete]
        public async Task<IHttpActionResult> DeleteFunn(int funnID)
        {
            bool deleteOK = await _db.DeleteFunn(funnID);
            if (!deleteOK) return NotFound();
            return Ok();
        } 

        [System.Web.Http.HttpPut]
        public async Task<IHttpActionResult> EditFunn(Funn f)
        {
            if (f == null)
            {
                return BadRequest();
            }
            bool editOK = await _db.EditFunn(f);
            if (!editOK) return NotFound();
            return Ok();
        }

        [System.Web.Http.HttpGet]
        public async Task<IHttpActionResult> GetFunn(String brukernavn, int funnID)
        {
            if(brukernavn == null)
            {
                return BadRequest();
            }
            if (funnID == null)
            {
                return BadRequest();
            }
            var getOk = await _db.GetFunn(brukernavn, funnID);
            if (getOk == null) return NotFound();
            return Ok();
        }

       /* [Microsoft.AspNetCore.Mvc.HttpPost]
        public bool dJ(String jsonStr)
        {
            bool imgOk =  _db.dJ(jsonStr);
            if (!imgOk) return true;
            return false;
        }*/


    }
}
