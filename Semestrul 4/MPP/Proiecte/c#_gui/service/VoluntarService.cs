using System;
using System.Threading.Tasks;
using csharp_avalonia.domain;
using csharp_avalonia.repository;

namespace csharp_avalonia.service;

public interface IVoluntarService
{
    Task<Voluntar> Login(string username, string password);
}

public class VoluntarService
{
    private readonly VoluntarRepo0 voluntarRepo;

    public VoluntarService(VoluntarRepo voluntarRepo1)
    {
        voluntarRepo = voluntarRepo1;
    }

    public Voluntar Login(string username, string parola)
    {
        Voluntar voluntar = voluntarRepo.Login(username, parola);
        if (voluntar != null)
            return voluntar;
        throw new System.Exception("Date de autentificare incorecte!");
    }
}
