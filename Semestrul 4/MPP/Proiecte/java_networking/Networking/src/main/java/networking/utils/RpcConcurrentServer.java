package networking.utils;

import networking.rpcprotocol.ClientRpcReflectionWorker;
import services.IProjectServices;

import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer{
    private IProjectServices Server;
    public RpcConcurrentServer(int port, IProjectServices Server1) {
        super(port);
        this.Server = Server1;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client){
        ClientRpcReflectionWorker worker = new ClientRpcReflectionWorker(Server, client);

        Thread thread = new Thread(worker);
        return thread;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services...");
    }
}
