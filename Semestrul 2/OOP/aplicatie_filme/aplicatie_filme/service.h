#pragma once
#include <vector>
#include <string>
#include "domain.h"
#include "repository.h"
#include "validator.h"
#include "vector.h"
using std::vector;
using std::string;
typedef int(*Sort)(const Film&, const Film&);

class FilmServ
{
private:
    FilmRepo& repo;
    Validator& valid;
public:
    
    explicit FilmServ(FilmRepo& repo, Validator& valid) :repo{ repo }, valid{ valid }
    {
    }
    FilmServ() = delete;
    FilmServ(const FilmServ& fs) = delete;
    void adauga(const string& titlu, const string& gen, const string& actor, int an);
    void modifica(const string& titlu_vechi, const string& titlu, const string& gen, const string& actor, int an);
    void sterge(const string& titlu);
    bool cauta(const string& titlu);
    VectorDinamic <Film> filtrare_titlu(const string& titlu);
    VectorDinamic <Film> filtrare_an(int an);
    VectorDinamic <Film> sortare(Sort functieSortare);
    VectorDinamic <Film>& getAll() noexcept
    {
        return repo.getAll();
    }
};

