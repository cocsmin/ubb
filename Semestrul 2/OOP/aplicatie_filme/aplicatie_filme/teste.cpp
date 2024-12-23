#include "teste.h"
#include "validator.h"
#include <assert.h>
#include <sstream>
#include <iostream>

using std::ostream;
using std::cout;
using std::stringstream;

void test_adauga() 
{
    FilmRepo repo;
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Fast and Furious", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 2);
}

void test_cauta() 
{
    FilmRepo repo;
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    auto f = repo.cauta("Titanic");
    assert(f.get_titlu() == "Titanic");
    assert(f.get_gen() == "dragoste");
    assert(f.get_actor() == "Leonardo DiCaprio");
    assert(f.get_an() == 1997);
    try 
    {
        repo.cauta("Fast and Furious");
    }
    catch (ExceptieRepo& ex) 
    {
        stringstream os;
        os << ex;
        assert(os.str().find("exista") >= 0);
        assert(true);
    }
}

void test_sterge()
{
    FilmRepo repo;
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    Film f = Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997);
    repo.sterge(f);
    assert(repo.getAll().size() == 0);
}

void test_getAll() 
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo, valid };
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Fast and Furious", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 2);
    VectorDinamic <Film> lista = serv.getAll();
    assert(lista.size() == 2);
}

void test_adauga2() 
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo,valid };
    assert(repo.getAll().size() == 0);
    serv.adauga("Titanic", "dragoste", "Leonardo DiCaprio", 1997);
    assert(repo.getAll().size() == 1);
    serv.adauga("Fast and Furious", "actiune", "Vin Diesel", 2001);
    assert(repo.getAll().size() == 2);
}

void test_modifica() 
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo,valid };
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    serv.modifica("Titanic", "Fast and Furious", "actiune", "Vin Diesel", 2001);
    Film f = repo.cauta("Fast and Furious");
    assert(f.get_titlu() == "Fast and Furious");
    assert(f.get_actor() == "Vin Diesel");
}

void test_sterge2() 
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo,valid };
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    serv.sterge("Titanic");
    assert(repo.getAll().size() == 0);
}

void test_cauta2()
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo, valid };
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    bool rezultat = serv.cauta("Titanic");
    assert(rezultat == true);
    bool rezultat2 = serv.cauta("nimic");
    assert(rezultat2 == false);
    try 
    {
        repo.adauga(Film("a", "b", "c", -1));
    }
    catch (const Exceptie&) 
    {
        assert(true);
    }
}

void test_filtrare_titlu()
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo, valid };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 2);
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 3);
    VectorDinamic<Film> lista = serv.filtrare_titlu("Fast and Furious");
    assert(lista.size() == 1);
    assert(lista.get(0).get_an() == 2003);
    VectorDinamic <Film> lista2 = serv.filtrare_titlu("Titlu");
    assert(lista2.size() == 0);
}

void test_filtrare_an() 
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo, valid };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 2);
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 1997));
    assert(repo.getAll().size() == 3);
    VectorDinamic <Film> lista = serv.filtrare_an(1997);
    assert(lista.size() == 2);
    assert(lista.get(0).get_titlu() == "Titanic");
    assert(lista.get(1).get_titlu() == "Fast and Furious 2");
}

int sort_titlu2(const Film& f1, const Film& f2)
{
    return f1.get_titlu().compare(f2.get_titlu());
}

int sort_actor2(const Film& f1, const Film& f2)
{
    return f1.get_actor().compare(f2.get_actor());
}

int sort_gen_an2(const Film& f1, const Film& f2)
{
    if (f1.get_an() == f2.get_an())
    {
        return (f1.get_gen().compare(f2.get_gen()));
    }
    else {
        return f1.get_an() > f2.get_an();
    }
}

void test_sortare() 
{
    FilmRepo repo;
    Validator valid;
    FilmServ serv{ repo, valid };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 2);
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 1997));
    assert(repo.getAll().size() == 3);
    auto list = serv.sortare(sort_titlu2);
    auto list2 = serv.sortare(sort_actor2);
    auto list3 = serv.sortare(sort_gen_an2);
    assert(list.size() == 3);
    assert(list.get(0).get_titlu() == "Fast and Furious");
    assert(list.get(2).get_titlu() == "Titanic");
    assert(list2.size() == 3);
    assert(list2.get(0).get_actor() == "Leonardo DiCaprio");
    assert(list2.get(2).get_actor() == "Vin Diesel");
    assert(list3.size() == 3);
    assert(list3.get(0).get_an() == 1997);
    assert(list3.get(2).get_an() == 2003);
}

void test_domain()
{
    Film f("Titanic", "drama", "Leonardo DiCaprio", 1997);
    assert(f.get_titlu() == "Titanic");
    assert(f.get_gen() == "drama");
    assert(f.get_actor() == "Leonardo DiCaprio");
    assert(f.get_an() == 1997);
    Film f2;
    assert(f2.get_titlu() == "");
    assert(f2.get_gen() == "");
    assert(f2.get_actor() == "");
}

void test_repo()
{
    FilmRepo repo;
    Film f("Titanic", "drama", "Leonardo DiCaprio", 1997);
    repo.adauga(f);
    Film f2("Fast and Furious", "actiune", "Paul Walker", 2003);
    repo.adauga(f2);
    const VectorDinamic<Film>& lista = repo.getAll();
    assert(lista.size() == 2);
    const Film& gasit = repo.cauta("Titanic");
    assert(gasit.get_titlu() == "Titanic");
    try {
        repo.cauta("nimic");
    }
    catch (ExceptieRepo& ex)
    {
        stringstream os;
        os << ex;
        assert(os.str().find("exista") >= 0);
        assert(true);
    }
}

void test_validare()
{
    Validator validator;
    try 
    {
        validator.validare("a", "drama", "Leonardo DiCaprio", 1997);
        assert(false);
    }
    catch (Exceptie& e) 
    {
        assert(e.get_mesaj() == "Titlul nu poate fi vid");
    }
    try 
    {
        validator.validare("Titanic", "drama", "b", 1997);
        assert(false);
    }
    catch (Exceptie& e) 
    {
        assert(e.get_mesaj() == "Numele actorului nu poate fi vid");
    }
    try 
    {
        validator.validare("Titanic", "drama", "Leonardo DiCaprio", -1997);
        assert(false); 
    }
    catch (Exceptie& e)
    {
        assert(e.get_mesaj() == "Anul nu poate fi mai mic ca 0");
    }
    try 
    {
        validator.validare("Titanic", "c", "Leonardo DiCaprio", 1997);
        assert(false); 
    }
    catch (Exceptie& e) 
    {
        assert(e.get_mesaj() == "Genul filmului nu poate fi vid");
    }
}

void teste() 
{
    test_adauga();
    test_cauta();
    test_sterge();
    test_getAll();
    test_adauga2();
    test_sterge2();
    test_cauta2();
    test_modifica();
    test_filtrare_titlu();
    test_filtrare_an();
    test_repo();
    test_sortare();
    test_domain();
    test_validare();
}