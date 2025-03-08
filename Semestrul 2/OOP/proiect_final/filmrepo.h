#include "film.h"
#include <vector>
//#include "vectordinamic.h"
#include <utility>
#include <string>
#include <memory>
#include <unordered_map>
#include <random>
using std::string;
using std::vector;
class ExceptieRepo{
    string mesaj;
public:
    //implementare pentru exceptii
    ExceptieRepo(string m) :mesaj{m} {

    }
    string get_mesaj() const{
        return mesaj;
    }
};

class RepoAbstract{
public:
    RepoAbstract() = default;

    RepoAbstract(const RepoAbstract& f) = delete;

    virtual void adauga(const Film& f) = 0;

    virtual void sterge(const Film& f) = 0;

    virtual const Film& cauta(string titlu) = 0;

    virtual vector<Film>& get_filme() = 0;
};

class FilmRepo : public RepoAbstract {
    vector<Film> filme;

public:
    //constructor
    FilmRepo(const FilmRepo& ot) = delete;
    FilmRepo() = default;
    // Functie de adaugare in repo cu parametrul movie - filmul ce va fi adaugat
    void adauga(const Film& movie) override;
    // Functie de stergere din repo, cu parametrul movie - filmul ce va fi sters
    void sterge(const Film& movie) override;
    /* Functie ce cauta un anumit film in lista
     * titlu - titlul filmului cautat
     * returneaza filmul daca exista in lista sau exceptie daca nu exista
    */
    const Film& cauta(const string titlu) override;
    // Functie de get pentru toate filmele
    vector<Film>& get_filme() override{
        return filme;
    }



};

class FilmRepoFile : public FilmRepo{
private:
    string numefisier;

    void loadFromFile();

    void writeToFile();

public:
    explicit FilmRepoFile(string numefisier): FilmRepo(), numefisier{ numefisier } {
        loadFromFile();
    }

    void adauga(const Film& f) override {
        FilmRepo::adauga(f);
        writeToFile();
    }

    void sterge(const Film& f) override {
        FilmRepo::sterge(f);
        writeToFile();
    }
};


class FilmRepoLab : public RepoAbstract {
private:
    std::unordered_map<int, Film> filme;
    double probabilitate;


public:
    //explicit FilmRepoLab(double probabilitate): RepoAbstract(), probabilitate { probabilitate } {};
    // Functie de adaugare in repo cu parametrul movie - filmul ce va fi adaugat
    void adauga(const Film& movie) override;
    // Functie de stergere din repo, cu parametrul movie - filmul ce va fi sters
    void sterge(const Film& movie) override;
    /* Functie ce cauta un anumit film in lista
     * titlu - titlul filmului cautat
     * returneaza filmul daca exista in lista sau exceptie daca nu exista
    */
    const Film& cauta(const string titlu) override;
    // Functie de get pentru toate filmele
    vector<Film> filmulete;
    vector<Film>& get_filme() override{
        //filmulete.erase(filmulete.begin(), filmulete.end());
        filmulete.clear();
        if (!filme.empty())
            for (auto &f : filme)
                filmulete.push_back(f.second);

        return filmulete;
    }
    void random() const;
    void set_probabilitate(double p){
        probabilitate = p;
    }
};