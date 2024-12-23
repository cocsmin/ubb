#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>
#include <stack>
#include <exception>
#include <utility>
using namespace std;

MDO::MDO(Relatie r) {
	/* de adaugat */
    rel = r;
    radacina = -1;
    PrimLiber = 0;
    capacitate = 10;
    elemente = new ABC_NOD[capacitate];
    for (int i = 0; i < capacitate; i++){
        elemente[i].parinte = -1;
        elemente[i].drept = -1;
        elemente[i].stang = i + 1; //pentru ca o sa l folosesc pe post de urmator
    }
    elemente[capacitate - 1].stang = -1;
}


void MDO::adauga(TCheie c, TValoare v) {
	/* BC = Theta(1)
	 * AC = WC = Theta(h)
	 */
    TElem val = make_pair(c, v);
    if (radacina == -1){
        radacina = PrimLiber;
        int NouLiber = elemente[PrimLiber].stang;
        ABC_NOD el;
        el.stang = -1;
        el.drept = -1;
        el.elem = val;
        elemente[PrimLiber] = el;
        elemente[PrimLiber].parinte = -1;
        PrimLiber = NouLiber;
        return;
    }
    int ant;
    int curent = radacina;
    while (curent != -1){
        ant = curent;
        if (rel(val.first,elemente[curent].elem.first))
            curent = elemente[curent].stang;
        else
            curent = elemente[curent].drept;
    }
    if (rel(val.first,elemente[ant].elem.first)){
        elemente[ant].stang = PrimLiber;
        int urm = elemente[PrimLiber].stang;
        ABC_NOD el;
        el.stang = -1;
        el.drept = -1;
        el.elem = val;
        elemente[PrimLiber] = el;
        elemente[PrimLiber].parinte = ant;
        PrimLiber = urm;
    }
    else{
        elemente[ant].drept = PrimLiber;
        int urm = elemente[PrimLiber].stang;
        ABC_NOD el;
        el.stang = -1;
        el.drept = -1;
        el.elem = val;
        elemente[PrimLiber] = el;
        elemente[PrimLiber].parinte = ant;
        PrimLiber = urm;
    }
    if (PrimLiber == -1)
        redim();
}

vector<TValoare> MDO::cauta(TCheie c) const {
    /* BC = Theta(1)
     * AC = WC = Theta(h)
     */
	vector<TValoare> cautat;
    cautat.clear();
    int nod = radacina;
    while (nod != -1){
        if (elemente[nod].elem.first == c)
            cautat.push_back(elemente[nod].elem.second);
        if (rel(c,elemente[nod].elem.first))
            nod = elemente[nod].stang;
        else
            nod = elemente[nod].drept;
    }
    return cautat;
}

bool MDO::sterge(TCheie c, TValoare v) {
    /* BC = Theta(1)
     * AC = WC = Theta(h)
     */
    int nod = radacina;
    int sters = -1;
    while (nod != -1){
        if (elemente[nod].elem.first == c && elemente[nod].elem.second == v){
            sters = nod;
            break;
        }
        if (rel(c,elemente[nod].elem.first))
            nod = elemente[nod].stang;
        else
            nod = elemente[nod].drept;
    }
    if (sters == -1)
        return false;

    if (elemente[sters].stang == -1)
        tran(sters, elemente[sters].drept);
    else if (elemente[sters].drept == -1)
        tran(sters, elemente[sters].stang);
    else{
        nod = min_arbore(elemente[sters].drept);
        if (elemente[nod].parinte != sters){
            tran(nod, elemente[nod].drept);
            elemente[nod].drept = elemente[sters].drept;
            elemente[elemente[nod].drept].parinte = nod;
        }
        tran(sters, nod);
        elemente[nod].stang = elemente[sters].stang;
        elemente[elemente[nod].stang].parinte = nod;
    }
    sterge_nod(sters);
    return true;
}

