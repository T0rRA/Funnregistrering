﻿using FunnregistreringsAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public interface FunnRepositoryInterface
    {
        public Task<bool> RegistrerFunn(Funn nyttFunn);
    }
}