package map.socialnetwork.repository;

import map.socialnetwork.domain.Entity;
import map.socialnetwork.validator.Validator;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class AbstractFileRepo<ID, E extends Entity<ID>> extends InMemoryRepo<ID, E> {

    String nume_fisier;

    public AbstractFileRepo(String nume_fisier, Validator<E> validator) {
        super(validator);
        this.nume_fisier = nume_fisier;
        loadData();
    }

    public abstract E extractEntity(List<String> atribute);

    private void loadData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nume_fisier))){
            String linie;
            while ((linie = reader.readLine()) != null){
                System.out.println(linie);
                List<String> data = Arrays.asList(linie.split(";"));
                E entity = extractEntity(data);
                super.save(entity);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    protected abstract String createEntityAsString(E entity);

    @Override
    public Optional<E> save (E entity) {
        Optional<E> rezultat = super.save(entity);
        if (rezultat.isEmpty())
            writeToFile(entity);
        return rezultat;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nume_fisier, true))){
            writer.write(createEntityAsString(entity));
            writer.newLine();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
