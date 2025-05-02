using modelnet;

namespace persistancenet;

public interface VoluntarRepo0 : Repository<Voluntar, int>
{
  Voluntar Login(string username, string password);
  public Voluntar FindByUsername(string username);
    
}