#include "IteratorMDO.h"
#include "MDO.h"

IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
    // BC = AC = WC = Theta(1)
    curent = dict.min_arbore(dict.radacina);
}

void IteratorMDO::prim(){
    /* BC = Theta(1)
     * AC = WC = Theta(h)
     */
    curent = dict.min_arbore(dict.radacina);
}

void IteratorMDO::urmator(){
    /* BC = Theta(1)
     * AC = WC = Theta(h)
     */
    if (!valid())
        throw std::exception();

    curent = dict.succesor(curent);
}

bool IteratorMDO::valid() const{
	// BC = AC = WC = Theta(1)
	if (curent != -1)
        return true;
    return false;
}

TElem IteratorMDO::element() const{
    // BC = AC = WC = Theta(1)
	if (!valid())
        throw std::exception();

    return dict.elemente[curent].elem;
}


