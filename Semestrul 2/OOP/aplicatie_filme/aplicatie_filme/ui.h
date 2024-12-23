#pragma once
#include "service.h"
#include "domain.h"
#include "vector.h"

class Ui 
{
    FilmServ& serv;
    void meniu();
    void adauga_default();
    void adauga_ui();
    void afisare_ui(const VectorDinamic <Film>& filme);
    void modifica_ui();
    void sterge_ui();
    void cauta_ui();
    void filtrare_titlu_ui();
    void filtrare_an_ui();
    void sortare_titlu_ui();
    void sortare_actor_ui();
    void sortare_an_gen_ui();
public:
    explicit Ui(FilmServ& serv) : serv{ serv } 
    {
    }
    Ui(const Ui& fs) = delete;
    void run();
};