namespace csharp_avalonia.domain;

public class Caz : Entity<int>
{
    private string nume_caz;
    private string descriere;

    public Caz(int id, string nume_caz, string descriere)
    {
        this.id = id;
        this.nume_caz = nume_caz;
        this.descriere = descriere;
    }

    public Caz(string nume_caz, string descriere)
    {
        this.nume_caz = nume_caz;
        this.descriere = descriere;
    }
    
    
    public string Nume_caz { get => nume_caz; set => nume_caz = value; }
    
    public string Descriere { get => descriere; set => descriere = value; }

    public override string ToString()
    {
        return nume_caz;
    }
}