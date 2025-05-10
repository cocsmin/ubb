package network.jsonprotocol;
import com.google.gson.*;
import model.*;
import services.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientJsonWorker implements Runnable, IProjectObserver {
    private final IProjectServices server;
    private final Socket connection;
    private BufferedReader reader;
    private BufferedWriter writer;
    private volatile boolean connected = false;

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)).create();

    public ClientJsonWorker(IProjectServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
            connected = true;
        } catch (IOException e) {
            throw new RuntimeException("Error initializing worker streams", e);
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                String line = reader.readLine();
                if (line == null || line.isEmpty()) continue;
                Request req = gson.fromJson(line, Request.class);
                Response resp = handleRequest(req);
                if (resp != null) sendResponse(resp);

            } catch (IOException ignored) {
                System.err.println("IOException: " + ignored.getMessage());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
                System.err.println("InterruptedException: " + ignored.getMessage());
            }
        }
        try {
            reader.close();
            writer.close();
            connection.close();
        }catch (IOException ignored) {
            System.err.println("Error closing resources: " + ignored.getMessage());
        }
    }

    private static final Response okResponse = JsonProtocolUtils.createOkResponse();

    private void sendResponse(Response response) throws IOException {
        String json = gson.toJson(response);
        synchronized (writer) {
            writer.write(json);
            writer.newLine();
            writer.flush();
        }
    }

    private Response handleRequest(Request request) {
        if (request == null || request.getType() == null)
            return JsonProtocolUtils.createErrorResponse("Invalid request");

        switch (request.getType()) {
            case LOGIN:   return handleLogin(request);
            case LOGOUT:  return handleLogout(request);
            //case FIND_ALL_VOLUNTARI: return handleFindAllVoluntari();
            case GET_SUMA_DONATII_PT_CAZ: return handleGetSuma(request);
            case FIND_ALL_CAZ: return handleFindAllCaz();
            case LIST_DTO_CAZ: return handleListDtoCaz();
            case SEARCH_DONO_BYPNAME: return handleSearchDonatori(request);
            case FIND_DONO: return handleFindDonator(request);
            case SAVE_DONO: return handleSaveDonator(request);
            case DONATIE_NOUA: return handleNewDonatie(request);
            default: return JsonProtocolUtils.createErrorResponse("Unknown request type");
        }
    }

    private Response handleLogin(Request req) {
        try {
            Voluntar voluntar = server.login(req.getNume(), req.getPassword(), this);
            return JsonProtocolUtils.createOkResponse(voluntar);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse("Login failed: " + e.getMessage());
        }
    }

    private Response handleLogout(Request req) {
        try {
            server.logout(req.getVoluntar().getUsername(), this);
            connected = false;
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse("Logout failed: " + e.getMessage());
        }
    }

//    private Response handleFindAllVoluntari() {
//        try {
//            List<Voluntar> list = server.findAllVoluntari();
//            return JsonProtocolUtils.createFindAllVoluntariResponse(list);
//        } catch (Exception e) {
//            return JsonProtocolUtils.createErrorResponse(e.getMessage());
//        }
//    }

    private Response handleGetSuma(Request req) {
        try {
            int sum = server.getSumaDonatiiPentruCaz(req.getIdCaz());
            return JsonProtocolUtils.createGetSumaDonatiiPtCazResponse(sum);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleFindAllCaz() {
        try {
            List<Caz> list = server.findAllCaz();
            return JsonProtocolUtils.createFindAllCazResponse(list);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleListDtoCaz() {
        try {
            List<CazDTO> dto = server.createCazDTOList();
            return JsonProtocolUtils.createListDTOCazResponse(dto);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleSearchDonatori(Request req) {
        try {
            List<Donator> list = server.searchByName(req.getPname());
            return JsonProtocolUtils.createSearchDonoByPNameResponse(list);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleFindDonator(Request req) {
        try {
            Donator d = server.findByFullName(req.getFname());
            return JsonProtocolUtils.createFindDonatorResponse(d);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleSaveDonator(Request req) {
        try {
            Donator d = server.saveDonator(req.getDonator());
            return JsonProtocolUtils.createSaveDonatorResponse(d);
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    private Response handleNewDonatie(Request req) {
        try {
            server.saveDonatie(req.getDonatie());
            //sendRequest(JsonProtocolUtils.createListDTOCazRequest());
            return okResponse;
        } catch (Exception e) {
            return JsonProtocolUtils.createErrorResponse(e.getMessage());
        }
    }

    @Override
    public void adauga(Donatie donatie) throws Exception {
        Response response = JsonProtocolUtils.createSaveDonatieResponse(donatie);
        sendResponse(response);
    }

}
