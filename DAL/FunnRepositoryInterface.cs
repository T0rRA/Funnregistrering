using Funnregistrering.Models;
using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public interface FunnRepositoryInterface
    {
        public Task<string> RegistrerFunn(InnFunn nyttfunn, String brukernavn);
         public Task<List<Funn>> GetAllUserFunn(String brukernavn, String passord);
        public Task<string> DeleteFunn(int funnID);
        public Task<string> EditFunn(Funn f);
        public Task<Funn> GetFunn(String brukernavn, int funnID);
        public Task<GBNr> GetGBNr(string gbnr);
    }
}
