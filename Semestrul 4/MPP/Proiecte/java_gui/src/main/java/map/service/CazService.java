package map.service;

import map.domain.Caz;
import map.domain.CazDTO;
import map.repository.database.CazDBRepo;
import map.repository.database.CazRepo0;

import java.util.ArrayList;
import java.util.List;

public class CazService {

    private CazRepo0 cazDBRepo;

    public CazService(CazDBRepo cazDBRepo) {
        this.cazDBRepo = cazDBRepo;
    }

    public List<Caz> findAll() {
        return (List<Caz>) cazDBRepo.findAll();
    }

    public List<CazDTO> createCazDTOList(List<Caz> cazuri, DonatieService donatieService) {
        if (cazuri == null || cazuri.isEmpty()) {
            return List.of();
        }

        List<CazDTO> cazDTOList = new ArrayList<>();
        for (Caz caz : cazuri) {
            int sumaDonatii = donatieService.getSumaDonatiiPentruCaz(caz.getId());

            CazDTO dto = new CazDTO(caz.getNume_caz(), sumaDonatii);
            cazDTOList.add(dto);
        }
        return cazDTOList;
    }

    public Caz findById(Integer id) {
        return null;
    }
}
