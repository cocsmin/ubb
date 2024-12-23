#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>


using namespace std;

/*
 creez un nou vector de pozitii pe care il dublez si mut in el elementele vectorului meu
 de pozitii.
 apoi il sterg pe cel vechi si il mut pe cel nou dublat in cel pe care il folosesc
 BC: teta(1)
 WC: teta(nr_poz)
 TC = teta(nr_poz)
 */
void Colectie::redim_poz() {
    int *pozNou = new int[this->cp_elemente_pozitii * 2];
    for (int i = 0; i < this->nr_poz; i++) {
        pozNou[i] = this->pozitii_elemente[i];
    }
    this->cp_elemente_pozitii *= 2;
    delete[] this->pozitii_elemente;
    this->pozitii_elemente = pozNou;
}
/*
 creez un nou vector de elemente distincte pe care il dublez si mut in el elementele vectorului meu
 de elemente distincte.
 apoi il sterg pe cel vechi si il mut pe cel nou dublat in cel pe care il folosesc
 BC: teta(1)
 WC: teta(nr_dist)
 TC = teta(nr_dist)
 */
void Colectie::redim_elem() {
    TElem *eNou = new TElem[2 * this->cp_elemente];
    for (int i = 0; i < this->nr_dist; i++) {
        eNou[i] = this->elemente_distincte[i];
    }
    this->cp_elemente *= 2;
    delete[] this->elemente_distincte;
    this->elemente_distincte = eNou;
}
/*
 creez o colectie in care initializez capacitatile vectorilor, nr de elemente si vectorii
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
Colectie::Colectie() {
    cp_elemente = 2;
    cp_elemente_pozitii = 2;
    this->elemente_distincte = new TElem[this->cp_elemente];
    this->pozitii_elemente = new int[this->cp_elemente_pozitii];
    this->nr_dist = 0;
    this->nr_poz = 0;

}

/*
 adaug in colectie un element in vectorul de distincte daca nu exista, iar daca exista ii adaug doar pozitia
 primei aparitii in vectorul de pozitii, facand redimensionare daca este necesar
 BC: teta(1)
 WC: teta(nr_poz), cand se aplica redimensionarea pe vectorul de pozitii
 TC = teta(nr_poz)
 */
void Colectie::adauga(TElem elem) {
    if (this->nr_poz == this->cp_elemente_pozitii) {
        redim_poz();
    }
    if (this->nr_dist == this->cp_elemente) {
        redim_elem();
    }
    int poz_index = -1;
    for (int j = 0; j < this->nr_dist; j++) {
        if (this->elemente_distincte[j] == elem) {
            poz_index = j;
            break;
        }
    }
    if (poz_index == -1) {
        this->elemente_distincte[this->nr_dist++] = elem;
        poz_index = this->nr_dist - 1;
    }
    this->pozitii_elemente[this->nr_poz++] = poz_index;
}

/*
 returneaza true daca s-a sters un element, altfel fals
 sterge prima aparitie din vectorul de pozitie, iar daca nu mai exista acea pozitie dupa stergere,
 se sterge si elementul din vectorul de elemente distincte
 BC: teta(1)
 WC: teta(nr_dist*nr_poz)
 TC = teta(nr_dist*nr_poz)
 */
bool Colectie::sterge(TElem elem) {
    if (this->cauta(elem)) {
        int index = -1;
        // Find the index of elem in elemente_distincte
        for(int i = 0; i < nr_dist; ++i) {
            if(this->elemente_distincte[i] == elem) {
                index = i;
                break;
            }
        }

        if(index != -1) {
            int ok = this->functie(index);

            // Remove elem from pozitii_elemente
            for(int i = ok; i < nr_poz - 1; ++i) {
                this->pozitii_elemente[i] = this->pozitii_elemente[i + 1];
            }
            this->nr_poz--;

            // Check if elem still exists in pozitii_elemente
            bool still = false;
            for(int i = 0; i < nr_poz; ++i) {
                if(this->pozitii_elemente[i] == index) {
                    still = true;
                    break;
                }
            }

            // If elem does not exist in pozitii_elemente, remove it from elemente_distincte
            if(!still) {
                for(int i = index; i < nr_dist - 1; ++i) {
                    this->elemente_distincte[i] = this->elemente_distincte[i + 1];
                }
                this->nr_dist--;
                for(int i = 0;i<=this->nr_poz;i++)
                    if(index<this->pozitii_elemente[i]) this->pozitii_elemente[i]--;
            }
            return true;
        }
    }
    return false;
}


