namespace csharp_avalonia.domain;

public class CazDTO
{
    public string NumeCaz { get; set; }
    public int SumaDons { get; set; }

    public CazDTO(string numeCaz, int sumaDons)
    {
        NumeCaz = numeCaz;
        SumaDons = sumaDons;
    }
}