#include "TestScurt.h"
#include <assert.h>
#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>




void testAll() { //apelam fiecare functie sa vedem daca exista
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente
	c.adauga(5);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	assert(c.dim() == 7);
	assert(c.cauta(10) == true);
	assert(c.cauta(16) == false);
	assert(c.nrAparitii(1) == 2);
	assert(c.nrAparitii(7) == 1);
	assert(c.sterge(1) == true);
	assert(c.sterge(6) == false);
	assert(c.dim() == 6);
	assert(c.nrAparitii(1) == 1);
	IteratorColectie ic = c.iterator();
	ic.prim();
	while (ic.valid()) {
		TElem e = ic.element();
		ic.urmator();
	}

    Colectie col;
    assert(col.vida() == true);
    col.adauga(5);
    col.adauga(3);
    col.adauga(13);
    col.adauga(17);
    col.adauga(17);
    assert(col.elementeCuFrecventaData(2) == 1);
    assert(col.elementeCuFrecventaData(1) == 3);
    try{
        col.elementeCuFrecventaData(0);
        assert(false);
    }catch (std::exception){
        assert(true);
    }
}