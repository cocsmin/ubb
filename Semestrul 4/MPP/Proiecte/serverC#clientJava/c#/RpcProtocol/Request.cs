using modelnet;

namespace networkingnet.RpcProtocol;

public class Request
{
    public RequestType Type { get; set; }
    
    public Voluntar Voluntar { get; set; }
    
    public Donator Donator { get; set; }
    
    public Caz Caz { get; set; }
    
    public Donatie Donatie { get; set; }
    
    public string nume { get; set; }
    
    public string password { get; set; }
    
    public string pname { get; set; }
    
    public string fname { get; set; }
    
    public int idCaz { get; set; }
    
    public int idDonator { get; set; }
    
    public int sumaDonatas { get; set; }

    public override string ToString()
    {
        return string.Format("Request[type={0}, user={1}, message={2}, data={3}]",
            Type,
            Voluntar,
            Caz,Donator,Donatie);    }
}