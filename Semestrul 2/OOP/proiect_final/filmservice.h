//
// Created by Cosmin Secrier on 28.03.2024.
//
#pragma once
#include <string>
#include <vector>
#include "validator.h"
#include "cosfilme.h"
#include "dto.h"
#include "undo.h"
#include "erori.h"
using std::string;
using std::vector;
using std::function;
class FilmService{

private:
    RepoAbstract& rep;
    Validator& Valid;
    CosFilme& cosCurent;
    vector<std::unique_ptr<ActiuneUndo>> UndoList;

public:

    //Constructoare
    FilmService(RepoAbstract& rep, Validator& valid, CosFilme& cos) :rep{ rep }, Valid{ valid } , cosCurent{ cos }{}
    FilmService() = default;
    //FilmService(const FilmService& ot) = delete;
    /* Functie de adaugare
     * titlu - titltul filmului ce urmeaza a fi adaugat, nu poate fi gol
     * gen - genul filmului ce urmeaza a fi adaugat, nu poate fi gol
     * an_aparitie - anul in care a aparut filmul ce urmeaza a fi adaugat, mai mare ca 0
     * actor_principal - actorul principal al filmului ce urmeaza a fi adaugat, nu poate fi gol
    */
    void adauga(const string& titlu, const string& gen, int an_aparitie, const string& actor_principal);
    //Functie ce sterge din lista filmul cu titlul "titlu"
    void sterge(const string& titlu);
    //Functie ce cauta in lista filmul cu titlutl "titlu" si inlocuieste cu valorile din parametrii
    void modifica(const string& titlu, const string& gen_nou, int an_nou, const string& actor_nou);
    //Functie de cautare ce returneaza True daca exista filmul cu titlul "titlu" in lista sau False
    bool cauta(const string &titlu) const;
    //Functie de filtrare ce returneaza un vector de filme care au titlul identic cu cel furnizat prin parametru
    vector<Film> filtrareTitlu(const string& titlu) const;
    //Functie de filtrare ce returneaza un vector de filme care au anul aparitiei acelasi cu cel furnizat prin parametru
    vector<Film> filtrareAnAparitie(int an_aparitie) const;
    /* Functie de sortare
     * filme - vectorul de filme ce urmeaza a fi sortat
     * criteriu - int, criteriul dupa care se sorteaza: 1-titlu, 2-actor, 3-an si gen
    */
    vector<Film> sortare(int criteriu) const;

    const vector<Film>& get_filme() const noexcept{
        return rep.get_filme();
    }
    // Functie ce adauga filmul cu titlul dat ca parametru in cos
    void adauga_cos(const string& titlu);
    // Functie ce adauga *numar* filme in cos
    int adauga_random(int numar);
    const vector<Film>& getAllCos();
    // Functie ce goleste cosul
    void sterge_cos();
    static bool cmp_titlu(const Film& a, const Film& b){
        return a.get_titlu() < b.get_titlu();
    }

    static bool cmp_actor(const Film& a, const Film& b){
        return a.get_actor() < b.get_actor();
    }

    static bool cmp_angen(const Film& a, const Film& b){
        if (a.get_an_aparitie() == b.get_an_aparitie())
            return a.get_gen() < b.get_gen();
        return a.get_an_aparitie() < b.get_an_aparitie();
    }
    static void test_Titlu();
    static void test_Actor();
    static void test_Angen();
    unordered_map<string, DTO> frecvente_gen() const;

    void undo();
    void export_cos(const string& numefisier);

    void sterge_filmcos(string titlu){
        cosCurent.sterge_filmcos(titlu);
    }

    CosFilme& getCos(){
        return cosCurent;
    }
};