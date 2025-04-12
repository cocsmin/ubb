using System.Collections.Generic;
using csharp_avalonia.domain;

namespace csharp_avalonia.repository;

public interface CazRepo0 : Repository<Caz, int>
{
    public IEnumerable<Caz> findAll();
}