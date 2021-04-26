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
        public async Task<IHttpActionResult> RegistrerFunn([System.Web.Http.FromBody] InnFunn nyttFunn, string brukernavn)
        {
            try
            {
                //if (ModelState.IsValid)
                //{
                if (nyttFunn == null)
                {
                    return (IHttpActionResult)BadRequest();
                }

                bool regOK = await _db.RegistrerFunn(nyttFunn, brukernavn);
                if (!regOK) return (IHttpActionResult)BadRequest("Did not work out well");
                return (IHttpActionResult)Ok("Funn is given!");
                //}
                //return BadRequest("Bad Request 400");
            } catch (Exception e)
            {
                return (IHttpActionResult)StatusCode(500);
            }
        }

        [System.Web.Http.HttpGet]
        public async Task<IHttpActionResult> GetAllUserFunn(String brukernavn, String passord)
        {
            //if (ModelState.IsValid)
            //{
            var getListOk = await _db.GetAllUserFunn(brukernavn, passord);
            if (getListOk == null) return (IHttpActionResult)BadRequest();
            return (IHttpActionResult)Ok();
            //}
            //return BadRequest("BadRequest request 400");
        }

        [System.Web.Http.HttpDelete]
        public async Task<IHttpActionResult> DeleteFunn(int funnID)
        {
            bool deleteOK = await _db.DeleteFunn(funnID);
            if (!deleteOK) return (IHttpActionResult)NotFound();
            return (IHttpActionResult)Ok();
        } 

        [System.Web.Http.HttpPut]
        public async Task<IHttpActionResult> EditFunn(Funn f)
        {
            if (f == null)
            {
                return (IHttpActionResult)BadRequest();
            }
            bool editOK = await _db.EditFunn(f);
            if (!editOK) return (IHttpActionResult)NotFound();
            return (IHttpActionResult)Ok();
        }

        [System.Web.Http.HttpGet]
        public async Task<IHttpActionResult> GetFunn(String brukernavn, int funnID)
        {
            if (brukernavn == null)
            {
                return (IHttpActionResult)BadRequest();
            }
            if (funnID == null)
            {
                return (IHttpActionResult)BadRequest();
            }
            var getOk = await _db.GetFunn(brukernavn, funnID);
            if (getOk == null) return new NotFoundResult();
            return (IHttpActionResult)Ok();

        }

    }
}
