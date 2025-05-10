using modelnet;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
namespace servicesnet;

public interface IProjectServices
{
    Voluntar login(String username, String password, IProjectObserver client);

    //void logout(IProjectObserver client) throws Exception;
    Donatie saveDonatie(Donatie donatie);

    List<Caz> findAllCaz();

    List<CazDTO> createCazDTOList();

    List<Donator> searchByName(String partialName);

    Donator saveDonator(Donator donator);

    Donator findByFullName(String numeDonator);

    int getSumaDonatiiPentruCaz(int cazId);


    void logout(String username, IProjectObserver client);
    IEnumerable<Voluntar> findAllVoluntari();
}