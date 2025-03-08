//
// Created by Cosmin Secrier on 28.03.2024.
//

#pragma once
#include "filmservice.h"
#include <iostream>
using std::cout;
class UI{
    FilmService& service;
public:
    explicit UI(FilmService& service) : service{ service } {

    }
    static void print_menu(){
        cout<<"Alegeti una din urmatoarele optiuni: \n";
        cout<<"1)Adaugare film\n";
        cout<<"2)Stergere film\n";
        cout<<"3)Modificare film\n";
        cout<<"4)Cautare film\n";
        cout<<"5)Filtrare filme dupa titlu\n";
        cout<<"6)Filtrare filme dupa anul aparitiei \n";
        cout<<"7)Sortare filme dupa titlu\n";
        cout<<"8)Sortare filme dupa actorul principal\n";
        cout<<"9)Sortare filme dupa anul aparitiei + gen\n";
        cout<<"10)Afisare lista filme\n";
        cout<<"15)Raport de filme pe baza genului \n";
        cout<<"50)Adauga in cos \n";
        cout<<"51)Adauga random in cos \n";
        cout<<"52)Goleste cos \n";
        cout<<"53)Export cos \n";
        cout<<"55) UNDO \n";
        cout<<"0)Iesire\n";
    }

    static void afisare(const vector<Film>& filme);
    static void PrintFilm(const Film& film);
    void start();
};