using System.Text.Json.Serialization;

namespace modelnet;

public class CazDTO
{
    public string NumeCaz { get; set; }
    public int SumaDons { get; set; }
    [JsonConstructor]
    public CazDTO(string numeCaz, int sumaDons)
    {
        NumeCaz = numeCaz;
        SumaDons = sumaDons;
    }
}