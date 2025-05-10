package client;

import java.io.IOException;
import java.util.Properties;

public class StartObjectClient {
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";
    public static void main(String[] args){
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartObjectClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set");
            clientProps.list(System.out);
        }catch (IOException e){
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultPort;
        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        }catch (NumberFormatException e){
            System.err.println("Wrong port number " + e.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP: " + serverIP);
        System.out.println("Using server port: " + serverPort);

        System.out.println("Not implemented for object variant...");
    }
}
