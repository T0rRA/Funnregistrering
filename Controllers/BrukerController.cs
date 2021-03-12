using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using FunnregistreringsAPI.DAL;
using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Mvc;

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
        public async Task<bool> ChangePassword()
        {
            return await _db.ChangePassword();
        }
    }
}
