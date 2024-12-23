#include "repository.h"
#include <string>


void FilmRepo::adauga(const Film &f2)
{
    filme.push_back(f2);
}

void FilmRepo::sterge(Film& f2)
{
    int i = 0;
    while (i < filme.size() && filme.get(i).get_titlu() == f2.get_titlu()) 
    {
        filme.erase(i);
        break;
    }
}

const Film& FilmRepo::cauta(const string& titlu) const
{
    for (const auto& f : filme)
        if (f.get_titlu() == titlu)
            return f;
    throw ExceptieRepo("Nu exista acest film");

}

ostream& operator<<(ostream& out, const ExceptieRepo& ex) 
{
    out << ex.mesaj;
    return out;
}