namespace networkingnet.Utils;

using System;
using System.IO;
using System.Net;
using System.Net.Sockets;

public abstract class AbstractServer
{
    private int port;
    private TcpListener server = null;

    public AbstractServer(int port)
    {
        this.port = port;
    }

    public void Start()
    {
        try
        {
            server = new TcpListener(IPAddress.Any, port);
            server.Start();
            while (true)
            {
                Console.WriteLine("Waiting for clients ...");
                TcpClient client = server.AcceptTcpClient();
                Console.WriteLine("Client connected ...");
                ProcessRequest(client);
            }
        }
        catch (IOException e)
        {
            throw new ServerException("Starting server error ", e);
        }
        finally
        {
            Stop();
        }
    }

    protected abstract void ProcessRequest(TcpClient client);

    public void Stop()
    {
        try
        {
            server.Stop();
        }
        catch (IOException e)
        {
            throw new ServerException("Closing server error ", e);
        }
    }
}