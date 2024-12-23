#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>
#define NIL INT_MIN
#define DEL INT_MIN+10
using namespace std;

int hasdcode(TElem elem){
    //BC = AC = WC = Theta(1)
    return abs(elem);
}

int Colectie::d(TElem elem, int i) {
    //BC = AC = WC = Theta(1)
    unsigned long c1, c2;
    c1 = hasdcode(elem) % m;
    c2 = (1 + hasdcode(elem) % (m - 2)) % m;
    return int((c1 + (i * c2) % m) % m);
}

Colectie::Colectie() {
	/* de adaugat */
    //BC = AC = WC = Theta(m)
    m = MAX;
    el = new TElem[m];
    for (int i = 0; i < m; i++)
        el[i] = NIL;

}

int prim(int x){
    if (x <= 1 || x % 2 == 0)
        return 0;
    for (int d = 3; d*d <= x; d = d+2)
        if (x % d == 0)
            return 0;
    return 1;
}

void Colectie::redim() {
    /* BC = Theta(m)
     * AC = O(m^2)
     * WC = Theta(m^2)
    */
    int aux = m;
    m = m + 2;
    while (!prim(m))
        m = m + 2;

    TElem* elNoi = new int[m];
    TElem* elAux = new int[aux];

    for (int i = 0; i < aux; i++)
        elAux[i] = el[i];

    for (int i = 0; i < m; i++)
        elNoi[i] = NIL;

    delete[] el;
    el = elNoi;
    for (int i = 0; i < aux; i++)
        adauga(elAux[i]);

    delete[] elAux;
}

void Colectie::adauga(TElem elem) {
	/* de adaugat */
    /* BC = Theta(m)
     * AC = O(m^2)
     * WC = Theta(m^2)
    */
    int i = 0;
    bool ok = false;
    do {
        int j = d(elem, i);
        if ((el[j] == NIL) || (el[j] == DEL)){
            el[j] = elem;
            ok = true;
        }
        else
            i++;
    } while (i < m && !ok);

    if (i == m){
        redim();
        adauga(elem);
    }
}


bool Colectie::sterge(TElem elem) {
	/* de adaugat */
    /* BC = Theta(1)
     * AC = O(m)
     * WC = Theta(m)
    */
    int i = 0;
    bool ok = false;
    do {
        int j = d(elem, i);
        if (el[j] == NIL)
            break;
        if (el[j] == elem){
            el[j] = DEL;
            ok = true;
        }
        else
            i++;
    } while (i < m && !ok);
	return ok;
}


bool Colectie::cauta(TElem elem) {
	/* de adaugat */
    /* BC = Theta(1)
     * AC = O(m)
     * WC = Theta(m)
    */
    int i = 0;
    bool ok = false;
    do {
        int j = d(elem, i);
        if (el[j] == NIL)
            ok = false;
        if (el[j] == elem)
            ok = true;
        else
            i++;
    } while (i < m && !ok);
	return ok;
}

int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */
    //BC = AC = WC = Theta(m)
	int contor = 0;
    for (int i = 0; i < m; i++)
        if (el[i] == elem)
            contor++;

    return contor;
}


int Colectie::dim() const {
	/* de adaugat */
    //BC = AC = WC = Theta(m)
    int nrElemente = 0;
    for (int i = 0; i < m; i++)
        if (el[i] != NIL && el[i] != DEL)
            nrElemente++;

	return nrElemente;
}


bool Colectie::vida() const {
	/* de adaugat */
    //BC = AC = WC = Theta(m)
    if (dim() == 0)
        return true;
    return false;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	/* de adaugat */
    delete[] el;
}


//functionalitate noua
/* Algoritm elementeCuFrecventaData(int frecventa){
 *      intreg fr[m], dublura[m], numere <- 0
 *      daca frecventa <= 0
 *          arunca exceptie
 *
 *      pentru i <- 0,m{
 *          fr[i] = nrAparaitii(el[i]);
 *          daca (fr[i] = frecventa ^ dublura[el[i]] != NIL)
 *              numere <- numere + 1
 *              dublura[el[i]] = NIL
 *          sf daca
 *      sfpentru
 *      returneaza numere
 * sfAlgoritm
 */
// BC = AC = WC = Theta(m^2);

int Colectie::elementeCuFrecventaData(int frecventa) const {
    int fr[m], dublura[m], numere = 0;
    if (frecventa <= 0)
        throw std::exception();

    for (int i = 0; i < m; i++){
        fr[i] = nrAparitii(el[i]);
        if (fr[i] == frecventa && dublura[el[i]] != NIL){
            numere++;
            dublura[el[i]] = NIL;
        }
    }
    return numere;
}

