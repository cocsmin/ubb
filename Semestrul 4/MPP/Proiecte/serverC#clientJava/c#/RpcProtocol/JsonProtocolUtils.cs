using modelnet;

namespace networkingnet.RpcProtocol;

public class JsonProtocolUtils
{
    public static Request createLoginRequest(string username, string password)
    {
        return new Request{ Type = RequestType.LOGIN, nume = username, password = password };
    }

    public static Request CreateLogoutRequest(string username)
    {
        return new Request{ Type = RequestType.LOGOUT, nume = username };
    }

    public static Request createGetSumaDonatiiPtCaz(int cazId)
    {
        return new Request{ Type = RequestType.GET_SUMA_DONATII_PT_CAZ, idCaz = cazId };
    }

    public static Request createFindAllCaz()
    {
        return new Request { Type = RequestType.FIND_ALL_CAZ };
    }

    public static Request createSearchDonoByPName(string pName)
    {
        return new Request { Type = RequestType.SEARCH_DONO_BYPNAME, pname = pName };
    }

    public static Request createSaveDonator(Donator donator)
    {
        return new Request{ Type = RequestType.SAVE_DONO, Donator = donator };
    }

    public static Request createFindDonator(string fName)
    {
        return new Request{ Type = RequestType.FIND_DONO, fname = fName };
    }

    public static Request createSaveDonatie(Donatie donatie)
    {
        return new Request{ Type = RequestType.DONATIE_NOUA, Donatie = donatie };
    }

    public static Request createListDTOCaz()
    {
        return new Request { Type = RequestType.LIST_DTO_CAZ };
    }
    
    public static Request createfindAllVoluntariRequest()
    {
        return new Request { Type = RequestType.FIND_ALL_VOLUNTARI };
    }

    public static Response createOkResponse()
    {
        return new Response{ Type = ResponseType.OK };
    }

    public static Response createErrorResponse(string message)
    {
        return new Response{ Type = ResponseType.ERROR, ErrorMessage = message };
    }

    public static Response createGetSumaDonatiiPtCazResponse(int suma)
    {
        return new Response{ Type = ResponseType.GET_SUMA_DONATII_PT_CAZ, SumaPentruCaz = suma };
    }

    public static Response createFindAllCazResponse(IEnumerable<Caz> cazuri)
    {
        return new Response{ Type = ResponseType.FIND_ALL_CAZ, Cazuri = cazuri };
    }

    public static Response createSearchDonoByPNameResponse(IEnumerable<Donator> donatori)
    {
        return new Response{ Type = ResponseType.SEARCH_DONO_BYPNAME, Donatori = donatori };
    }

    public static Response createSaveDonatorResponse(Donator donator)
    {
        return new Response{ Type = ResponseType.SAVE_DONO, Donator = donator };
    }

    public static Response createFindDonatorResponse(Donator donator)
    {
        return new Response{ Type = ResponseType.FIND_DONO, Donator = donator };
    }

    public static Response createSaveDonatieResponse(Donatie donatie)
    {
        return new Response{ Type = ResponseType.DONATIE_NOUA, Donatie = donatie };
    }

    public static Response createFindAllVoluntariResponse(IEnumerable<Voluntar> voluntari)
    {
        return new Response { Type = ResponseType.FIND_ALL_VOLUNTARI, Voluntari = voluntari};
    }

    public static Response createListDTOCazResponse(IEnumerable<CazDTO> cazuridto)
    {
        return new Response{ Type = ResponseType.LIST_DTO_CAZ, CazuriDTO = cazuridto.ToList() };
    }
}
