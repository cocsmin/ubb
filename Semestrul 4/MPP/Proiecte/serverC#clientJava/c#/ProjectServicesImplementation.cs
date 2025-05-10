using modelnet;
using persistancenet;
using servicesnet;

namespace servernet;

public class ProjectServicesImplementation : IProjectServices
{
    public VoluntarRepo0 voluntarRepo { get; set; }
    public DonatorRepo0 donatorRepo { get; set; }
    public CazRepo0 cazRepo { get; set; }
    public DonatieRepo0 donatieRepo { get; set; }

    private readonly IDictionary<String, IProjectObserver> loggedClients;

    public ProjectServicesImplementation(VoluntarRepo0 voluntarRepo, DonatorRepo0 donatorRepo, CazRepo0 cazRepo,
        DonatieRepo0 donatieRepo)
    {
        this.voluntarRepo = voluntarRepo;
        this.donatorRepo = donatorRepo;
        this.cazRepo = cazRepo;
        this.donatieRepo = donatieRepo;
        
        loggedClients = new Dictionary<String, IProjectObserver>();
    }

    public Voluntar login(string username, string password, IProjectObserver client)
    {
        Console.WriteLine($"Încercare login pentru: {username}");
        Voluntar validVoluntar = voluntarRepo.FindByUsername(username);
        bool passwordValid = false;
        if (validVoluntar != null)
        {
            //if (validVoluntar.Password == password)
               // passwordValid = true;
            
            if (BCrypt.Net.BCrypt.Verify(password, validVoluntar.Password))
                passwordValid = true;
            Console.WriteLine($"Parolă validă: {passwordValid}");
            if (passwordValid)
            {
                if (loggedClients.ContainsKey(username))
                {
                    throw new ProjectException("Utilizator deja conectat.");
                }
                loggedClients[username] = client;
                return validVoluntar;
            }
            else
            {
                throw new ProjectException("Parolă incorectă.");
            }
        }
        else
        {
            Console.WriteLine("Utilizator inexistent!");
            throw new ProjectException("Nume de utilizator invalid.");
        }
    }
    

    public void logout(string username, IProjectObserver client)
    {
        IProjectObserver clientToRemove = loggedClients[username];
        if (clientToRemove == null)
        {
            throw new ProjectException("User not logged in");
        }
        loggedClients.Remove(username);
    }

    public IEnumerable<Voluntar> findAllVoluntari()
    {
        try
        {
            List<Voluntar> voluntari;
            voluntari = voluntarRepo.findAll().ToList();

            return voluntari;
        }
        catch (Exception e)
        {
            throw new ProjectException("Unable to fetch caz list", e);
        }
    }

    public List<Caz> findAllCaz()
    {
        try
        {
            List<Caz> cazuri;
            cazuri = (List<Caz>)cazRepo.findAll();

            return cazuri;
        }
        catch (Exception e)
        {
            throw new ProjectException("Unable to fetch caz list", e);
        }
    }

    public List<CazDTO> createCazDTOList()
    {
        List<Caz> cazuri = cazRepo.findAll().ToList();
        List<CazDTO> cazdto = new List<CazDTO>();
        foreach (Caz caz in cazuri)
        {
            int sumaDonatii = donatieRepo.GetSumaDonatiiPentruCaz(caz.Id);
            CazDTO dto = new CazDTO(caz.Nume_caz, sumaDonatii);
            cazdto.Add(dto);
        }
        return cazdto;
    }
    
    public Donator saveDonator(Donator donator)
    {
        try
        {
            donator = donatorRepo.save(donator);
            return donator;
        }
        catch (Exception e)
        {
            throw new ProjectException("Unable to save donator", e);
        }
    }

    public int getSumaDonatiiPentruCaz(int cazId)
    {
        try
        {
            int suma = donatieRepo.GetSumaDonatiiPentruCaz(cazId);
            return suma;
        }
        catch (Exception e)
        {
            throw new ProjectException("Unable to get suma donatii ptcaz", e);
        }
    }

    public List<Donator> searchByName(string name)
    {
        try
        {
            List<Donator> donators = donatorRepo.FindByNameContaining(name);
            return donators;
        }
        catch (Exception e)
        {
            throw new ProjectException("Unable to search by name", e);
        }
    }

    public Donator findByFullName(string name)
    {
        {
            Donator donator = donatorRepo.FindByFullName(name);
            return donator;
        }
        
    }

    public Donatie saveDonatie(Donatie donatie)
    {
        Donatie d;
        try
        {
            Console.WriteLine($"Salvare donație: CazId={donatie.Caz?.Id}, DonatorId={donatie.Donator?.Id}");
            d = donatieRepo.save(donatie);
            notifyObs(donatie);
            return d;
        }
        catch (Exception e)
        {
            Console.WriteLine($"Eroare la salvarea donației: {e.Message}");
            throw new ProjectException("Unable to save donatie", e);
        }

    }
    
    

    private void notifyObs(Donatie donatie)
    {
        foreach (var client in loggedClients.Values)
        {
            try
            {
                client.notifyObservers(donatie);
            }
            catch (ProjectException e)
            {
                Console.WriteLine("Error in notifying observers " + e);
            }
        }
    }

}