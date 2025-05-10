using System.Text.Json.Serialization;

namespace modelnet;

public class Donator : Entity<int>
{
    private string nume_donator;
    private string adresa;
    private string telefon;

    public Donator(int id, string nume_donator, string adresa, string telefon)
    {
        this.id = id;
        this.nume_donator = nume_donator;
        this.adresa = adresa;
        this.telefon = telefon;
    }

    [JsonConstructor]
    public Donator(string nume_donator, string adresa, string telefon)
    {
        this.nume_donator = nume_donator;
        this.adresa = adresa;
        this.telefon = telefon;
    }
    
    public string Nume_donator { get => nume_donator; set => nume_donator = value; }
    
    public string Adresa { get => adresa; set => adresa = value; }
    
    public string Telefon { get => telefon; set => telefon = value; }
    
}