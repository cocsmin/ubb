package networking.rpcprotocol;

import model.*;
import services.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

public class ClientRpcReflectionWorker implements Runnable, IProjectObserver{
    private IProjectServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcReflectionWorker(IProjectServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Response handleRequest(Request request){
        Response response = null;
        String handlerName = "handle"+(request).type();
        System.out.println("HandlerName " + handlerName);
        try {
            Method method = this.getClass().getDeclaredMethod(handlerName, Request.class);
            response = (Response)method.invoke(this, request);
            System.out.println("Method " + handlerName + " invoked");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("Sending response " + response);
        synchronized (output) {
            output.writeObject(response);
            output.flush();
        }
    }

    public void run() {
        try {
            while (connected) {
                Object request = input.readObject();
                Response response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse(response);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Conexiune închisă" + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (connection != null) connection.close();
        } catch (IOException e) {
            System.out.println("Eroare la închidere resurse" + e.getMessage());
        }
    }

    @Override
    public void adauga(Donatie donatie) throws Exception {
        Response resp = new Response.Builder()
                .type(ResponseType.DONATIE_NOUA)
                .data(donatie)
                .build();
        System.out.println("Donatie adaugata: " + resp);
        try {
            sendResponse(resp);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void logoutNotification() {
//        try {
//            System.out.println("Utilizatorul a fost deconectat! Te rugăm să te loghezi din nou.");
//            this.frame.dispose();
//
//            LoginWindow loginWindow = new LoginWindow();
//            loginWindow.setVisible(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleLOGIN(Request request){
        System.out.println("Login request..." + request.type());
        Voluntar vDTO = (Voluntar) request.data();
        try {
            Voluntar voluntar = server.login(vDTO.getUsername(), vDTO.getPassword(), this);
            return new Response.Builder().type(ResponseType.OK).data(voluntar).build();
        }catch (Exception e){
            System.out.println("Login failed: " + e.getMessage());
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_ALL_CAZ(Request request){
        System.out.println("Find all caz request...");
        try {
            List<Caz> cazuri = server.findAllCaz();
            return new Response.Builder().type(ResponseType.FIND_ALL_CAZ).data(cazuri).build();
        }catch (Exception e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_SUMA_DONATII_PT_CAZ(Request request){
        System.out.println("Get SUMA Donati PT request...");
        Integer id = (Integer) request.data();
        try {
            Integer suma = server.getSumaDonatiiPentruCaz(id);
            return new Response.Builder().type(ResponseType.GET_SUMA_DONATII_PT_CAZ).data(suma).build();
        }catch (Exception e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    // În ClientRpcReflectionWorker
    // În ClientRpcReflectionWorker.java
    private Response handleLIST_DTO_CAZ(Request request) {
        System.out.println("List DTO Caz request...");
        try {
            List<CazDTO> dtoCazuri = server.createCazDTOList();
            // Logging pentru depanare
            System.out.println("Serverul a generat " + dtoCazuri.size() + "cazuri DTO");
            return new Response.Builder()
                    .type(ResponseType.LIST_DTO_CAZ)
                    .data(dtoCazuri)
                    .build();
        } catch (Exception e) {
            System.err.println("Eroare la handleLIST_DTO_CAZ: " + e.getMessage());
            return new Response.Builder()
                    .type(ResponseType.ERROR)
                    .data(e.getMessage())
                    .build();
        }
    }

    private Response handleSEARCH_DONO_BYPNAME(Request request){
        System.out.println("Search Donatori by PNAME request...");
        String partialName = (String) request.data();
        try {
            List<Donator> donatori = server.searchByName(partialName);
            return new Response.Builder().type(ResponseType.SEARCH_DONO_BYPNAME).data(donatori).build();
        }catch (Exception e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleSAVE_DONO(Request request){
        System.out.println("Save Donator request...");
        Donator donator = (Donator) request.data();
        try {
            Donator d1 = server.saveDonator(donator);
            return new Response.Builder().type(ResponseType.SAVE_DONO).data(d1).build();
        }catch (Exception e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleFIND_DONO(Request request){
        System.out.println("Find Donator request...");
        String fullName = (String) request.data();
        try {
            Donator donator = server.findByFullName(fullName);
            return new Response.Builder().type(ResponseType.FIND_DONO).data(donator).build();
        }catch (Exception e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    // În ClientRpcReflectionWorker.java
    private Response handleDONATIE_NOUA(Request request) {
        System.out.println("Donatie NOUA request...");
        try {
            Donatie donatie = (Donatie) request.data();
            Donatie savedDonatie = server.saveDonatie(donatie);
            // Logging pentru confirmare
            System.out.println("Donatie salvată cu ID: " + savedDonatie.getId());
            return new Response.Builder()
                    .type(ResponseType.DONATIE_NOUA)
                    .data(savedDonatie)
                    .build();
        } catch (Exception e) {
            System.err.println("Eroare la handleDONATIE_NOUA: " + e.getMessage());
            return new Response.Builder()
                    .type(ResponseType.ERROR)
                    .data(e.getMessage())
                    .build();
        }
    }

    private Response handleDONATIE_SAVE(Request request) {
        System.out.println("Donatie SAVE request...");
        try {
            Donatie donatie = (Donatie) request.data();
            Donatie savedDonatie = server.saveDonatie(donatie);
            System.out.println("Donatie salvată cu ID: " + savedDonatie.getId());
            // Trimitem un răspuns sincronic cu un tip diferit
            return new Response.Builder()
                    .type(ResponseType.DONATIE_SAVE)
                    .data(savedDonatie)
                    .build();
        } catch (Exception e) {
            System.err.println("Eroare la handleDONATIE_SAVE: " + e.getMessage());
            return new Response.Builder()
                    .type(ResponseType.ERROR)
                    .data(e.getMessage())
                    .build();
        }
    }

    private Response handleLOGOUT(Request request){
        System.out.println("Logout request...");
        String username = (String) request.data();
        try {
            server.logout(username);
            connected = false;
            return new Response.Builder().type(ResponseType.OK).build();
        } catch (Exception e){
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }



}
