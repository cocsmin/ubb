namespace networkingnet.Utils;

using System.Net.Sockets;

public abstract class AbsConcurrentServer : AbstractServer
{
    public AbsConcurrentServer(int port) : base(port)
    {
        Console.WriteLine("Concurrent AbstractServer");
    }

    protected override void ProcessRequest(TcpClient client)
    {
        Thread tw = CreateWorker(client);
        tw.Start();
    }

    protected abstract Thread CreateWorker(TcpClient client);
    public virtual void Stop()
    {
    }
}