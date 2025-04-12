using csharp_avalonia.domain;

namespace csharp_avalonia.repository;

public interface DonatieRepo0 : Repository<Donatie, int>
{
    int GetSumaDonatiiPentruCaz(int cazId);
}