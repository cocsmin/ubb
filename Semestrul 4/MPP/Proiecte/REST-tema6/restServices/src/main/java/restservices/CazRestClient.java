package restservices;

import model.Caz;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class CazRestClient {
    public static void main(String[] args) {
        String BASE = "http://localhost:8080/api/cazuri";
        RestTemplate rt = new RestTemplate();

        Caz toCreate = new Caz();
        toCreate.setNume_caz("Ajutor urgente");
        toCreate.setDescriere("Colecta pentru victimele inundațiilor");

        ResponseEntity<Integer> createResp =
                rt.postForEntity(BASE, toCreate, Integer.class);

        if (createResp.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException("Create failed: " + createResp.getStatusCode());
        }
        Integer newId = createResp.getBody();
        System.out.println("Created Caz id=" + newId);

        Caz fetched = rt.getForObject(BASE + "/" + newId, Caz.class);
        System.out.println("Fetched: " + fetched);

        fetched.setDescriere("Actualizare descriere: foarte urgent");
        rt.put(BASE + "/" + newId, fetched);

        Caz updated = rt.getForObject(BASE + "/" + newId, Caz.class);
        System.out.println("Updated: " + updated);

        ResponseEntity<Caz[]> allResp =
                rt.getForEntity(BASE, Caz[].class);
        List<Caz> all = Arrays.asList(allResp.getBody());
        System.out.println("All cazuri:");
        all.forEach(System.out::println);

        rt.delete(BASE + "/" + newId);
        System.out.println("Deleted id=" + newId);
    }
}
