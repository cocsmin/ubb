using modelnet;

namespace networkingnet.RpcProtocol;

public class Response
{
    public ResponseType Type { get; set; }
    
    public string ErrorMessage { get; set; }
    
    public Voluntar Voluntar { get; set; }
    
    public Caz Caz { get; set; }
    
    public Donator Donator { get; set; }
    
    public Donatie Donatie { get; set; }
    
    public int SumaPentruCaz { get; set; }
    
    public IEnumerable<Donator> Donatori { get; set; }
    
    public IEnumerable<Caz> Cazuri { get; set; }
    
    public IEnumerable<Voluntar> Voluntari { get; set; }
    
    public List<CazDTO> CazuriDTO { get; set; }
    
    public override string ToString()
    {
        return string.Format("[type={0}, error={1}, user={2}]",
            Type,
            ErrorMessage);
    }
}