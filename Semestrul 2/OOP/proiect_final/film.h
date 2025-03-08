//
// Created by Cosmin Secrier on 28.03.2024.
//

#pragma once
#include <string>
#include <iostream>
#include <algorithm>
#include <time.h>
using std::string;
using std::cout;
class Film{
private:
    string titlu;
    string gen;
    int an_aparitie;
    string actor;

public:
    /* Constructorul pentru film, cu parametrii:
     * titlu - titltul filmului, nu poate fi gol
     * gen - genul filmului, nu poate fi gol
     * an_aparitie - anul in care a aparut filmul, mai mare ca 0
     * actor_principal - actorul principal al filmului, nu poate fi gol
    */
    Film(const string& titlu, const string& gen, int an_aparitie, const string& actor) :titlu { titlu }, gen{ gen }, an_aparitie{ an_aparitie }, actor{ actor } {}
    //Constructorul default
    Film() = default;
    //Film(const Film& ot) = delete;
    //gettere
    string get_titlu() const {
        return titlu;
    }
    string get_gen() const {
        return gen;
    }
    int get_an_aparitie() const {
        return an_aparitie;
    }
    string get_actor() const {
        return actor;
    }
};