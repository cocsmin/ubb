package network.utils;

import network.jsonprotocol.ClientJsonWorker;
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
        ClientJsonWorker worker = new ClientJsonWorker(Server, client);

        Thread thread = new Thread(worker);
        return thread;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services...");
    }
}
