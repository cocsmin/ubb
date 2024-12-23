#include <iostream>
#include <exception>

#include "IteratorLP.h"
#include "Lista.h"

using std::exception;
Lista::Lista() {
	/* de adaugat */
    //BC=WC=AC=theta(1)
    capacitate = 10;
    lungime = 0;
    el = new TElem[capacitate];
    urm = new int[capacitate];
    ant = new int[capacitate];
    primu = 0;
    ultimu = 0;
    primLiber = 1;
    for (int i = 1; i < capacitate - 1; i++){
        urm[i] = i + 1;
        ant[i + 1] = i;
    }
    urm[capacitate - 1] = 0;
    ant[1] = 0;
}

int Lista::dim() const {
	/* de adaugat */
    //BC=WC=AC=theta(1)
    return lungime;
}


bool Lista::vida() const {
	/* de adaugat */
    //BC=WC=AC=theta(1)
    if (lungime == 0)
        return true;

    return false;
}

IteratorLP Lista::prim() const {
	/* de adaugat */
    //BC=WC=AC=theta(1)
    IteratorLP it = IteratorLP(*this);
    it.curent = primu;
    return it;

}

TElem Lista::element(IteratorLP poz) const {
	/* de adaugat */
    //BC=WC=AC=theta(1)
    return poz.element();
}

TElem Lista::sterge(IteratorLP& poz) {
	/* de adaugat */
    /* Arunca exceptie daca iteratorul e invalid, daca lista e goala
     * BC: Theta(1) - elementul trebuie sters de pe prima pozitie
     * AC: O(n)
     * WC: Theta(n) - elementul trebuie sters de pe ultima pozitie
    */
	if ((primu == 0) || (dim() == 0) || (!poz.valid()))
        throw exception();

    int pozitie = primu;
    int i = 0;
    while (i < poz.curent){
        i++;
        pozitie = urm[pozitie];
    }
    TElem sters = el[i];
    if (i == 0){
        primLiber = 1;
        primu = 0;
        ultimu = 0;
        lungime--;
        return sters;

    }
    else {
        urm[ant[pozitie]] = urm[pozitie];
        ant[urm[pozitie]] = ant[pozitie];

        urm[pozitie] = ant[pozitie] = 0;
        lungime--;
        return sters;
    }
}

IteratorLP Lista::cauta(TElem e) const{
	/* de adaugat */
    /* BC: Theta(1) - elementul cautat e pe prima pozitie
     * AC: O(n)
     * WC: Theta(n) - elementul cautat e pe ultima pozitie
    */
    IteratorLP it = IteratorLP(*this);
    it.curent = 0;
    if (dim() == 0)
        return it;

    int i = primu;
    while (el[i] != e) {
        i = urm[i];
        if (i == 0)
            return it;
    }

    if ((i != ultimu) && (el[i] == e))
        it.curent = i;

    return it;
}

TElem Lista::modifica(IteratorLP poz, TElem e) {
	/* de adaugat */
    /* Arunca exceptie daca iteratorul e invalid
     * BC: Theta(1) - elementul ce trebuie modificat e pe prima pozitie
     * AC: O(n)
     * WC: Theta(n) - elementul ce trebuie modificat e pe ultima pozitie
    */
	if ((dim() == 0) || (!poz.valid()))
        throw exception();

    int i = primu;
    while (i != 0){
        if (i == poz.curent){
            TElem modif = el[i];
            el[i] = e;
            return modif;
        }
        i = urm[i];
    }
}

void Lista::adauga(IteratorLP& poz, TElem e) {
    /* de adaugat */
    /* Arunca exceptie daca iteratorul e invalid
     * BC: Theta(1) - elementul trebuie adaugat pe prima pozitie
     * AC: O(n)
     * WC: Theta(n) - elemntul trebuie adaugat pe ultima pozitie
    */
    if (!poz.valid())
        throw exception();

    if (lungime == capacitate - 2)
        redim();
    int i = 0;
    if (vida()){
        el[i] = el[poz.curent];
        ant[i] = poz.curent;
        el[poz.curent] = e;
        urm[poz.curent] = i;
        urm[i] = 0;
        lungime++;
    }else {
        i = primu;
        while (i != poz.curent)
            i = urm[i];

        if (i == primu)
            adaugaInceput(e);
        else{
            int nouLiber = urm[primLiber];
            el[primLiber] = e;
            ant[primLiber] = ant[i];
            urm[primLiber] = i;
            urm[ant[i]] = primLiber;
            ant[i] = primLiber;
            poz.curent = primLiber;
            primLiber = nouLiber;
            ant[primLiber] = 0;
        }
    lungime++;
    }

}

void Lista::adaugaInceput(TElem e) {
	/* de adaugat */
    // BC=AC=WC=Theta(1)
    if (lungime == capacitate - 2)
        redim();

    urm[primLiber] = primu;
    el[primLiber] = e;
    primu = primLiber;
    ultimu = urm[primLiber];
    primLiber = urm[urm[primLiber]];
    lungime++;
}

void Lista::adaugaSfarsit(TElem e) {
    /* de adaugat */
    /* BC = Theta(1) - lista e goala
     * AC = WC = Theta(n)
    */
    if (lungime == capacitate - 2)
        redim();
    int i = primu;
    if (i == 0) {
        primu = 1;
        el[primu] = e;
        primLiber = urm[primLiber];
        urm[primu] = 0;
        ant[primu] = 0;
        lungime++;
    }
    else {
        while (urm[i] != 0)
            i = urm[i];

        urm[i] = primLiber;
        primLiber = urm[primLiber];
        el[urm[i]] = e;
        urm[urm[i]] = 0;
        ant[urm[i]] = i;
        lungime++;

    }
}

void Lista::redim() {
    //BC = AC = WC = Theta(n)
    int cp = capacitate;
    capacitate = capacitate * 2;
    primLiber = cp;
    TElem* temp1 = new TElem[capacitate];
    int* temp2 = new int[capacitate];
    int* temp3 = new int[capacitate];
    for(int i = 0; i < cp; i++){
        temp1[i] = el[i];
        temp2[i] = urm[i];
        temp3[i] = ant[i];
    }

    for (int i = cp; i < capacitate; i++){
        temp2[i] = i + 1;
        temp3[i] = i - 1;
    }
    delete[] el;
    delete[] urm;
    delete[] ant;
    el = temp1;
    urm = temp2;
    ant = temp3;
}


/* PSEUDOCOD:
*algoritm_filtreaza(Conditie cond)
* intreg i <- primu
* Iterator it = Iterator(lista-curenta)
* cat timp(it este valid) executa
    daca (conditie(el[i]) este fals)
        sterge(it)
    sfdaca
    i <- urm[i]
    it.urmator()
* sfcattimp
*sfalgortm
*/
void::Lista::filtreaza(Conditie cond){
    /* BC = Theta(n)
     * AC = WC = O(n^2)
    */
    IteratorLP it = IteratorLP(*this);
    while (it.valid()){
        if (!cond(it.element()))
            sterge(it);
        it.urmator();
    }
}

bool condi(TElem e) {
    return e > 4;
}

Lista::~Lista() {
	/* de adaugat */
    delete[] el;
    delete[] urm;
    delete[] ant;
}
