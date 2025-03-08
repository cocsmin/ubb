//
// Created by Cosmin Secrier on 28.03.2024.
//

#include "ui.h"
#include <iostream>
#include "erori.h"
using namespace std;
void UI :: PrintFilm(const Film& film) {
    cout<<film.get_titlu()<<" ce apartine genului "<<film.get_gen()
        <<" aparut in anul "<<film.get_an_aparitie()<<" cu actorul in rol principal " << film.get_actor() << "\n";
}

void UI :: afisare(const vector<Film> &filme){
    cout<<"Filmele disponibile sunt: \n";
    for (const auto& film : filme)
        PrintFilm(film);
    //for (int i = 0; i < filme.size(); i++)
    //PrintFilm(filme[i]);
    //std::for_each(filme.begin(), filme.end(), PrintFilm);
}

void UI:: start() {
    while (true) {
        int optiune;
        print_menu();
        cin >> optiune;
        try {
            switch (optiune) {
                case 1: {
                    string titlu;
                    string gen;
                    int an_aparitie;
                    string actor_principal;
                    cout << "Titlu: ";
                    getline(cin>>ws, titlu);
                    cout << "Gen: ";
                    getline(cin>>ws, gen);
                    cout << "Anul aparitiei: ";
                    cin >> an_aparitie;
                    cout << "Actorul principal: ";
                    getline(cin>>ws, actor_principal);
                    try {
                        service.adauga(titlu, gen, an_aparitie, actor_principal);
                        cout << "Film adaugat cu succes. \n";
                    }
                    catch (Exception& ex){
                        cout<<ex.get_mesaj();
                    }
                    break;
                }
                case 2: {
                    string titlu;
                    cout << "Introduceeti titlul filmului pe care doriti sa l stergeti: ";
                    getline(cin>>ws, titlu);
                    try {
                        service.sterge(titlu);
                        cout << "Filmul a fost sters cu succes! \n";
                    }
                    catch (Exception& ex){
                        cout<<ex.get_mesaj();
                    }
                    break;
                }
                case 3: {
                    string gen_nou, actor_nou;
                    int an_nou;
                    string titlu;
                    cout << "Introduceti titlul filmului pe care doriti sa l stergeti: ";
                    getline(cin>>ws, titlu);
                    cout << "Introduceti noul gen al filmului: ";
                    getline(cin>>ws, gen_nou);
                    cout << "Introduceti noul an al filmului: ";
                    cin >> an_nou;
                    cout << "Introdcueti noul actor principal al filmuiui: ";
                    getline(cin>>ws, actor_nou);
                    try {
                        service.modifica(titlu, gen_nou, an_nou, actor_nou);
                        cout << "Oferta a fost modificata cu succes! \n";
                    }
                    catch (Exception& ex){
                        cout<<ex.get_mesaj();
                    }
                    break;
                }
                case 4: {
                    string titlu;
                    cout << "Introduceti titlul filmului pe care doriti sa l cautati: ";
                    getline(cin>>ws, titlu);
                    if (service.cauta(titlu))
                        cout << "Filmul cu titlul dat exista in lista \n";
                    else
                        cout << "Filmul cu titlul dat nu exista in lista\n";
                    break;
                }
                case 5: {
                    string titlu;
                    cout << "Introduceti titlul dupa care doriti sa filtrati: ";
                    getline(cin>>ws, titlu);
                    afisare(service.filtrareTitlu(titlu));
                    break;
                }
                case 6: {
                    int an;
                    cout << "Introduceti anul dupa care doriti sa filtrati: ";
                    cin >> an;
                    afisare(service.filtrareAnAparitie(an));
                    break;
                }
                case 7:{
                    vector<Film> sortat;
                    sortat = service.sortare(1);
                    afisare(sortat);
                    break;
                }
                case 8:{
                    vector<Film> sortat;
                    sortat = service.sortare(2);
                    afisare(sortat);
                    break;
                }
                case 9: {
                    vector<Film> sortat;
                    sortat = service.sortare(3);
                    afisare(sortat);
                    break;
                }
                case 10:{
                    afisare(service.get_filme());
                    break;
                }

                case 15:{
                    cout << "-----RAPORT-----" << endl;
                    auto map = service.frecvente_gen();
                    for (const auto& fnr : map)
                        //cout << "Exista " << fnr.second << " filme apartinand genului " << fnr.first << endl;
                    break;
                }

                case 50:{
                    string titlu;
                    cout << "Introduceti titlul filmului pe care doriti sa l adaugati in cos \n";
                    getline(cin>>ws, titlu);
                    try{
                        service.adauga_cos(titlu);
                        cout << "Filmul s-a adaugat cu succes in cos! \n";
                    }catch (Exception& ex) {
                        cout << ex.get_mesaj() << endl;
                    }
                    break;
                }
                case 51:{
                    int numar;
                    cout << "Cate oferte random doriti in cos? \n";
                    cin >> numar;
                    if (numar < 0)
                        cout << "Numarul dat este invalid !" << endl;
                    else{
                        try{
                            int numar_filme = service.adauga_random(numar);
                            cin >> numar_filme;
                            cout << "S au adaugat " << numar_filme << " filme random in cos \n";
                        }catch (Exception& ex){
                            cout << ex.get_mesaj() << endl;
                        }
                    }
                    break;
                }
                case 52:{
                    service.sterge_cos();
                    cout << "S au sters toate filmele din cos! \n";
                    break;
                }

                case 53:{
                    string numefisier;
                    cout << "Introduceti numele fisierului in care doriti sa salvati cosul(.csv sau .html): \n";
                    cin >> numefisier;
                    string ref = "/Users/cosmin/CLionProjects/oop_lab7";
                    service.export_cos(ref.append(numefisier));
                    break;
                }

                case 55:{
                    try {
                        service.undo();
                        cout << "Undo s-a efectuat cu succes \n";
                    }catch (Exception ex) {
                        cout << ex.get_mesaj() << endl;
                    }
                    break;
                }
                case 0:{
                    return;
                }
                default:
                    cout << "Comanda invalida \n";
            }
        }
        catch (ExceptieRepo& e) {
            cout << e.get_mesaj();
        }
    }
}