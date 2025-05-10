package network.jsonprotocol;

import model.*;

import java.util.List;

public class JsonProtocolUtils {

    public static Request createLoginRequest(String username, String password) {
        Request r = new Request();
        r.setType(RequestType.LOGIN);
        r.setNume(username);
        r.setPassword(password);
        return r;
    }

    public static Request createLogoutRequest(String username) {
        Request r = new Request();
        r.setType(RequestType.LOGOUT);
        r.setNume(username);
        return r;
    }

    public static Request createGetSumaDonatiiPtCaz(int cazId) {
        Request r = new Request();
        r.setType(RequestType.GET_SUMA_DONATII_PT_CAZ);
        r.setIdCaz(cazId);
        return r;
    }

    public static Request createFindAllCazRequest() {
        Request r = new Request();
        r.setType(RequestType.FIND_ALL_CAZ);
        return r;
    }

    public static Request createSearchDonoByPName(String pName) {
        Request r = new Request();
        r.setType(RequestType.SEARCH_DONO_BYPNAME);
        r.setPname(pName);
        return r;
    }

    public static Request createSaveDonator(Donator donator) {
        Request r = new Request();
        r.setType(RequestType.SAVE_DONO);
        r.setDonator(donator);
        return r;
    }

    public static Request createFindDonator(String fName) {
        Request r = new Request();
        r.setType(RequestType.FIND_DONO);
        r.setFname(fName);
        return r;
    }

    public static Request createSaveDonatie(Donatie donatie) {
        Request r = new Request();
        r.setType(RequestType.DONATIE_NOUA);
        r.setDonatie(donatie);
        return r;
    }

    public static Request createListDTOCazRequest() {
        Request r = new Request();
        r.setType(RequestType.LIST_DTO_CAZ);
        return r;
    }

    /*
    public static Request createFindAllVoluntariRequest() {
        Request r = new Request();
        r.setType(RequestType.FIND_ALL_VOLUNTARI);
        return r;
    }

     */

    public static Response createOkResponse() {
        Response r = new Response();
        r.setType(ResponseType.OK);
        return r;
    }

    public static Response createOkResponse(Voluntar v) {
        Response r = new Response();
        r.setType(ResponseType.OK);
        r.setVoluntar(v);
        return r;
    }

    public static Response createErrorResponse(String message) {
        Response r = new Response();
        r.setType(ResponseType.ERROR);
        r.setErrorMessage(message);
        return r;
    }

    public static Response createGetSumaDonatiiPtCazResponse(int suma) {
        Response r = new Response();
        r.setType(ResponseType.GET_SUMA_DONATII_PT_CAZ);
        r.setSumaPentruCaz(suma);
        return r;
    }

    public static Response createFindAllCazResponse(List<Caz> cazuri) {
        Response r = new Response();
        r.setType(ResponseType.FIND_ALL_CAZ);
        r.setCazuri(cazuri);
        return r;
    }

    public static Response createSearchDonoByPNameResponse(List<Donator> donatori) {
        Response r = new Response();
        r.setType(ResponseType.SEARCH_DONO_BYPNAME);
        r.setDonatori(donatori);
        return r;
    }

    public static Response createSaveDonatorResponse(Donator donator) {
        Response r = new Response();
        r.setType(ResponseType.SAVE_DONO);
        r.setDonator(donator);
        return r;
    }

    public static Response createFindDonatorResponse(Donator donator) {
        Response r = new Response();
        r.setType(ResponseType.FIND_DONO);
        r.setDonator(donator);
        return r;
    }

    public static Response createSaveDonatieResponse(Donatie donatie) {
        Response r = new Response();
        r.setType(ResponseType.DONATIE_NOUA);
        r.setDonatie(donatie);
        return r;
    }

    /*
    public static Response createFindAllVoluntariResponse(List<Voluntar> voluntari) {
        Response r = new Response();
        r.setType(ResponseType.FIND_ALL_VOLUNTARI);
        r.setVoluntari(voluntari);
        return r;
    }

     */

    public static Response createListDTOCazResponse(List<CazDTO> cazuriDTO) {
        Response r = new Response();
        r.setType(ResponseType.LIST_DTO_CAZ);
        // if it's not already a List
        r.setCazuriDTO((List<CazDTO>) cazuriDTO);
        return r;
    }
}
