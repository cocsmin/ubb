//
// Created by Cosmin Secrier on 28.03.2024.
//

#include "teste.h"
#include <assert.h>
#include "filmservice.h"
#include "validator.h"
#include "erori.h"
//functii de test pentru repository
void test_adauga(){
    FilmRepo repo;
    Film film;
    repo.adauga(Film("Star Wars","SF",1977, "Mark Hamill"));
    assert(repo.get_filme().size() == 1);
    try{
        repo.adauga(Film(" ","SF",2005,"x"));
        assert(false);
    }catch (const ExceptieRepo&){
        assert(true);
    }
    try{
        repo.adauga(Film("a"," ",2005,"x"));
        assert(false);
    }catch (const ExceptieRepo&){
        assert(true);
    }
    try{
        repo.adauga(Film("a","b",-5,"x"));
        assert(false);
    }catch (ExceptieRepo&){
        assert(true);
    }
    try{
        repo.adauga(Film("a","b",1250," "));
        assert(false);
    }catch (const ExceptieRepo&){
        assert(true);
    }
}

void test_cauta(){
    FilmRepo repo;
    repo.adauga(Film("Star Wars","SF",1977, "Mark Hamill"));
    auto film1 = repo.cauta("Star Wars");
    try{
        repo.cauta("prostie");
        assert(false);
    }catch (const ExceptieRepo&){
        assert(true);
    }
}

void test_stergere(){
    FilmRepo repo;
    repo.adauga(Film("Star Wars","SF",1977, "Mark Hamill"));
    Film sw = Film("Star Wars","SF",1977, "Mark Hamill");
    repo.sterge(sw);
    assert(repo.get_filme().empty());
    Film ex = Film("Star Wars","SF",1977, "Mark Hamill");
    try{
        repo.sterge(ex);
        assert(false);
    }catch (const ExceptieRepo&){
        assert(true);
    }
}

void test_exceptie(){
    FilmRepo repo;
    Film exemplu = Film("Star Wars","SF",-1, "Mark Hamill");
    try{
        repo.adauga(exemplu);
        assert(false);
    }catch (const ExceptieRepo& e){
        assert(e.get_mesaj() == "Anul aparitiei este invalid!");
        assert(true);
    }
    exemplu = Film(" ","SF",2005, "Mark Hamill");
    try{
        repo.adauga(exemplu);
        assert(false);
    }catch (const ExceptieRepo& e){
        assert(e.get_mesaj() == "Titlul este invalid!");
        assert(true);
    }
    exemplu = Film("Star Wars"," ",2005, "Mark Hamill");
    try{
        repo.adauga(exemplu);
        assert(false);
    }catch (const ExceptieRepo& e){
        assert(e.get_mesaj() == "Genul filmului este invalid!");
        assert(true);
    }
    exemplu = Film("Star Wars","SF",2005, " ");
    try{
        repo.adauga(exemplu);
        assert(false);
    }catch (const ExceptieRepo& e){
        assert(e.get_mesaj() == "Numele actorului este invalid!");
        assert(true);
    }
    Film exemplu_bun = Film("Star Wars","SF",2005, "Mark Hamill");
    repo.adauga(exemplu_bun);
    try{
        repo.adauga(exemplu_bun);
        assert(false);
    }catch (const ExceptieRepo& e){
        assert(e.get_mesaj() == "Filmul exista deja in lista!");
        assert(true);
    }
}

//functii de test pentru service

void test_adauga_sv(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
}

void test_sterge_sv(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
    service.sterge("Star Wars");
    assert(repo.get_filme().empty());
}

void test_cauta_sv(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
    assert(service.cauta("Star Wars") == true);
    assert(service.cauta("prostie") == false);
}

void test_modifica(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    service.modifica("Star Wars", "aventura", 2005, "Ewan McGregor");
    Film f = repo.cauta("Star Wars");
    assert(f.get_an_aparitie() == 2005);
}

void test_get_filme(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
    vector<Film>fs = service.get_filme();
    assert(fs.size() == 1);
}

void test_filtrare_titlu(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    vector<Film> filtr = service.filtrareTitlu("Star Wars");
    assert(filtr.size() == 1);
    assert(filtr[0].get_an_aparitie() == 1977);
    vector<Film> filtr2 = service.filtrareTitlu("contraexemplu");
    assert(filtr2.empty());
}

void test_filtrare_an(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "SF", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    vector<Film> filtr = service.filtrareAnAparitie(2014);
    assert(filtr.size() == 1);
    assert(filtr[0].get_gen() == "comedie");
    vector<Film> filtr2 = service.filtrareAnAparitie(2000);
    assert(filtr2.empty());
}