/*
 returneaza true daca gaseste un element in vectorul de elemente distincte, altfel fals
 BC: teta(1)
 WC: teta(nr_dist)
 TC = teta(nr_dist)
 */
bool Colectie::cauta(TElem elem) const {
    int i;
    for (i = 0; i < this->nr_dist; i++)
        if (this->elemente_distincte[i] == elem) return true;
    return false;
}

/*
 returneaza nr de aparitii al elementului selectat din vectorul de elemente distincte
 prin numararea indicilor elementului din vectorul de pozitii
 BC: teta(1)
 WC: teta(nr_poz)
 TC = teta(nr_poz)
 */
int Colectie::nrAparitii(TElem elem) const {
    int i, nr = 0;
    for (i = 0; i < this->nr_poz; i++)
        if (elem == this->elemente_distincte[this->pozitii_elemente[i]]) nr++;
    return nr;
}

/*
 returneaza nr elementelor din colectie
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
int Colectie::dim() const {
    return this->nr_poz;
}

/*
 returneaza true daca inca exista cel putin un element in colectie, altfel fals
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
bool Colectie::vida() const {
    if (this->nr_poz == 0) return true;
    return false;
}

/*
 returneaza o colectie
 BC: teta(1)
 WC: teta(1)
 TC = teta(1)
 */
IteratorColectie Colectie::iterator() const {
    return IteratorColectie(*this);
}


Colectie::~Colectie() {
    delete[] elemente_distincte;
    delete[] pozitii_elemente;
}

/*
 returneaza indicele din vectorul de pozitii unde se gaseste indicele elementului
 din vectorul de elemente distincte, altfel -1
 BC: teta(1)
 WC: teta(nr_poz)
 TC = teta(nr_poz)
 */
int Colectie::functie(int index) {
    for(int i = 0;i < this->nr_poz; ++i){
        if(this->pozitii_elemente[i] == index){
            return i;
        }
    }
    return -1;
}

/*
    parcurge vectorul de elemente distincte presupunand ca acestea se afla in colectie
    si face diferenta dintre cel mai mare element si cel mai mic element
    BC: teta(nr_dist)
    WC: teta(nr_dist)
    TC: teta(nr_dist)

    Pseudocod:
    subalgoritm diferenta(Colectie)
        i, min, max: intreg
        min <- Colectie.elemente_distincte[0]
        max <- Colectie.elemente_distincte[0]
        daca vida() = false atunci
            pentru i <- 1,Colectie.nr_dist executa
                daca Colectie.elemente_distincte[i] > max atunci
                    max <- Colectie.elemente_distincte[i]
                sf daca
                daca Colectie.elemente_distincte[i] < min atunci
                    min <- Colectie.elemente_distincte[i]
                sf daca
            sf pentru
            returneaza max-min
        sf daca
        returneaza -1
    sf subalgoritm
 */
int Colectie::diferenta() const
{
    int i, min, max;
    min = max = this->elemente_distincte[0];
    if(!vida()) {
        for (i = 1; i < this->nr_dist; i++) {
            if (this->elemente_distincte[i] > max) max = this->elemente_distincte[i];
            if (this->elemente_distincte[i] < min) min = this->elemente_distincte[i];
        }
        return max-min;
    }
    return -1;
}



