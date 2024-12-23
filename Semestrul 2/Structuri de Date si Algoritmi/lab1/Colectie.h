#pragma once

#define NULL_TELEM -1
typedef int TElem;

class IteratorColectie;

class Colectie
{
    friend class IteratorColectie;
private:
    int cp_elemente;
    int cp_elemente_pozitii;
    int nr_poz;
    int nr_dist;
    TElem *elemente_distincte; // vectorul cu elemente distincte
    int *pozitii_elemente; // vector de pozitii
    void redim_poz();
    void redim_elem();
    //returneaza indicele vectorului de pozitii unde elementul din vector
    //este egal cu i
    int functie(int i);
public:
    //constructorul implicit
    Colectie();

    //adauga un element in colectie
    void adauga(TElem elem);

    //sterge o aparitie a unui element din colectie
    //returneaza adevarat daca s-a putut sterge
    bool sterge(TElem elem);

    //verifica daca un element se afla in colectie
    bool cauta(TElem elem) const;

    //returneaza numar de aparitii ale unui element in colectie
    int nrAparitii(TElem elem) const;


    //intoarce numarul de elemente din colectie;
    int dim() const;

    //verifica daca colectia elemente_distincte vida;
    bool vida() const;

    //returneaza un iterator pe colectie
    IteratorColectie iterator() const;

    //returneaza diferenta dintre valoarea maxima si cea minima (presupunem valori intregi)
    //daca colectia este vida se returneaza -1
    int diferenta() const;

    // destructorul colectiei
    ~Colectie();



};

