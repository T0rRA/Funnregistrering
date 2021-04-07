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
        public async Task<bool> RegistrerFunn(Funn nyttFunn, String brukernavn)
        {
            return await _db.RegistrerFunn(nyttFunn, brukernavn);
        }
        public async Task<List<Funn>> GetAllUserFunn(String brukernavn, String passord)
        {
            return await _db.GetAllUserFunn(brukernavn, passord);
        }
        public async Task<bool> DeleteFunn(int funnID)
        {
            return await _db.DeleteFunn(funnID);
        } 
        public async Task<bool> EditFunn(Funn f)
        {
            return await _db.EditFunn(f);
        }
        public async Task<Funn> GetFunn(List<Funn> funnListe, int funnID)
        {
            return await _db.GetFunn(funnListe, funnID);
        }

        public async Task<string> GeneratePdf(string f)
        {
            return await _db.GeneratePdf(f);
        }

    }
}
