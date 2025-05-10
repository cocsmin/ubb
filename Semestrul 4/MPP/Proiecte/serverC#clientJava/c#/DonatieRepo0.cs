using modelnet;

namespace persistancenet;

public interface DonatieRepo0 : Repository<Donatie, int>
{
    int GetSumaDonatiiPentruCaz(int cazId);
}