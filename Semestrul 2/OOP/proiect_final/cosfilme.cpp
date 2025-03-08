//
// Created by Cosmin Secrier on 12.04.2024.
//

#include "cosfilme.h"
#include <random>
#include <fstream>
using std::shuffle;
void CosFilme::adauga_cos(const Film &film) {
    vector<Film> filme = this->getAllCos();
    for (auto elem : filme)
        if (elem.get_titlu() == film.get_titlu())
            throw ExceptieRepo("Exista deja!");
    this->cos.push_back(film);
    notifica();
}

void CosFilme::adauga_random(vector<Film> filme, int numar) {
    shuffle(filme.begin(), filme.end(), std::default_random_engine(std::random_device{}()));
    while (cos.size() < numar && !filme.empty()){
        cos.push_back(filme.back());
        filme.pop_back();
    }
    notifica();
}

void CosFilme::sterge_filmcos(std::string titlu) {
    int i = 0;
    while (i < this->cos.size() && this->cos.at(i).get_titlu() != titlu)
        i++;
    if (i < this->cos.size())
        this->cos.erase(this->cos.begin() + i);
    else
        throw ExceptieRepo("Filmul nu exista!\n");
    notifica();
}

const vector<Film>& CosFilme::getAllCos() {
    return this->cos;
}

void CosFilme::sterge_cos() {
    this->cos.clear();
    notifica();
}

void CosFilme::exporta_cos(std::string fisier) {
    std::ofstream fout(fisier);
    for (auto& f : cos)
        fout << f.get_titlu() << ":" << f.get_gen() << ":" << f.get_an_aparitie() << ":" << f.get_an_aparitie() << std::endl;

    fout.close();
}