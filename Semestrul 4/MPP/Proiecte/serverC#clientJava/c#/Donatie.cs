using System;
using System.Text.Json.Serialization;

namespace modelnet;

public class Donatie : Entity<int>
{
    private Donator donator;
    private Caz caz;
    [JsonPropertyName("data_donatie")]
    private DateTime data_donatie;
    private int suma_donata;

    public Donatie() {}
    
    public Donatie(int id, Donator d1, Caz c1, DateTime data_donatie, int suma_donata)
    {
        this.id = id;
        this.donator = d1;
        this.caz = c1;
        this.data_donatie = data_donatie;
        this.suma_donata = suma_donata;
        
    }

    public Donatie(Donator donator, Caz caz, DateTime dataDonatie, int sumaDonata)
    {
        this.donator = donator;
        this.caz = caz;
        this.data_donatie = dataDonatie;
        this.suma_donata = sumaDonata;
        
    }
    
    public Donator Donator { get => donator; set => donator = value; }
    
    public Caz Caz { get => caz; set => caz = value; }
    
    public DateTime Data_donatie { get => data_donatie; set => data_donatie = value; }
    
    public int Suma_donata { get => suma_donata; set => suma_donata = value; }
    
}