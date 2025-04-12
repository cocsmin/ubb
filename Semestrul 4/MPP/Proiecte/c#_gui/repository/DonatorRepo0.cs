using System.Collections.Generic;
using csharp_avalonia.domain;

namespace csharp_avalonia.repository;

public interface DonatorRepo0 : Repository<Donator, int>
{
    List<Donator> FindByNameContaining(string partialName);
    Donator FindByFullName(string donorName);
}