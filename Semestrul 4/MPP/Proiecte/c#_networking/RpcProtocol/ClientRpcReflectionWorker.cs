using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using modelnet;
using networkingnet.Utils;
using servicesnet;

namespace networkingnet.RpcProtocol;

public class ClientRpcReflectionWorker : IProjectObserver
{
    private IProjectServices server;
    private TcpClient connection;

    private NetworkStream stream;
    private volatile bool connected;

    public ClientRpcReflectionWorker(IProjectServices server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            stream = connection.GetStream();
            connected = true;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    
    public virtual void Run()
    {
        using StreamReader reader = new StreamReader(connection.GetStream());
        while (connected)
        {
            try
            {
                string requestJson = reader.ReadLine(); // Read JSON line-by-line
                if (string.IsNullOrEmpty(requestJson)) continue;
                var options = new JsonSerializerOptions
                {
                    IncludeFields = true
                };
                Console.WriteLine("Received json request {0}", requestJson);
                Request request = JsonSerializer.Deserialize<Request>(requestJson, options);
                Console.WriteLine("Deserializaed Request {0} ", request);
                Response response = handleRequest(request);
                if (response != null)
                {
                    sendResponse(response);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("run eroare {0}", e.Message);
                if (e.InnerException != null) ;
                //log.ErrorFormat("run inner error {0}", e.InnerException.Message);
                Console.WriteLine(e.StackTrace);
            }

            try
            {
                Thread.Sleep(1000);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }
        try
        {
            stream.Close();
            connection.Close();
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    
    private static Response okResponse = JsonProtocolUtils.createOkResponse();

    private Response handleRequest(Request request)
    {
        Response response = null;
        switch (request.Type)
        {
            case RequestType.LOGIN:
                response = HandleLOGIN(request);
                break;
            case RequestType.LOGOUT:
                response = HandleLOGOUT(request);
                break;
            case RequestType.FIND_DONO:
                response = HandleFIND_DONO(request);
                break;
            case RequestType.SAVE_DONO:
                response = HandleSAVE_DONO(request);
                break;
            case RequestType.FIND_ALL_CAZ:
                response = HandleFIND_ALL_CAZ(request);
                break;
            case RequestType.LIST_DTO_CAZ:
                response = HandleLIST_DTO_CAZ(request);
                break;
            case RequestType.DONATIE_NOUA:
                response = HandleDONATIE_NOUA(request);
                break;
            case RequestType.DONATIE_SAVE:
                response = HandleDONATIE_SAVE(request);
                break;
            case RequestType.SEARCH_DONO_BYPNAME:
                response = HandleSEARCH_DONO_BYPNAME(request);
                break;
            case RequestType.GET_SUMA_DONATII_PT_CAZ:
                response = HandleGET_SUMA_DONATII_PT_CAZ(request);
                break;
            case RequestType.FIND_ALL_VOLUNTARI:
                response = HandleFIND_ALL_VOLUNTARI(request);
                break;
            default:
                Console.WriteLine("Unknown request type {0}", request.Type.ToString());
                break;
        }
        return response;
    }

    public void notifyObservers(Donatie donatie)
    {
        Console.WriteLine("notifyObservers");
        try
        {
            sendResponse(JsonProtocolUtils.createSaveDonatieResponse(donatie));
        }
        catch (IOException e)
        {
            Console.WriteLine(e.Message);
        }
    }

    private void sendResponse(Response response)
    {
        string json = JsonSerializer.Serialize(response);
        Console.WriteLine(json);
        lock (stream)
        {
            byte[] data = Encoding.UTF8.GetBytes(json + "\n");
            stream.Write(data, 0, data.Length);
            stream.Flush();
        }
    }

    private Response HandleLOGIN(Request request)
    {
        string username = request.nume;
        string password = request.password;
        try
        {
            Voluntar voluntar;
            lock (server)
                voluntar = server.login(username, password, this);
            return new Response { Type = ResponseType.OK, Voluntar = voluntar };
        }
        catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleLOGOUT(Request request)
    {
        string username = request.nume;
        try
        {
            lock (server)
                server.logout(username, this);
            connected = false;
            return okResponse;
        }
        catch (ServerException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleGET_SUMA_DONATII_PT_CAZ(Request request)
    {
        int cazId = request.idCaz;
        try
        {
            int suma;
            lock (server)
                suma = server.getSumaDonatiiPentruCaz(cazId);
            return JsonProtocolUtils.createGetSumaDonatiiPtCazResponse(suma);
        }
        catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleFIND_ALL_CAZ(Request request)
    {
        try
        {
            IEnumerable<Caz> cazuri;
            lock (server)
                cazuri = server.findAllCaz();
            return JsonProtocolUtils.createFindAllCazResponse(cazuri);
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleLIST_DTO_CAZ(Request request)
    {
        List<CazDTO> cazuri;
        try
        {
            lock (server)
                cazuri = server.createCazDTOList();
            return JsonProtocolUtils.createListDTOCazResponse(cazuri);
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleDONATIE_NOUA(Request request)
    {
        Donatie donatie = request.Donatie;
        try
        {
            lock (server)
                server.saveDonatie(donatie);
            return okResponse;
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleDONATIE_SAVE(Request request)
    {
        return null;
    }

    private Response HandleFIND_DONO(Request request)
    {
        string nume = request.fname;
        try
        {
            Donator donator;
            lock (server)
                donator = server.findByFullName(nume);
            return JsonProtocolUtils.createFindDonatorResponse(donator);
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleSEARCH_DONO_BYPNAME(Request request)
    {
        string nume = request.pname;
        try
        {
            IEnumerable<Donator> donatori;
            lock (server)
                donatori = server.searchByName(nume);
            return JsonProtocolUtils.createSearchDonoByPNameResponse(donatori);
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleSAVE_DONO(Request request)
    {
        Donator donator = request.Donator;
        try
        {
            Donator saved = server.saveDonator(donator);
            return JsonProtocolUtils.createSaveDonatorResponse(saved);
            //return okResponse;
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }

    private Response HandleFIND_ALL_VOLUNTARI(Request request)
    {
        try
        {
            IEnumerable<Voluntar> voluntari;
            lock (server)
                voluntari = server.findAllVoluntari();
            return JsonProtocolUtils.createFindAllVoluntariResponse(voluntari);
        }catch (ProjectException e)
        {
            return JsonProtocolUtils.createErrorResponse(e.Message);
        }
    }
    
}