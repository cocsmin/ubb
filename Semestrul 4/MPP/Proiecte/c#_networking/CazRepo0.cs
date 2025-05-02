using System.Collections.Generic;
using modelnet;

namespace persistancenet;

public interface CazRepo0 : Repository<Caz, int>
{
    public IEnumerable<Caz> findAll();
}