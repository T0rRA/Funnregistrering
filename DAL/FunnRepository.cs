using FunnregistreringsAPI.Models;
using Microsoft.AspNetCore.Cryptography.KeyDerivation;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;

namespace FunnregistreringsAPI.DAL
{
    public class FunnRepository : FunnRepositoryInterface
    {
        public Task<bool> RegistrerFunn(Funn nyttFunn)
        {
            throw new NotImplementedException();

        }
    }
}