int MDO::dim() const {
	// BC = AC = WC = Theta(n)
    stack<int> stiva;
    int len = 0;
    int nod = radacina;
    stiva.push(nod);
    while (!stiva.empty()){
        nod = stiva.top();
        stiva.pop();
        if (nod != -1){
            len++;
            stiva.push(elemente[nod].stang);
            stiva.push(elemente[nod].drept);
        }
    }
    return len;
}

bool MDO::vid() const {
    // BC = AC = WC = Theta(1)
	if (radacina == -1)
        return true;
    return false;
}

IteratorMDO MDO::iterator() const {
    // BC = AC = WC = Theta(1)
    return IteratorMDO(*this);
}

MDO::~MDO() {
	/* de adaugat */
    delete[] elemente;
}

void MDO::redim() {
    int cp = capacitate;
    capacitate = capacitate * 2;
    ABC_NOD* temp = new ABC_NOD[capacitate];
    for (int i = 0; i < cp; i++)
        temp[i] = elemente[i];
    PrimLiber = cp;
    for (int i = PrimLiber; i < capacitate; i++){
        temp[i].parinte = -1;
        temp[i].drept = -1;
        temp[i].stang = i + 1;
    }
    temp[capacitate - 1].stang = -1;
    delete[] elemente;
    elemente = temp;
}

int MDO::min_arbore(int nod) const{
    stack<TCheie> stiva;
    stiva.push(nod);
    int ret = NULL;
    while (!stiva.empty()){
        int curent = stiva.top();
        stiva.pop();
        if (ret == NULL){
            ret = curent;
            continue;
        }
        if (rel(elemente[curent].elem.first,elemente[ret].elem.first))
            ret = curent;
        stiva.push(elemente[curent].stang);
    }
    return ret;
}

void MDO::tran(int acolo, int aici) {
    if (elemente[acolo].parinte == -1)
        radacina = aici;
    else if (elemente[elemente[acolo].parinte].stang == acolo)
        elemente[elemente[acolo].parinte].stang = aici;
    else
        elemente[elemente[acolo].parinte].drept = aici;

    if (aici != -1)
        elemente[aici].parinte = elemente[acolo].parinte;
}

void MDO::sterge_nod(int index) {
    int aux = PrimLiber;
    PrimLiber = index;
    elemente[index].drept = -1;
    elemente[index].parinte = -1;
    elemente[index].stang = aux;
}

int MDO::succesor(int index) const {
    if (elemente[index].drept != -1)
        return min_arbore(elemente[index].drept);

    int ex = elemente[index].parinte;
    while ((ex != -1) && (elemente[ex].drept == index)){
        index = ex;
        ex = elemente[ex].parinte;
    }
    return ex;
}
//Functionalitate noua
/* pseudocod:
vector<Tvalaloare> colectiaValorilor()
    vector<TValoare> valori;
    intreg nod <- radacina;
    valori.adauga(elemente[nod].elem.second);
    executa
        nod <- elemente[nod].stang;
        daca nod = -1
            break;
        valori.adauga(elemente[nod].elem.second);
    cat timp nod != -1

    nod <- radacina;
    executa
        nod <- elemente[nod].drept;
        daca nod = -1
            break;
        valori.adauga(elemente[nod].elem.second);
    cat timp nod != -1

    return valori;
sfAlgoritm
*/
// BC = AC = WC = Theta(n);
void MDO::parcurgere(vector<TValoare>& val, int nod) const{
    if (nod == -1)
        return;
    parcurgere(val, elemente[nod].stang);
    val.push_back(elemente[nod].elem.second);
    parcurgere(val,elemente[nod].drept);
}
vector<TValoare> MDO::colectiaValorilor() const {
    vector<TValoare> valori;
    /*
    int nod = radacina;
    valori.push_back(elemente[nod].elem.second);
    do {
        nod = elemente[nod].stang;
        if (nod == -1){
            nod = elemente[nod].parinte;
        }
        valori.push_back(elemente[nod].elem.second);
    } while (nod != -1);

    nod = radacina;
    do {
        nod = elemente[nod].drept;
        if (nod == -1)
            break;
        valori.push_back(elemente[nod].elem.second);
    } while (nod != -1);

    */
    parcurgere(valori, radacina);
    return valori;
}

