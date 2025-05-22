package restservices;

import model.Caz;
import org.springframework.http.HttpStatus;
import persistance.Repo.CazORMRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cazuri")
@CrossOrigin(origins = "http://localhost:3000")
public class CazController {
    private final CazORMRepo cazRepo;

    public CazController(CazORMRepo cazRepo){
        this.cazRepo = cazRepo;
    }

    @PostMapping
    public ResponseEntity<Integer> createCaz(@RequestBody Caz caz){
        Caz saved = cazRepo.save(caz);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Caz> getCazById(@PathVariable int id){
        Caz result = cazRepo.findOne(id);
        if (result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<Caz> getAll(){
        return (List<Caz>)cazRepo.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Caz> updateCaz(@PathVariable int id, @RequestBody Caz caz){
        Caz existing = cazRepo.findOne(id);
        if (existing == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        // aplică modificările
        existing.setNume_caz(caz.getNume_caz());
        existing.setDescriere(caz.getDescriere());

        Caz saved = cazRepo.update(existing);
        return ResponseEntity.ok(saved);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCaz(@PathVariable int id) {
        Caz existing = cazRepo.findOne(id);
        if (existing == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        cazRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}