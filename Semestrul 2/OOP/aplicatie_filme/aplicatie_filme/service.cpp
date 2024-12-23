#include "service.h"
#include <vector>
void FilmServ::adauga(const string& titlu, const string& gen, const string& actor, int an)
{
    valid.validare(titlu, gen, actor, an);
    Film f{ titlu, gen, actor, an };
    repo.adauga(f);
}

void FilmServ::modifica(const std::string& titlu_vechi, const std::string& titlu, const std::string& gen, const std::string& actor, int an)
{
    valid.validare(titlu, gen, actor, an);
    Film f = repo.cauta(titlu_vechi);
    repo.sterge(f);
    Film f_nou{ titlu, gen, actor, an };
    repo.adauga(f_nou);
}

void FilmServ::sterge(const std::string& titlu)
{
    valid.validare(titlu, "gen", "actor", 2000);
    Film f = repo.cauta(titlu);
    repo.sterge(f);
}

bool FilmServ::cauta(const string& titlu) 
{
    try 
    {
        repo.cauta(titlu);
        return true;
    }
    catch (ExceptieRepo&) 
    {
        return false;
    }
}

VectorDinamic <Film> FilmServ::filtrare_titlu(const std::string& titlu)
{
    VectorDinamic <Film> filtrata;
    for (auto& f : repo.getAll())
        if (f.get_titlu() == titlu)
            filtrata.push_back(f);
    return filtrata;
}

VectorDinamic <Film> FilmServ::filtrare_an(int an)
{
    VectorDinamic<Film> filtrata;
    for (auto& f : repo.getAll())
        if (f.get_an() == an)
            filtrata.push_back(f);
    return filtrata;
}

VectorDinamic<Film> FilmServ::sortare(Sort functieSortare) 
{
    VectorDinamic<Film> lista_sortata;
    VectorDinamic<Film> lista = repo.getAll();
    for (int i = 0; i < lista.size(); i++) 
    {
        lista_sortata.push_back(Film(lista.get(i)));
    }
    bool sorted = false;
    while (!sorted) {
        sorted = true;
        for (int i = 0; i < lista_sortata.size() - 1; i++) {
            if (functieSortare(lista_sortata.get(i), lista_sortata.get(i + 1)) > 0) {
                Film aux = lista_sortata.get(i);
                lista_sortata.get(i) = lista_sortata.get(i + 1);
                lista_sortata.get(i + 1) = aux;
                sorted = false;
            }
        }
    }
    return lista_sortata;
}