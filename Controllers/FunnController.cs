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
    [ApiController]
    public class FunnController : ControllerBase
    {
        private readonly FunnRepositoryInterface _db;

        public FunnController(FunnRepositoryInterface db)
        {
            _db = db;
        }
        //We have to make these functions secure. Right now these can be injected if they have the webserver API and the function. 
        //Is it feasible to every time person logs in the "password" is physically saved on the device so that we can confirm their status?
        public async Task<List<Funn>> GetAllUserFunn(InnBruker ib)
        {
            if (ModelState.IsValid)
            {
                return await _db.GetAllUserFunn(ib);
            }
            else
            {
                return null;
            }
            
        }

        public async Task<bool> RegistrerFunn(InnFunn nyttFunn, InnBruker ib)
        {
            throw new NotImplementedException();
        }
        public async Task<bool> DeleteFunn(Funn f)
        {
            throw new NotImplementedException();
        }
    }
}
