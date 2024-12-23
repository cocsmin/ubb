#include "IteratorColectie.h"
#include "Colectie.h"
#include <exception>
#include <iostream>
#define NIL INT_MIN
using std::exception;
void IteratorColectie::deplasare() {
    /* BC = Theta(1)
     * AC = O(m)
     * WC = Theta(m)
    */
    while ((curent < col.m) && col.el[curent] == NIL)
        curent++;
}

IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	/* de adaugat */
    /* BC = Theta(1)
     * AC = O(m)
     * WC = Theta(m)
    */
    curent = 0;
    deplasare();
}

void IteratorColectie::prim() {
	/* de adaugat */
    /* BC = Theta(1)
     * AC = O(m)
     * WC = Theta(m)
    */
    curent = 0;
    deplasare();
}


void IteratorColectie::urmator() {
	/* de adaugat */
    /* BC = Theta(1)
     * AC = O(m)
     * WC = Theta(m)
    */
    if (!valid())
        throw std::exception();

    curent++;
    deplasare();
}


bool IteratorColectie::valid() const {
	/* de adaugat */
    //BC = AC = WC = Theta(1)
	if (curent < col.m)
        return true;
    return false;
}



TElem IteratorColectie::element() const {
	/* de adaugat */
    //BC = AC = WC = Theta(1)H
    if (!valid())
        throw std::exception();

    return col.el[curent];
}
