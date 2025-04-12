using System.Collections.Generic;
using csharp_avalonia.domain;
using csharp_avalonia.repository;

namespace csharp_avalonia.service;

public class DonatorService
{
    private readonly DonatorRepo0 donatorRepo;

    public DonatorService(DonatorRepo0 donatorRepo1)
    {
        donatorRepo = donatorRepo1;
    }

    public List<Donator> SearchByName(string partialName)
    {
        return donatorRepo.FindByNameContaining(partialName);
    }

    public Donator FindByFullName(string donorName)
    {
        return donatorRepo.FindByFullName(donorName);
    }

    public Donator save(Donator donator)
    {
        return donatorRepo.save(donator);
    }
}

