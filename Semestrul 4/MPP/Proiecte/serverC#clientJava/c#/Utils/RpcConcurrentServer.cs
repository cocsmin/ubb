using servicesnet;

namespace networkingnet.Utils;

using System;
using System.Net.Sockets;
using System.Threading;
using networkingnet.RpcProtocol;


public class RpcConcurrentServer : AbsConcurrentServer
{
    private IProjectServices server;

    public RpcConcurrentServer(int port, IProjectServices transportServer) : base(port)
    {
        this.server = transportServer;
        Console.WriteLine("Transport - TransportRpcConcurrentServer");
    }

    protected override Thread CreateWorker(TcpClient client)
    {
        ClientJsonWorker worker = new ClientJsonWorker(server, client);
        Thread tw = new Thread(worker.Run);
        return tw;
    }

    public override void Stop()
    {
        Console.WriteLine("Stopping services ...");
        base.Stop();
    }
}