package server;

import network.utils.AbstractServer;
import network.utils.RpcConcurrentServer;
import persistance.*;
import persistance.Repo.*;
import services.IProjectServices;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort = 55555;
    public static void main(String[] args){
        Properties serverProps = new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        }catch (IOException e) {
            System.err.println("Cannot load server properties" + e);
            return;
        }
        VoluntarRepo0 voluntarRepo0 = new VoluntarDBRepo(serverProps);
        DonatorRepo0 donatorRepo0 = new DonatorDBRepo(serverProps);
        CazRepo0 cazRepo0 = new CazDBRepo(serverProps);
        DonatieRepo0 donatieRepo0 = new DonatieDBRepo(serverProps);
        IProjectServices ServerImpl = new ServicesImpl(voluntarRepo0, donatorRepo0, donatieRepo0, cazRepo0);
        int ServerPort = defaultPort;
        try {
            ServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
        } catch (NumberFormatException nef) {
            System.err.println("Wrong port number " + nef.getMessage());
            System.err.println("Using default " + defaultPort);
        }
        System.out.println("Server started on port " + ServerPort);
        AbstractServer server = new RpcConcurrentServer(ServerPort, ServerImpl);
        try {
            server.start();
        }catch (Exception e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch (Exception e) {
                System.err.println("Error stopping the server" + e.getMessage());
            }
        }
    }
}
