﻿using FunnregistreringsAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public interface FunnRepositoryInterface
    {
        public Task<bool> RegistrerFunn(InnFunn nyttFunn, InnBruker ib);
        
        //A method to get all finds for a user.
        //remember change this to task
        //Has to take inn a user (?) or what do we do
        public List<Funn> GetAllUserFunn();
    }
}