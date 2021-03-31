using FunnregistreringsAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public interface BrukerRepositoryInterface
    {
        public Task<bool> CreateUser(InnBruker bruker);

        public Task<bool> SendPwResetLink(string brukernavn);

        public Task<bool> ChangePassword(String brukernavn, String token, String newPassword, String newPassword2);

        public Task<bool> EditUser(InnBruker bruker);

        public Task<bool> DeleteUser(string brukernavn, string passord);

        public Task<InnBruker> GetUser(InnBruker bruker);

        public Task<bool> LogIn(string brukernavn, string passord);
        public Task<bool> LogOut(string brukernavn);
        public Task<bool> CheckIfUserLoggedIn(string brukernavn);
    }
}
