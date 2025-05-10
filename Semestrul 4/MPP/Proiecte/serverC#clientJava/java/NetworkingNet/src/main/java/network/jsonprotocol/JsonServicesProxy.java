package network.jsonprotocol;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import services.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class JsonServicesProxy implements IProjectServices {
    private final String host;
    private final int port;
    private IProjectObserver client;

    private Socket connection;
    private BufferedReader input;
    private BufferedWriter output;
    private final Queue<Response> responses = new LinkedBlockingQueue<>();
    private volatile boolean finished = false;
    private List<CazDTO> cachedCazuriDTO;
    private final Object lock = new Object();

    private static final Gson gson = new GsonBuilder()
            // serialize/deserialize enum names as strings:
            .registerTypeAdapter(RequestType.class,
                    (JsonSerializer<RequestType>)(src, t, c) -> new JsonPrimitive(src.name()))
            .registerTypeAdapter(ResponseType.class,
                    (JsonSerializer<ResponseType>)(src, t, c) -> new JsonPrimitive(src.name()))
            // map Java camelCase ↔ JSON lower_case_with_underscores
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            // support LocalDateTime as ISO string
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>)(src, typeOfSrc, context) ->
                            new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>)(json, type, context) ->
                            LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .create();

    public JsonServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void initializeConnection() throws IOException, ProjectException {
        connection = new Socket(host, port);
        input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        finished = false;
        startReader();
    }

    private void startReader() {
        Thread t = new Thread(() -> {
            while (!finished) {
                try {
                    String responseLine = input.readLine();
                    System.out.println("Raw JSON from server: " + responseLine);
                    if (responseLine == null || responseLine.isEmpty()) continue;

                    JsonObject fullResponse = JsonParser.parseString(responseLine).getAsJsonObject();
                    String responseType = fullResponse.get("type").getAsString();

                    if (responseType.equals("LIST_DTO_CAZ")) {
                        JsonArray cazuriArray = fullResponse.getAsJsonArray("cazuriDTO");
                        List<CazDTO> cazuriData = gson.fromJson(cazuriArray, new TypeToken<List<CazDTO>>(){}.getType());

                        // Actualizează cache-ul cu noile date
                        cachedCazuriDTO = cazuriData;

                        // Trimite un răspuns dummy pentru declanșarea actualizării UI
                        Response dummy = new Response();
                        dummy.setType(ResponseType.LIST_DTO_CAZ);
                        synchronized (responses) {
                            responses.add(dummy);
                        }
                    }else if (responseType.equals("FIND_ALL_CAZ")){
                        JsonArray cazuriArray = fullResponse.getAsJsonArray("cazuri");
                        List<Caz> cazuriData = gson.fromJson(cazuriArray, new TypeToken<List<Caz>>(){}.getType());

                        Response dummy = new Response();
                        dummy.setType(ResponseType.FIND_ALL_CAZ);
                        //cachedCazuriDTO =cazuri
                        dummy.setCazuri(cazuriData);
                        synchronized (responses) {
                            responses.add(dummy);
                        }
                    }else if (responseType.equals("DONATIE_NOUA")) {
                        JsonObject donatieJson = fullResponse.getAsJsonObject("donatie");

                        // Extrage câmpurile ca JsonPrimitive (nu JsonObject)
                        JsonObject donatorJson = donatieJson.getAsJsonObject("donator");
                        JsonObject cazJson = donatieJson.getAsJsonObject("caz");
                        JsonPrimitive dataJson = donatieJson.getAsJsonPrimitive("data_donatie"); // Schimbă la JsonPrimitive
                        JsonPrimitive sumaJson = donatieJson.getAsJsonPrimitive("suma_donata");   // Schimbă la JsonPrimitive

                        // Deserializează obiectele complexe
                        Donator donator = gson.fromJson(donatorJson, Donator.class);
                        Caz caz = gson.fromJson(cazJson, Caz.class);

                        // Deserializează valorile simple
                        LocalDateTime data = LocalDateTime.parse(dataJson.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        int suma = sumaJson.getAsInt();

                        // Creează obiectul Donatie
                        Donatie donatie = new Donatie(donator, caz, data, suma);

                        // Notifică clientul
                        if (client != null) {
                            client.adauga(donatie);
                        }
                        sendRequest(JsonProtocolUtils.createListDTOCazRequest());
                    }
                    else {
                        Response response = gson.fromJson(fullResponse, Response.class);
                        synchronized (responses) {
                            responses.add(response);
                        }
                    }
                }catch (IOException e){
                    System.err.println("Error reading response: " + e.getMessage());
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
    });
        t.start();
    }

    private void sendRequest(Request request) throws IOException {
        synchronized (lock) {
            String reqJson = gson.toJson(request);
            System.out.println("Am trimis: " + gson.toJson(request));
            output.write(reqJson);
            output.newLine();
            output.flush();
        }
    }

    private Response readResponse() throws InterruptedException {
        while (responses.isEmpty()) {
            Thread.sleep(50);
        }
        synchronized (responses) {
            return responses.poll();
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException ignored) {
            System.err.println("Error closing connection: " + ignored.getMessage());
        }
        client = null;
    }

    @Override
    public Voluntar login(String username, String password, IProjectObserver client) throws Exception {
        initializeConnection();
        this.client = client;
        sendRequest(JsonProtocolUtils.createLoginRequest(username, password));
        Response resp = readResponse();
        System.out.println("Login response type: " + resp);
        System.out.println("Voluntarul logat: " + resp.getVoluntar()
        );
        if (resp.getType() == ResponseType.OK && resp.getVoluntar() != null) {
            return resp.getVoluntar();
        } else {
            closeConnection();
            throw new Exception(resp.getErrorMessage());
        }
    }

    @Override
    public Donatie saveDonatie(Donatie donatie) throws Exception {
        sendRequest(JsonProtocolUtils.createSaveDonatie(donatie));
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        return resp.getDonatie();
    }


    @Override
    public List<Caz> findAllCaz() throws Exception {
        sendRequest(JsonProtocolUtils.createFindAllCazRequest());
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        List<Caz> list = resp.getCazuri();
        return (list != null) ? list : Collections.emptyList();
    }

    @Override
    public List<CazDTO> createCazDTOList() throws Exception {
        sendRequest(JsonProtocolUtils.createListDTOCazRequest());
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        return cachedCazuriDTO != null ? cachedCazuriDTO : List.of();
    }

    @Override
    public List<Donator> searchByName(String partialName) throws Exception {
        sendRequest(JsonProtocolUtils.createSearchDonoByPName(partialName));
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        return (List<Donator>)resp.getDonatori();
    }

    @Override
    public Donator saveDonator(Donator donator) throws Exception {
        sendRequest(JsonProtocolUtils.createSaveDonator(donator));
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        return resp.getDonator();
    }

    @Override
    public Donator findByFullName(String numeDonator) throws Exception {
        sendRequest(JsonProtocolUtils.createFindDonator(numeDonator));
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        return resp.getDonator();
    }

    @Override
    public int getSumaDonatiiPentruCaz(int cazId) throws Exception {
        sendRequest(JsonProtocolUtils.createGetSumaDonatiiPtCaz(cazId));
        Response resp = readResponse();
        if (resp.getType() == ResponseType.ERROR) {
            throw new Exception(resp.getErrorMessage());
        }
        return resp.getSumaPentruCaz();
    }

    @Override
    public void logout(String username, IProjectObserver client) throws Exception {
        sendRequest(JsonProtocolUtils.createLogoutRequest(username));
//        readResponse(); // ignore content
//        closeConnection();
    }

//    @Override
//    public List<Voluntar> findAllVoluntari() throws Exception {
//        sendRequest(JsonProtocolUtils.createFindAllVoluntariRequest());
//        Response resp = readResponse();
//        if (resp.getType() == ResponseType.ERROR) {
//            throw new ProjectException(resp.getErrorMessage());
//        }
//        return resp.getVoluntari();
//    }
}
