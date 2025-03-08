//
// Created by Cosmin Secrier on 04.04.2024.
//

#pragma once
template <typename Element>
class IteratorVector;

template <typename Element>
class VectorDinamic{
public:
    VectorDinamic();

    VectorDinamic(const VectorDinamic& ot);

    ~VectorDinamic();
    VectorDinamic& operator=(const VectorDinamic& obiect);

    VectorDinamic(VectorDinamic&& obiect);
    VectorDinamic& operator=(VectorDinamic&& obiect);

    void push_back(const Element& element);
    Element& get(int pozitie) const noexcept;
    int size() const noexcept;
    void erase(int poz);


    friend class IteratorVector<Element>;
    IteratorVector<Element> begin() const;
    IteratorVector<Element> end() const;

private:
    int dimensiune;
    int capacitate;
    Element* elemente;

    void redim();

    void set(int pozitie, const Element &el);
};

template <typename Element>
VectorDinamic<Element>::VectorDinamic() :elemente{ new Element[10] }, capacitate{ 10 }, dimensiune{ 0 }{}

template <typename Element>
VectorDinamic<Element>::VectorDinamic(const VectorDinamic<Element>& obiect) {
    elemente = new Element[obiect.capacitate];
    for (int i = 0; i < obiect.dimensiune; i++)
        elemente[i] = obiect.elemente[i];

    dimensiune = obiect.dimensiune;
    capacitate = obiect.capacitate;
}
template <typename Element>
VectorDinamic<Element>& VectorDinamic<Element>::operator=(const VectorDinamic<Element> &obiect) {
    if (this == &obiect)
        return *this;
    delete[] elemente;
    elemente = new Element[obiect.capacitate];
    for (int i = 0;i < obiect.dimensiune; i++)
        elemente[i] = obiect.elemente[i];

    dimensiune = obiect.dimensiune;
    capacitate = obiect.capacitate;
    return *this;
}
template<typename Element>
VectorDinamic<Element>::VectorDinamic(VectorDinamic&& obiect) {
    elemente = obiect.elemente;
    dimensiune = obiect.dimensiune;
    capacitate = obiect.capacitate;

    obiect.elemente = nullptr;
    obiect.dimensiune = 0;
    obiect.capacitate = 0;
}

template<typename Element>
VectorDinamic<Element>& VectorDinamic<Element>::operator=(VectorDinamic&& obiect) {
    //if (this == &obiect)
        //return *this;
    delete[] elemente;

    elemente = obiect.elemente;
    dimensiune = obiect.dimensiune;
    capacitate = obiect.capacitate;

    obiect.elemente = nullptr;
    obiect.dimensiune = 0;
    obiect.capacitate = 0;
    return *this;
}

template<typename Element>
void VectorDinamic<Element>::push_back(const Element &element) {
    redim();
    elemente[dimensiune++] = element;
}

template<typename Element>
void VectorDinamic<Element>::erase(int poz) {
    if (poz < 0 || poz >= dimensiune)
        return;
    for (int i = poz; i < dimensiune - 1;i++)
       elemente[i] = elemente[i + 1];

    dimensiune--;
}

template<typename Element>
Element& VectorDinamic<Element>::get(int pozitie) const noexcept {
    return elemente[pozitie];
}

template<typename Element>
void VectorDinamic<Element>::set(int pozitie, const Element& el){
    elemente[pozitie] = el;
}

template<typename Element>
int VectorDinamic<Element>::size() const noexcept {
    return dimensiune;
}

template<typename Element>
void VectorDinamic<Element>::redim() {
    if (dimensiune < capacitate)
        return;
    capacitate *= 2;
    Element* aux = new Element[capacitate];
    for (int i = 0; i < dimensiune; i++)
        aux[i] = elemente[i];
    delete[] elemente;
    elemente = aux;
}

template<typename Element>
VectorDinamic<Element>::~VectorDinamic(){
    delete[] elemente;
}

template<typename Element>
IteratorVector<Element> VectorDinamic<Element>::begin() const {
    return IteratorVector<Element>(*this);
}

template<typename Element>
IteratorVector<Element> VectorDinamic<Element>::end() const {
    return IteratorVector<Element>(*this, dimensiune);
}


template<typename Element>
class IteratorVector{
    private:
        const VectorDinamic<Element>& vector;
        int pozitie = 0;




    public:
        IteratorVector(const VectorDinamic<Element>& vector) noexcept;
        IteratorVector(const VectorDinamic<Element>& vector, int pozitie) noexcept;
        Element& element() const;
        void urmator();
        bool valid()const;
        Element& operator*();
        IteratorVector& operator++();
        bool operator==(const IteratorVector& obiect) noexcept;
        bool operator!=(const IteratorVector& obiect) noexcept;
    };


template<typename Element>
IteratorVector<Element>::IteratorVector(const VectorDinamic<Element> &vector) noexcept : vector { vector }{}
template<typename Element>
IteratorVector<Element>::IteratorVector(const VectorDinamic<Element> &vector, int pozitie) noexcept : vector { vector }, pozitie{ pozitie }{}


template<typename Element>
Element& IteratorVector <Element>::element() const {
    return vector.elemente[pozitie];
}

template<typename Element>
bool IteratorVector<Element>::valid() const {
    return pozitie < vector.dimensiune;
}

template<typename Element>
void IteratorVector<Element>::urmator() {
    pozitie++;
}

template<typename Element>
Element& IteratorVector<Element>::operator*() {
    return element();
}

template<typename Element>
IteratorVector<Element>& IteratorVector<Element>::operator++() {
    urmator();
    return *this;
}

template<typename Element>
bool IteratorVector<Element>::operator==(const IteratorVector &obiect) noexcept {
    return pozitie == obiect.pozitie;
}

template<typename Element>
bool IteratorVector<Element>::operator!=(const IteratorVector &obiect) noexcept {
    return !(*this == obiect);
}