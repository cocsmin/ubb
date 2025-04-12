using System.Collections.Generic;
using csharp_avalonia.domain;
using csharp_avalonia.repository;
using csharp_avalonia.service;

public class CazService
{
    private readonly CazRepo0 cazRepo;

    public CazService(CazRepo0 cazRepo1)
    {
        cazRepo = cazRepo1;
    }

    public IEnumerable<Caz> FindAll()
    {
        return cazRepo.findAll();
    }

    public List<CazDTO> CreateCazDTOList(IEnumerable<Caz> cazuri, DonatieService donatieService)
    {
        var dtoList = new List<CazDTO>();
        foreach (var caz in cazuri)
        {
            int sumaDonatii = donatieService.GetSumaDonatiiPentruCaz(caz.Id);
            dtoList.Add(new CazDTO(caz.Nume_caz, sumaDonatii));
        }

        return dtoList;
    }
}

