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
        public Task<bool> RegistrerFunn(Funn nyttfunn);
        
        //A method to get all finds for a user.
        //remember change this to task
        //Has to take inn a user (?) or what do we do

        public Task<List<Funn>> GetAllUserFunn(InnBruker ib);


        public Task<bool> DeleteFunn(Funn f);
        public Task<Funn> GetFunn(List<Funn> funnListe);
        //what do i include even wth
        //what else might be required?

        public Task<string> GeneratePdf();

    }
}
