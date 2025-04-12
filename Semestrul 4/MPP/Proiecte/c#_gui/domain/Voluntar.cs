namespace csharp_avalonia.domain;

public class Voluntar : Entity<int>
{
    private string username;
    private string parola;
    private string nume_voluntar;

    public Voluntar(int id, string username, string password, string nume_voluntar)
    {
        this.id = id;
        this.username = username;
        this.parola = password;
        this.nume_voluntar = nume_voluntar;
    }

    public Voluntar(string username, string password, string nume_voluntar)
    {
        this.username = username;
        this.parola = password;
        this.nume_voluntar = nume_voluntar;
    }
    
    
    public string Username { get => username; set => username = value; }
    
    public string Password { get => parola; set => parola = value; }
    
    public string Nume_voluntar { get => nume_voluntar; set => nume_voluntar = value; }
}