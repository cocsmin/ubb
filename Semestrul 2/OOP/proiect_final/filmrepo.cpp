#include "filmrepo.h"
#include <string>
#include <fstream>
using std::cout;
void FilmRepo:: adauga(const Film& movie){
    for (const Film& f : filme) {
        if (f.get_titlu() == movie.get_titlu())
            throw ExceptieRepo("Filmul exista deja in lista!");
    }
    if (movie.get_titlu() == " ")
        throw ExceptieRepo("Titlul este invalid!");
    if (movie.get_actor() == " ")
        throw ExceptieRepo("Numele actorului este invalid!");
    if (movie.get_an_aparitie() < 0)
        throw ExceptieRepo("Anul aparitiei este invalid!");
    if (movie.get_gen() == " ")
        throw ExceptieRepo("Genul filmului este invalid!");

    filme.push_back(movie);
}

void FilmRepo::sterge(const Film &movie) {
    bool ok = false;
    for (const Film &f: filme)
        if (f.get_titlu() == movie.get_titlu())
            ok = true;
    if (!ok)
        throw ExceptieRepo("Nu se poate sterge un film ce nu exista!");
    int i = 0;
    while (i < filme.size() && filme[i].get_titlu() == movie.get_titlu() &&
           filme[i].get_gen() == movie.get_gen() &&
           filme[i].get_actor() == movie.get_actor() && filme[i].get_an_aparitie() == movie.get_an_aparitie()) {
        filme.erase(filme.begin() + i);
        break;
    }
}


const Film& FilmRepo::cauta(const string titlu) {
    auto f = std::find_if(this->filme.begin(), this->filme.end(), [=](const Film& F){
        return (F.get_titlu() == titlu);
    });
    if (f != this->filme.end())
        return (*f);
    throw ExceptieRepo("Nu exista filmul cu acest titlu!");
}

void FilmRepoFile::loadFromFile() {
    std::ifstream fin(numefisier);
    if (!fin.is_open())
        throw ExceptieRepo("Eroare la deschiderea fisierului!!");

    while (!fin.eof()) {
        string titlu, gen, actor;
        int an;
        fin >> titlu;
        if (fin.eof())
            break;
        fin >> gen;
        fin >> an;
        fin >> actor;
        Film film{titlu, gen, an, actor};
        FilmRepo::adauga(film);
    }
    fin.close();
}

void FilmRepoFile::writeToFile() {
    std::ofstream fout(numefisier);
    if (!fout.is_open())
        throw ExceptieRepo("Eroare la deschiderea fisierului!!");

    for (auto &f: FilmRepo::get_filme()) {
        fout << f.get_titlu();
        fout << std::endl;
        fout << f.get_gen();
        fout << std::endl;
        fout << f.get_an_aparitie();
        fout << std::endl;
        fout << f.get_actor();
        fout << std::endl;
    }
    fout.close();
}

void FilmRepoLab::random() const {
    bool ok;
    double pr = probabilitate * 100;
    double sansa = (rand() % 100);
    if (sansa < pr)
        throw ExceptieRepo("ups");
}

void FilmRepoLab::adauga(const Film &movie) {
    random();
    for (auto &f : filme) {
        if (f.second.get_titlu() == movie.get_titlu())
            throw ExceptieRepo("Filmul exista deja in lista!");
    }

    auto l = filme.size();
    filme.insert(std::make_pair(l, movie));
}

void FilmRepoLab::sterge(const Film &movie) {
    random();
    if (!filme.empty()){
        int i = 0;
        while (i < filme.size() && filme.at(i).get_titlu() != movie.get_titlu() && filme.at(i).get_titlu() != movie.get_titlu())
            i++;

        if (i >= filme.size())
            throw ExceptieRepo("Nu se poate sterge un film ce nu exista!");

        for (int j = i; j < filme.size() - 1; j++)
            filme.at(j) = filme.at(j + 1);

        filme.erase( (int)filme.size() - 1);
    }
    else
        throw ExceptieRepo("Nu se poate sterge dintr-un vector gol!");
}

const Film& FilmRepoLab::cauta(const string titlu) {
    random();
    auto it = find_if(filme.begin(), filme.end(), [=](const std::pair<int, Film> f) {
        return f.second.get_titlu() == titlu && f.second.get_titlu() == titlu;
    });

    if (it != filme.end())
        return (*it).second;

    throw ExceptieRepo("Nu exista filmul cu acest titlu!");
}
