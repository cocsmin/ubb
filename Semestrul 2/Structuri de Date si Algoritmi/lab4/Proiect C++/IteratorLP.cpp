#include "IteratorLP.h"
#include "Lista.h"
#include <exception>
using std::exception;
IteratorLP::IteratorLP(const Lista& l):lista(l) {
	/* de adaugat */
    // BC = AC = WC = Theta(1)
    curent = lista.primu;
}

void IteratorLP::prim(){
	/* de adaugat */
    // BC = AC = WC = Theta(1)
    curent = lista.primu;
}

void IteratorLP::urmator(){
	/* de adaugat */
    // BC = AC = WC = Theta(1)
    // arunca exceptie daca e invalid
    if (!valid())
        throw exception();

    curent = lista.urm[curent];
}

bool IteratorLP::valid() const{
	/* de adaugat */
    // BC = AC = WC = Theta(1)

    if (curent != 0)
        return true;
    return false;
}

TElem IteratorLP::element() const{
	/* de adaugat */
    // BC = AC = WC = Theta(1)

    if (!valid())
        throw exception();

    return lista.el[curent];
}


