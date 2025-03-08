//
// Created by Cosmin Secrier on 12.04.2024.
//
#pragma once
#include "filmrepo.h"
#include <vector>
#include <random>
#include "observator.h"
using std::vector;

class CosFilme: public Observabil{
private:
    vector<Film> cos;
    FilmRepo& rep;
public:
    CosFilme(FilmRepo& repo) : rep{repo} {};
    /* Functie ce adauga o oferta in cos
     * param: film - filmul ce urmeaza sa fie adaugat in cos
    */
    void adauga_cos(const Film& film);
    /* Functie ce adauga un numar de oferte in cos
     * param: filme - filmele random ce trebuie adaugate in cos
     * param: numar - numarul de file ce trebuie adaugate
    */
    void adauga_random(vector <Film> filme, int numar);
    // Functie ce returneaza vectorul cu toate filmele din cos
    const vector<Film>& getAllCos();
    // Functie ce sterge toate ofertele din cos
    void sterge_cos();

    void sterge_filmcos(string titlu);

    void baga_mare(int numar) {
        int seed = std::chrono::system_clock::now().time_since_epoch().count();
        vector<Film> filme = rep.get_filme();
        std::shuffle(filme.begin(), filme.end(), std::default_random_engine(seed));
        while (cos.size() < numar && filme.size() > 0) {
            cos.push_back(filme.back());
            filme.pop_back();
        }
        notifica();
    }

    void exporta_cos(string fisier);
};
