package networking.rpcprotocol;

import model.*;
import services.IProjectObserver;
import services.IProjectServices;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ServicesRpcProxy implements IProjectServices {
    private String host;
    private int port;

    private IProjectObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxy(String host, int port){
        this.host = host;
        this.port = port;
        qresponses = new LinkedBlockingQueue<Response>();
    }

    private void initializeConnection() throws Exception {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e){
            throw new Exception("Error connecting to the server: " + e.getMessage());
        }
    }

    private void closeConnection(){
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws Exception{
        if (output == null) initializeConnection();

        try {
            output.writeObject(request);
            output.flush();
        }catch (IOException e){
            throw new Exception("Error sending object: " + e.getMessage());
        }
    }

    private Response readResponse() throws Exception{
        try {
            return qresponses.poll(30, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            throw new Exception("Error reading response: " + e.getMessage());
        }
    }

    private void startReader() throws Exception{
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response) {
        // Tratază ca update doar dacă tipul este DONATIE_NOUA

        return response.type() == ResponseType.DONATIE_NOUA;
    }

    private void handleUpdate(Response response){
        if (isUpdate(response)){
            Donatie dono = (Donatie) response.data();
            try {
                client.adauga(dono);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while (!finished){
                try {
                    Object response = input.readObject();
                    if (isUpdate((Response) response)){
                        handleUpdate((Response) response);
                    }
                    else {
                        qresponses.put((Response) response);
                    }
                }catch (SocketException | EOFException e){
                    System.err.println("Socket closed, stopping reader");
                    break;
                }catch (IOException | ClassNotFoundException | InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Voluntar login(String username, String password, IProjectObserver client) throws Exception {
        initializeConnection();
        this.client = client;
        Voluntar voluntar = new Voluntar(username, password);
        Request req = new Request.Builder().type(RequestType.LOGIN).data(voluntar).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.OK){
            return (Voluntar) response.data();
        }
        throw new Exception(response.data().toString());
    }

    // În ServicesRpcProxy.java
    @Override
    public Donatie saveDonatie(Donatie donatie) throws Exception {
        System.out.println("Trimitere cerere DONATIE_NOUA: " + donatie);
        Request req = new Request.Builder().type(RequestType.DONATIE_SAVE).data(donatie).build();
        sendRequest(req);
        Response response = readResponse();

        if (response == null) {
            throw new Exception("Nu s-a primit răspuns de la server.");
        }

        System.out.println("Răspuns primit: " + response);
        if (response.type() == ResponseType.ERROR) {
            throw new Exception(response.data().toString());
        }
        return (Donatie) response.data();
    }

    @Override
    public int getSumaDonatiiPentruCaz(int cazId) throws Exception {
        Request req = new Request.Builder().type(RequestType.GET_SUMA_DONATII_PT_CAZ).data(cazId).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR){
            throw new Exception(response.data().toString());
        }

        return (Integer) response.data();
    }

    @Override
    public List<Caz> findAllCaz() throws Exception {
        Request req = new Request.Builder().type(RequestType.FIND_ALL_CAZ).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR)
            throw new Exception(response.data().toString());

        return (List<Caz>) response.data();
    }

    // În ServicesRpcProxy
    @Override
    public List<CazDTO> createCazDTOList() throws Exception {
        Request req = new Request.Builder().type(RequestType.LIST_DTO_CAZ).build();
        sendRequest(req);
        Response response = readResponse();
        if (response == null) {
            throw new Exception("Nu s-a primit răspuns de la server.");
        }
        if (response.type() != ResponseType.LIST_DTO_CAZ) { // Verifică tipul răspunsului
            throw new Exception("Răspuns neașteptat de la server: " + response.type());
        }
        return (List<CazDTO>) response.data();
    }

    @Override
    public List<Donator> searchByName(String name) throws Exception {
        Request req = new Request.Builder().type(RequestType.SEARCH_DONO_BYPNAME).data(name).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR)
            throw new Exception(response.data().toString());

        return (List<Donator>) response.data();
    }

    @Override
    public Donator saveDonator(Donator donator) throws Exception {
        Request req = new Request.Builder().type(RequestType.SAVE_DONO).data(donator).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR)
            throw new Exception(response.data().toString());

        return (Donator) response.data();
    }

    @Override
    public Donator findByFullName(String fullName) throws Exception {
        Request req = new Request.Builder().type(RequestType.FIND_DONO).data(fullName).build();
        sendRequest(req);
        Response response = readResponse();
        if (response.type() == ResponseType.ERROR)
            throw new Exception(response.data().toString());

        return (Donator) response.data();
    }

    @Override
    public void logout(String username) throws Exception {
        Request req = new Request.Builder()
                .type(RequestType.LOGOUT)
                .data(username)
                .build();
        sendRequest(req);
        Response response = readResponse(); // dacă nu faci pe thread separat, aici se poate bloca!

        if (response.type() == ResponseType.ERROR) {
            throw new Exception("Logout failed: " + response.data());
        }
    }

}
