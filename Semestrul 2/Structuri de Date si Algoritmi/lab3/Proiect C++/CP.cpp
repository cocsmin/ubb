
#include "CP.h"
#include <exception>
#include <stdio.h>

using namespace std;


CP::CP(Relatie r) {
	/*
	 Constructorul. E nevoie de o relatie r
	 BC=AC=WC=theta(1)
	 Spatiu: O(1)
	 */
    this->cap = nullptr;
    this->rel = r;
}


void CP::adauga(TElem e, TPrioritate p) {
	/*
	 Functie ce adauga un element in coada, in fucntie de prioritatea acestuia
	 - daca prioritatea noului element e mai mare decat cea a capului cozii, acesta devine noul cap
	 - daca prioritatea e cuprinsa intre alte 2 elemente din coada il adauga intre acestea
	 - daca prioritatea acestuia e mai mare decat a ultimului il pune penultimul, altfel la final
	 BC - cand coada e goala/prioritatea elementului e mai mare decat a capului -> theta(1)
	 WC - cand prioritatea e f mica - O(n), unde n=nr de elemente din coada
	 AC - O(n)
	 Spatiu: O(1)
	 */
    if (this->cap == nullptr || this->rel(p, this->cap->prioritate)){
        Nod* nou = new Nod();
        nou->valoare = e;
        nou->prioritate = p;
        nou->urmator = this->cap;
        this->cap = nou;
    }
    else{
        Nod* curent = this->cap;
        Nod* anterior = curent;
        while (!this->rel(p, curent->prioritate) && curent->urmator != nullptr){
            anterior = curent;
            curent = curent->urmator;
        }
        if (this->rel(p, curent->prioritate)){
            Nod* nou = new Nod();
            nou->valoare = e;
            nou->prioritate = p;
            nou->urmator = curent;
            anterior->urmator = nou;
        }
        else if (curent->urmator == nullptr ){
            Nod* nou = new Nod();
            nou->valoare = e;
            nou->prioritate = p;
            nou->urmator = nullptr;
            curent->urmator = nou;
        }
    }
}

//arunca exceptie daca coada e vida
Element CP::element() const {
	/*
    Functie ce returneaza elementul din capul cozii, arunca exceptie daca nu exista
    BC=WC=AC=theta(1)
    Spatiu: O(1)
	 */
    if (!this->cap)
        throw exception();
	return pair <TElem, TPrioritate>  (this->cap->valoare, this->cap->prioritate);       // copy constructor;
}

Element CP::sterge() {
	/*
    Functie ce sterge elementul din capul cozii, noul cap devenind urmatorul element
    succesiv si arunca exceptie daca coada e goala si nu se mai poate sterge
    Returneaza perechea (element,prioritate) care a fost stearsa din cap
    BC=WC=AC=theta(1)
    Spatiu: O(1)
	 */
    if (!this->cap)
        throw exception();
    Nod* aux = this->cap->urmator;
    int val = this->cap->valoare;
    int pr = this->cap->prioritate;
    delete this->cap;
    this->cap = aux;
	return pair <TElem, TPrioritate>  (val, pr);
}

bool CP::vida() const {
	/*
    Functie ce verifica daca coada este goala sau nu
    BC=AC=WC=theta(1)
    Spatiu: O(1)
	 */
	if (!this->cap)
        return true;
    return false;
}


CP::~CP() {
	/*
    Destructor
    BC - cand coada e formata doar dintr-un singur element -> O(1)
    WC - cand coada are mai multe elemente -> O(n), unde n=nr de elemente din coada
    AC - O(n)
    Spatiu: O(1)
	 */
    Nod* curent = this->cap;
    while (curent){
        Nod* urmator = curent->urmator;
        delete curent;
        curent = urmator;
    }
    this->cap = nullptr;
}


//Functie noua
/*
 Functia primeste ca parametru un obiect de tipul Priority Queue(coada cu prioritati) pe care il adauga in
 coada curenta in fucntie de prioritatea fiecarui element din aceasta

 BC=theta(m) -> prioritatea elem din b este mai mare decat TOATE prioritatile din
 coada curenta
 WC=theta(n*m) -> unde n este lungimea cozii curente iar m lungimea cozii b, cand toate elementele din b au prioritatea
 mai mica decat cele din coada curenta
 AC=O(n*m)

 pseudocod:
 Algoritm: adaugaToateElementele(Coada Prioritati b)
        Nod b_curent <- b.cap
        cat_timp b_curent != nullptr executa
            adauga([b_curent].valoare, [b_curent].prioritate)
            b_curent <- [b_curent].urmator
        sf cat_timp
SfAlgoritm
 */
void CP::adaugaToateElementele(const CP &b) {
    Nod* b_curent = b.cap;
    while (b_curent){
        adauga(b_curent->valoare, b_curent->prioritate);
        b_curent = b_curent->urmator;
        }
    }

