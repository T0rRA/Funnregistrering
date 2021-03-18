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

        public async Task<bool> CreateUser(InnBruker bruker)
        {
            return await _db.CreateUser(bruker);
        }

        public async Task<bool> SendResetPwLink(string epost)
        {
            return await _db.SendPwResetLink(epost);
        }

        public async Task<bool> ChangePassword(InnBruker bruker, String token, string newPassword, string newPassword2)
        {
            return await _db.ChangePassword(bruker, token, newPassword, newPassword2);
        }

        public async Task<bool> EditUser(InnBruker bruker)
        {
            return await _db.EditUser(bruker);
        }

        public async Task<bool> DeleteUser(InnBruker bruker)
        {
            return await _db.DeleteUser(bruker);
        }

        public async Task<InnBruker> GetUser(InnBruker bruker)
        {
            return await _db.GetUser(bruker);
        }
    }
}
