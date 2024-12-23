#include "TestScurt.h"

#include <assert.h>
#include <exception>
#include <iostream>

#include "Lista.h"
#include "IteratorLP.h"



using namespace std;

void testAll(){
	Lista lista = Lista();
	assert(lista.dim() == 0);
	assert(lista.vida());

    lista.adaugaSfarsit(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());


    IteratorLP it = lista.cauta(1);
    assert(it.valid());
    assert(it.element() == 1);
    it.urmator();
    assert(!it.valid());
    it.prim();
    assert(it.valid());
    assert(it.element() == 1);

    assert(lista.sterge(it) == 1);
    assert(lista.dim() == 0);
    assert(lista.vida());

    lista.adaugaInceput(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());

    //test filtreaza
    Lista lista2 = Lista();
    assert(lista2.vida());
    for (int i = 1; i < 10; i++)
        lista2.adaugaSfarsit(i);
    assert(lista2.dim() == 9);
    lista2.filtreaza(condi);
    assert(lista2.dim() == 6);
    IteratorLP it10 = lista2.cauta(2);
    assert(!it10.valid());
}

