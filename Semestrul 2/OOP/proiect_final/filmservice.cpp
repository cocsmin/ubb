//
// Created by Cosmin Secrier on 28.03.2024.
//

#include "filmservice.h"
#include <vector>
#include <assert.h>
#include <fstream>
using std::sort;
void FilmService :: adauga(const string& titlu, const string& gen, int an_aparitie, const string& actor_principal){
    Valid.valideaza_film(titlu, gen, an_aparitie, actor_principal);
    Film f{titlu,gen,an_aparitie,actor_principal};
    rep.adauga(f);
    UndoList.push_back(std::make_unique<UndoAdauga>(rep, f));
}

void FilmService :: sterge(const std::string &titlu) {
    Valid.valideaza_film(titlu, "exemplu", 2004, "ex");
    Film film = rep.cauta(titlu);
    rep.sterge(film);
    UndoList.push_back(std::make_unique<UndoSterge>(rep, film));
}

void FilmService :: modifica(const std::string &titlu, const std::string &gen_nou, int an_nou, const std::string &actor_nou) {
    Valid.valideaza_film(titlu, gen_nou, an_nou, actor_nou);
    Film film = rep.cauta(titlu);
    rep.sterge(film);
    Film film_nou{titlu, gen_nou, an_nou, actor_nou};
    rep.adauga(film_nou);
    UndoList.push_back(std::make_unique<UndoModifica>(rep, film, film_nou));
}

bool FilmService :: cauta(const string &titlu) const{
    try {
        rep.cauta(titlu);
        return true;
    }catch (ExceptieRepo&){
        return false;
    }
}


vector<Film> FilmService :: filtrareTitlu(const std::string &titlu) const {
    vector<Film> filtrata;
    const vector<Film>& filme = get_filme();
    std::copy_if(filme.begin(), filme.end(), back_inserter(filtrata), [titlu](const Film& f){
        return f.get_titlu() == titlu;
    });

    return (filtrata);
}

vector<Film> FilmService:: filtrareAnAparitie(int an_aparitie) const{
    vector<Film> filtrata;
    const vector<Film>& filme = get_filme();
    std::copy_if(filme.begin(), filme.end(), back_inserter(filtrata), [an_aparitie](const Film& f){
        return f.get_an_aparitie() == an_aparitie;
    });

    return (filtrata);
}

bool Titlu(const Film& f1, const Film& f2){
    return f1.get_titlu() < f2.get_titlu();
}
void FilmService::test_Titlu() {
    Film f1("Grown Ups", "comedie", 2014, "Adam Sandler");
    Film f2("Star Wars", "sf", 1977, "Mark Hamill");
    assert(Titlu(f1, f2) == true);
    Film f3("X-Men", "actiune", 2012, "Charles");
    assert(Titlu(f3, f2) == false);
}

bool Actor(const Film& f1, const Film& f2){
    return f1.get_actor() < f2.get_actor();
}

void FilmService::test_Actor() {
     Film f1("Grown Ups", "comedie", 2014, "Adam Sandler");
     Film f2("Star Wars", "sf", 1977, "Mark Hamill");
     assert(Actor(f1, f2) == true);
     assert(Actor(f2, f1) == false);
}


bool Angen(const Film& f1, const Film& f2){
    return f1.get_an_aparitie() < f2.get_an_aparitie() || (f1.get_an_aparitie() == f2.get_an_aparitie() &&
                                                           f1.get_gen() < f2.get_gen());
}

void FilmService::test_Angen() {
    Film f1("Grown Ups", "comedie", 2014, "Adam Sandler");
    Film f2("Star Wars", "sf", 1977, "Mark Hamill");
    Film f3("X-Men", "actiune", 2014, "Charles");
    assert(Angen(f1, f2) == false);
    assert(Angen(f2, f3) == true);
}

vector<Film> FilmService::sortare(int criteriu) const{
    vector<Film> filme = rep.get_filme();
    //VectorDinamic<Film> filme_sortate;
    if (criteriu == 1){
        /*
        bool sortat = false;
        while (!sortat){
            sortat = true;
            for (int i = 0; i < filme.size() - 1; i++)
                if (Titlu(filme[i], filme[i + 1]) == false){
                    Film aux = filme[i];
                    filme[i] = filme[i + 1];
                    filme[i + 1] = aux;
                    sortat = false;
                }
        }
        */
        sort(filme.begin(), filme.end(), cmp_titlu);
        //return filme;
    }
    else if (criteriu == 2){
        /*
        bool sortat = false;
        while (!sortat){
            sortat = true;
            for (int i = 0; i < filme.size() - 1; i++)
                if (Actor(filme[i], filme[i + 1]) == false){
                    Film aux = filme[i];
                    filme[i] = filme[i + 1];
                    filme[i + 1] = aux;
                    sortat = false;
                }
        }
        */
        sort(filme.begin(), filme.end(), cmp_actor);
        //return filme;
    }
    else if (criteriu == 3){
        /*
        bool sortat = false;
        while (!sortat){
            sortat = true;
            for (int i = 0; i < filme.size() - 1; i++)
                if (Angen(filme[i], filme[i + 1]) == false){
                    Film aux = filme[i];
                    filme[i] = filme[i + 1];
                    filme[i + 1] = aux;
                    sortat = false;
                }
        }
        */
        sort(filme.begin(), filme.end(), cmp_angen);
        //return filme;
    }
    return filme;
}

void FilmService::adauga_cos(const std::string &titlu) {
    const auto& film = rep.cauta(titlu);
    cosCurent.adauga_cos(film);
}

int FilmService::adauga_random(int numar) {
    cosCurent.adauga_random(this->get_filme(), numar);
    return cosCurent.getAllCos().size();
}

const vector<Film>& FilmService::getAllCos() {
    return cosCurent.getAllCos();
}

void FilmService::sterge_cos() {
    cosCurent.sterge_cos();
}


unordered_map<string, DTO> FilmService::frecvente_gen() const
{
    unordered_map<string, DTO> map;
    vector<Film> filme = this->get_filme();
    for (const auto& f : filme) {
        auto ma = map.find(f.get_gen());
        if (ma != map.end()){
            ma->second.increment();
        }
        else
            map.insert({f.get_gen(), 1});
    }
    return map;
}

void FilmService::undo() {
    if (UndoList.empty())
        throw Exception("Nu se mai poate face undo!!");

    UndoList.back()->doUndo();
    UndoList.pop_back();
}

void FilmService::export_cos(const string& numefisier) {
    if (numefisier.find(".csv") == std::string::npos && numefisier.find(".html") == std::string::npos)
        throw Exception("Fisierul nu e valid!!");

    else {
        std::ofstream fout(numefisier);
        if (numefisier.find(".html") != std::string::npos) {
            fout << "<html>";
            fout << "<style> table, th, td {border:1px solid black} body{background-color: #E6E6FA;} </style>";
            fout << "<body>";
            fout << "<h1> WISHLIST </h1>";
            fout << "<table><tr><th>Titlu</th> <th>Gen</th> <th>Anul aparitiei</th> <th>Actor principal</th></tr>";
            for (auto& f:getAllCos()) {
                fout << "<tr><td>" << f.get_titlu() << "<td>" << f.get_gen() << "</td><td>" << f.get_an_aparitie()
                << "</td><td>" << f.get_actor() << "</td></tr>";
            }
            fout << "</table></body>";
            fout << "<html>";
        }
        else
            for (auto& f:getAllCos()) {
                fout << f.get_titlu() << ";" << f.get_gen() << ";" << f.get_an_aparitie()
                << ";" << f.get_actor() << endl;
            }
        fout.close();
    }
}