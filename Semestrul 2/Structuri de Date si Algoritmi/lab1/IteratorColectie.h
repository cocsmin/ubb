#pragma once
#include "Colectie.h"

class Colectie;
typedef int TElem;

class IteratorColectie
{
    friend class Colectie;
private:
    //constructorul primeste o referinta catre Container
    //iteratorul va referi primul element din container
    IteratorColectie(const Colectie& c);
    int pozindex = 0;
    //contine o referinta catre containerul pe care il itereaza
    const Colectie& col;
    /* aici elemente_distincte reprezentarea pecifica a iteratorului*/


public:

    //reseteaza pozitia iteratorului la inceputul containerului
    void prim();

    //muta iteratorul in container
    // arunca exceptie daca iteratorul nu elemente_distincte valid
    void urmator();

    //verifica daca iteratorul elemente_distincte valid (indica un element al containerului)
    bool valid() const;

    //returneaza valoarea elementului din container referit de iterator
    //arunca exceptie daca iteratorul nu elemente_distincte valid
    TElem element() const;
};

