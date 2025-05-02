using System.Text.Json.Serialization;
using servicesnet;

namespace networkingnet.RpcProtocol;

using System.Net.Sockets;
using System.Text;
using System.Text.Json;
using modelnet;
using Microsoft.VisualBasic.CompilerServices;

public class ServicesRpcProxy : IProjectServices
{
    private string host;
    private int port;

    private IProjectObserver client;
    private NetworkStream stream;
    private TcpClient connection;
    private Queue<Response> responses;
    private volatile bool finished;
    private EventWaitHandle waitHandle;

    // ServicesRpcProxy.cs
    private static readonly JsonSerializerOptions jsonOptions = new JsonSerializerOptions
    {
        PropertyNameCaseInsensitive = true, // Ignoră diferențele de casing
        IncludeFields = true, // Include câmpuri dacă sunt folosite
        WriteIndented = true,
        // Adaugă suport pentru constructori parametrizați
        DefaultIgnoreCondition = JsonIgnoreCondition.Never
    };
    public ServicesRpcProxy(string host, int port)
    {
        this.host = host;
        this.port = port;
        responses = new Queue<Response>();
        try
        {
            initializeConnection(); // Inițializează conexiunea la server
        }
        catch (ProjectException e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }

    private void sendRequest(Request request)
    {
        try
        {
            lock (stream)
            {
                string json = JsonSerializer.Serialize(request);
                //log.DebugFormat("Sending json request {0}", json);
                byte[] bytes = Encoding.UTF8.GetBytes(json + "\n");
                stream.Write(bytes, 0, bytes.Length);
                stream.Flush();
            }
        }
        catch (Exception e)
        {
            //log.ErrorFormat("Error sending request {0}", e.Message);
            if (e.InnerException != null)
                // log.ErrorFormat("Error sending request inner error {0}", e.InnerException.Message);
                throw new ProjectException("Error sending request " + e);
        }
    }

    private Response readResponse()
    {
        Response response = null;
        try
        {
            waitHandle.WaitOne();
            lock (responses)
            {
                response = responses.Dequeue();
            }
        }
        catch (Exception e)
        {
            if (e.InnerException != null) ;
        }

        return response;
    }

    private void initializeConnection()
    {
        try
        {
            connection = new TcpClient(host, port);
            stream = connection.GetStream();
            finished = false;
            waitHandle = new AutoResetEvent(false);
            startReader();
        }
        catch (Exception e)
        {
            //log.ErrorFormat("Error initializing connection {0}", e.Message);
            if (e.InnerException != null) ;
            //log.ErrorFormat("Error initializing connection inner error {0}", e.InnerException.Message);
            Console.WriteLine("Error initializing connection " + e.Message);
        }
    }

    private void closeConnection()
    {
        finished = true;
        try
        {
            stream.Close();
            connection.Close();
            waitHandle.Close();
            client = null;
        }
        catch (Exception e)
        {
            //log.ErrorFormat("Error closing connection {0}", e.Message);
            if (e.InnerException != null) ;
            //log.ErrorFormat("Error closing connection inner error {0}", e.InnerException.Message);
        }
    }

    private void startReader()
    {
        Thread thread = new Thread(run);
        thread.Start();
    }

    private void handleUpdate(Response response)
    {
        //log.DebugFormat("Received json response {0}", JsonSerializer.Serialize(response));
        if (response.Type == ResponseType.DONATIE_NOUA)
        {
            try
            {
                //log.Debug($"Attempting to notify client with ticket: {response.Bilet != null}");
                if (client == null)
                {
                    Console.WriteLine("Client is null when trying to notify about ticket");
                    return;
                }

                // Use a try-catch here as the client might be disconnected
                try
                {
                    client.notifyObservers(response.Donatie);
                }
                catch (Exception ex)
                {
                    Console.WriteLine("Failed to notify client: {ex.Message}");
                }
            }
            catch (Exception e)
            {
                // log.ErrorFormat("Error handling update {0}", e.Message);
                if (e.InnerException != null) ;
                // log.ErrorFormat("Error handling update inner error {0}", e.InnerException.Message);
            }
        }
    }

    private bool isUpdate(Response response)
    {
        return response.Type == ResponseType.DONATIE_NOUA;
    }

    private void run()
    {
        using StreamReader streamReader = new StreamReader(stream, Encoding.UTF8);
        while (!finished)
        {
            try
            {
                string json = streamReader.ReadLine();
                if (string.IsNullOrEmpty(json))
                    continue;
                //log.DebugFormat("Received json response {0}", json);
                Response response = JsonSerializer.Deserialize<Response>(json);
                //log.DebugFormat("Deserialized response {0}", JsonSerializer.Serialize(response));
                if (isUpdate(response))
                {
                    handleUpdate(response);
                }
                else
                {
                    lock (responses)
                    {
                        responses.Enqueue(response);
                    }

                    waitHandle.Set();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error reading response {0}", e.Message);
                if (e.InnerException != null) ;
                //log.ErrorFormat("Error reading response inner error {0}", e.InnerException.Message);
            }
        }
    }

    public Voluntar login(string username, string password, IProjectObserver client)
    {
        Console.WriteLine("Login...");
        sendRequest(JsonProtocolUtils.createLoginRequest(username, password));
        Response response = readResponse();
        if (response.Type == ResponseType.OK && response.Voluntar != null)
        {
            this.client = client;
            Console.WriteLine("Login successful");
            return response.Voluntar;
        }
        string err = response.ErrorMessage ?? "Unknown login error";
        closeConnection();
        throw new ProjectException(err);
        
    }
    

    public Donatie saveDonatie(Donatie donatie)
    {
        sendRequest(JsonProtocolUtils.createSaveDonatie(donatie));
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }

        return response.Donatie;

    }

    public List<Caz> findAllCaz()
    {
        sendRequest(JsonProtocolUtils.createFindAllCaz());
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }

        List<Caz> cazuri = response.Cazuri.ToList();
        return cazuri;
    }

    public List<CazDTO> createCazDTOList()
    {
        sendRequest(JsonProtocolUtils.createListDTOCaz());
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }
        List<CazDTO> cazuriDTO = response.CazuriDTO.ToList();
        return cazuriDTO;
    }

    public List<Donator> searchByName(string partialName)
    {
        sendRequest(JsonProtocolUtils.createSearchDonoByPName(partialName));
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }
        List<Donator> donatori = response.Donatori.ToList();
        return donatori;
    }
    

    public Donator saveDonator(Donator donator)
    {
        sendRequest(JsonProtocolUtils.createSaveDonator(donator));
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }
        return response.Donator;
    }

    public Donator findByFullName(string numeDonator)
    {
        sendRequest(JsonProtocolUtils.createFindDonator(numeDonator));
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }
        return response.Donator;
    }

    public int getSumaDonatiiPentruCaz(int cazId)
    {
        sendRequest(JsonProtocolUtils.createGetSumaDonatiiPtCaz(cazId));
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }

        return response.SumaPentruCaz;
    }

    public void logout(string username, IProjectObserver client)
    {
        sendRequest(JsonProtocolUtils.CreateLogoutRequest(username));
        Response response = readResponse();
        closeConnection();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }
    }

    public IEnumerable<Voluntar> findAllVoluntari()
    {
        sendRequest(JsonProtocolUtils.createfindAllVoluntariRequest());
        Response response = readResponse();
        if (response.Type == ResponseType.ERROR)
        {
            throw new ProjectException("Error sending request");
        }

        return response.Voluntari;
    }
}