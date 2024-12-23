#include "IteratorColectie.h"
#include "Colectie.h"
#include <stdexcept>

/*
 initializeaza indexul iteratorului cu 0
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
    this->pozindex = 0;
}

/*
 initializeaza indexul iteratorului cu 0, fiind primul element
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
void IteratorColectie::prim() {
    this->pozindex = 0;
}

/*
 trece la urmatorul iterator daca este valida colectia, altfel arunca o exceptie
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
void IteratorColectie::urmator() {
    if(valid())
        this->pozindex++;
    else throw std::exception();
}

/*
 returneaza true daca indexul nu a terminat de parcurs colectia, altfel false
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
bool IteratorColectie::valid() const {
    return this->pozindex >= 0 && this->pozindex < col.dim();
}

/*
 returneaza elementul cu indexul de la pozitia care are indexul pozindex,
 altfel -1
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
TElem IteratorColectie::element() const {
    if(valid())
        return col.elemente_distincte[col.pozitii_elemente[this->pozindex]];
    return -1;
}
