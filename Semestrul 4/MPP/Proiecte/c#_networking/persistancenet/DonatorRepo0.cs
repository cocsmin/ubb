using System.Collections.Generic;
using modelnet;

namespace persistancenet;

public interface DonatorRepo0 : Repository<Donator, int>
{
    List<Donator> FindByNameContaining(string partialName);
    Donator FindByFullName(string donorName);
}