void test_sortare(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "sf", 1977, "Mark Hamill");
    assert(repo.get_filme().size() == 1);
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    vector<Film> sortat;
    sortat = service.sortare(1);
    assert(sortat[0].get_titlu() == "Grown Ups");
    assert(sortat[1].get_titlu() == "Star Wars");
    service.adauga("Fight Club", "thriller", 1999, "Brad Pitt");
    assert(repo.get_filme().size() == 3);
    assert(repo.get_filme()[2].get_an_aparitie() == 1999);
    sortat = service.sortare(2);
    assert(sortat[1].get_titlu() == "Fight Club");
    sortat = service.sortare(3);
    assert(sortat[0].get_an_aparitie() == 1977);
    service.adauga("Jumanji", "aventura", 1977, "Un actor bun");
    sortat = service.sortare(3);
    assert(sortat[0].get_titlu() == "Jumanji");

}

void test_validator(){
    Validator valid;
    try{
        valid.valideaza_film("","asd",2003,"dsada");
        assert(false);
    }catch (Exception& e) {
        assert(e.get_mesaj() == "Titlul este invalid! \n");
        assert(true);
    }
    try{
        valid.valideaza_film("12312£@!£!@$1","asd",2003,"dsada");
        assert(false);
    }catch (Exception& e){
        assert(e.get_mesaj() == "Titlul este invalid! \n");
        assert(true);
    }
    try{
        valid.valideaza_film("exemplu","21312$!@£!@", 2014, "dasdsa");
        assert(false);
    }catch (Exception& e){
        assert(e.get_mesaj() == "Genul filmului este invalid! \n");
        assert(true);
    }
    try{
        valid.valideaza_film("exemplu", "exemplu iara",-1,"hahaa");
        assert(false);
    }catch (Exception& e){
        assert(e.get_mesaj() == "Anul aparitiei este invalid! \n");
        assert(true);
    }
    try{
        valid.valideaza_film("ex", "exemplu", 42069, "231312$%!£!");
        assert(false);
    }catch (Exception& e){
        assert(e.get_mesaj() == "Numele actorului este invalid! \n");
        assert(true);
    }
}

void test_adauga_cos(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "sf", 1977, "Mark Hamill");
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    service.adauga_cos("Star Wars");
    assert(service.getAllCos().size() == 1);
    try{
        service.adauga_cos("Fifty Shades of Grey");
        assert(false);
    }
    catch (ExceptieRepo& e){
        assert(true);
    }
    assert(service.getAllCos().size() == 1);
}

void test_adauga_random_cos(){
    FilmRepo repo;
    CosFilme cos(repo);
    assert(cos.getAllCos().empty());
    vector<Film> filme = {
            Film("Star Wars", "sf", 1977, "Mark Hamill"),
            Film("Grown Ups", "comedie", 2014, "Adam Sandler"),
    };
    cos.adauga_random(filme, 1);
    assert(cos.getAllCos().size() == 1);

}

void test_adauga_random_cos2(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    repo.adauga(Film("Star Wars", "sf", 1977, "Mark Hamill"));
    repo.adauga(Film("Grown Ups", "comedie", 2014, "Adam Sandler"));
    int size_initial = service.getAllCos().size();
    service.adauga_random(2);
    assert(service.getAllCos().size() == size_initial + 2);
}

void test_sterge_cos(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "sf", 1977, "Mark Hamill");
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    service.adauga_cos("Star Wars");
    assert(service.getAllCos().size() == 1);
    service.sterge_cos();
    assert(service.getAllCos().empty());
}

void test_getAllCos(){
    FilmRepo repo;
    CosFilme cos(repo);
    assert(cos.getAllCos().empty());
    vector<Film> filme = {
            Film("Star Wars", "sf", 1977, "Mark Hamill"),
            Film("Grown Ups", "comedie", 2014, "Adam Sandler"),
    };
    cos.adauga_random(filme, 2);
    const vector<Film>& cosFilme = cos.getAllCos();
    assert(cosFilme.size() == 2);
    assert(find_if(cosFilme.begin(), cosFilme.end(), [](const Film& f){
        return f.get_titlu() == "Star Wars";
    }) != cosFilme.end());
    assert(find_if(cosFilme.begin(), cosFilme.end(), [](const Film& f) {
        return f.get_titlu() == "Grown Ups";
    }) != cosFilme.end());
}

void test_functii(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    FilmService::test_Titlu();
    FilmService::test_Actor();
    FilmService::test_Angen();
}

void test_raport(){
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
    service.adauga("Star Wars", "sf", 1977, "Mark Hamill");
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    service.adauga("Home Alone", "comedie", 1990, "Macaulay Culkin");
    auto map = service.frecvente_gen();
    assert(map[service.get_filme()[0].get_gen()].getCount() == 1);
    assert(map[service.get_filme()[1].get_gen()].getCount() == 2);
    assert(map[service.get_filme()[2].get_gen()].getCount() == 2);
    assert(map["actiune"].getCount() == 0);
}

