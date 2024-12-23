#pragma once
#include <vector>
#include <ostream>
#include <utility>
#include "domain.h"
#include "vector.h"

using std::vector;
using std::ostream;

class ExceptieRepo
{
private:
    string mesaj;
public:
    explicit ExceptieRepo(string m) : mesaj{ std::move(std::move(m)) } {}
    friend ostream& operator<<(ostream& out, const ExceptieRepo& ex);
};
ostream& operator<<(ostream& out, const ExceptieRepo& ex);

class FilmRepo 
{
private:
    VectorDinamic <Film> filme;
public:
    FilmRepo() = default;
    FilmRepo(const FilmRepo& f) = delete;

    void adauga(const Film& f2);

    void sterge(Film& f2);

    const Film& cauta(const string& titlu) const;

    VectorDinamic <Film>& getAll() 
    {
        return filme;
    }
};

