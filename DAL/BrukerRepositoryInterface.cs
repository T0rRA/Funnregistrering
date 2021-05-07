using FunnregistreringsAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public interface BrukerRepositoryInterface
    {
        public Task<string> CreateUser(InnBruker bruker);

        public Task<string> EditUser(InnBruker bruker);

        public Task<string> DeleteUser(string brukernavn, string passord);

        public Task<Bruker> GetUser(String brukernavn);

        public Task<string> LogIn(string brukernavn, string passord);
        public Task<string> LogOut(string brukernavn);
    }
}