void test_undo() {
    FilmRepo rep;
    Validator valid;
    CosFilme cos(rep);
    FilmService service{rep, valid, cos};
    try {
        service.undo();
        assert(false);
    } catch (Exception& ex) {
        assert(true);
    }
    service.adauga("Star Wars", "sf", 1977, "Mark Hamill");
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    service.modifica("Star Wars", "actiune", 2018, "Secrier Cosmin");
    service.sterge("Grown Ups");
    assert(service.get_filme().size() == 1);
    service.undo();
    assert(service.get_filme().size() == 2);
    auto film = rep.cauta("Star Wars");
    assert(film.get_actor() == "Secrier Cosmin");
    service.undo();
    film = rep.cauta("Star Wars");
    assert(film.get_actor() == "Mark Hamill");
    service.undo();
    service.undo();
    assert(service.get_filme().empty());
    try{
        service.undo();
        assert(false);
    }catch (Exception& ex) {
        assert(true);
    }
}

void test_export(){
    FilmRepo rep;
    Validator valid;
    CosFilme cos(rep);
    FilmService service{rep, valid, cos};
    service.adauga("Star Wars", "sf", 1977, "Mark Hamill");
    service.adauga("Grown Ups", "comedie", 2014, "Adam Sandler");
    service.adauga_cos("Star Wars");
    string test_csv = "/Users/cosmin/CLionProjects/oop_lab7/test.csv";
    string test_html = "/Users/cosmin/CLionProjects/oop_lab7/test.html";
    string test_altcv = "/Users/cosmin/CLionProjects/oop_lab7/test.txt";
    service.export_cos(test_csv);
    service.export_cos(test_html);
    try{
        service.export_cos(test_altcv);
        assert(false);
    }catch (Exception& ex) {
        assert(ex.get_mesaj() == "Fisierul nu e valid!!");
        assert(true);
    }
}

void test_fisier(){
    try{
        FilmRepoFile repo{ "/Users/cosmin/CLionProjects/oop_lab7/fisierfake.txt"};
        assert(false);
    }catch (ExceptieRepo ex){
        assert(ex.get_mesaj() == "Eroare la deschiderea fisierului!!");
        assert(true);
    }
    FilmRepoFile repo{ "/Users/cosmin/CLionProjects/oop_lab7/fisiertest.txt" };
    Validator valid;
    CosFilme cos(repo);
    FilmService service{ repo, valid, cos};
    //assert(service.get_filme().size() == 2);
    //service.adauga("Test", "testam", 69, "Mr Testulescu");
    //assert(service.get_filme().size() == 3);
    //service.sterge("Test");
}

void test_repolab(){
    FilmRepoLab repo;
    repo.set_probabilitate(0);
    try{
        repo.random();
        assert(true);
    }catch (ExceptieRepo& e){
        assert(false);
    }

    Film film("Star Wars", "sf", 1977, "Mark Hamill");
    repo.adauga(film);
    assert(repo.get_filme()[0].get_titlu() == "Star Wars");
    try{
        repo.adauga(film);
        assert(false);
    }catch (ExceptieRepo& ex){
        assert(ex.get_mesaj() == "Filmul exista deja in lista!");
    }

    auto f = repo.cauta("Star Wars");
    assert(f.get_an_aparitie() == 1977);
    try{
        repo.cauta("American Pie");
        assert(false);
    }catch (ExceptieRepo& e){
        assert(true);
    }

    Film test("asdsacxsa", "dsacsa", 1234, "cnasj");
    try{
        repo.sterge(test);
        assert(false);
    }catch (ExceptieRepo& e){
        assert(true);
    }

    repo.sterge(film);
    try{
        repo.sterge(film);
        assert(false);
    }catch (ExceptieRepo& e){
        assert(e.get_mesaj() == "Nu se poate sterge dintr-un vector gol!");
    }

    repo.set_probabilitate(100);
    try{
        repo.random();
        assert(false);
    }catch (ExceptieRepo& e){
        assert(true);
    }
}

/*
void test_vector(){
    vector<Film> filme;
    char a[3] = "a";
    for (int i = 1; i <= 10; i++){
        Film f(a, "sf", 2003, "Dwayne Johnson");
        filme.push_back(f);
        strcpy(a, a + 1);
    }
    Film ultim("sada", "dsadas", 2012, "dsadsacas");
    filme.push_back(ultim);
    filme.erase(5);
    assert(filme[0].get_titlu() == "a");
    filme.erase(11);
}
*/
void TestAll(){
    test_adauga();
    test_stergere();
    test_cauta();
    test_exceptie();
    test_adauga_sv();
    test_sterge_sv();
    test_cauta_sv();
    test_modifica();
    test_get_filme();
    test_filtrare_titlu();
    test_filtrare_an();
    test_sortare();
    test_validator();
    test_adauga_cos();
    test_adauga_random_cos();
    test_adauga_random_cos2();
    test_sterge_cos();
    test_getAllCos();
    test_functii();
    test_raport();
    test_undo();
    test_export();
    test_fisier();
    test_repolab();
    //test_vector();
    //test_sortare_actor();
    //test_sortare_angen();
}
