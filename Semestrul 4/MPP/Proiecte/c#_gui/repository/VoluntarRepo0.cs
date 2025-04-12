using csharp_avalonia.domain;

namespace csharp_avalonia.repository;

public interface VoluntarRepo0 : Repository<Voluntar, int>
{
  Voluntar Login(string username, string password);
  public Voluntar FindByUsername(string username);
    
}