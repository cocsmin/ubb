#include <iostream>

#include "ui.h"
#include "domain.h"
#include "vector.h"
#include "validator.h"

using namespace std;

void Ui::meniu()
{
    cout << "Meniu:" << endl;
    cout << " Optiunea 1. Afisare lista de filme" << endl;
    cout << " Optiunea 2. Adaugare film in lista de filme" << endl;
    cout << " Optiunea 3. Stergere film din lista de filme" << endl;
    cout << " Optiunea 4. Modificare film din lista de filme" << endl;
    cout << " Optiunea 5. Cautare film in lista de filme" << endl;
    cout << " Optiunea 6. Filtrare lista de filme dupa titlu" << endl;
    cout << " Optiunea 7. Filtrare lista de filme dupa an" << endl;
    cout << " Optiunea 8. Sortare lista de filme dupa titlu" << endl;
    cout << " Optiunea 9. Sortare lista de filme dupa actor" << endl;
    cout << " Optiunea 10. Sortare lista de filme dupa an,iar apoi dupa gen" << endl;
    cout << " Optiunea 11. Iesire" << endl;
}

void Ui::adauga_default()
{
    string titlu, gen, actor;
    int an;
    titlu = "Pacea", gen = "actiune", actor = "Mihai", an = 2005;
    this->serv.adauga(titlu, gen, actor, an);
    titlu = "Marea", gen = "dragoste", actor = "Ana", an = 2000;
    this->serv.adauga(titlu, gen, actor, an);
    titlu = "Pruncul", gen = "dragoste", actor = "Carla", an = 2005;
    this->serv.adauga(titlu, gen, actor, an);
    titlu = "Natura", gen = "fictiune", actor = "Tudor", an = 2004;
    this->serv.adauga(titlu, gen, actor, an);
}

void Ui::adauga_ui() 
{
    string titlu, gen, actor;
    int an;
    cout << "Titlul ce doriti a fi adaugat este: ";
    getline(cin>>ws, titlu);
    cout << "Genul ce doriti a fi adaugat este: ";
    getline(cin>>ws, gen);
    cout << "Actorul ce doriti a fi adaugat este: ";
    getline(cin>>ws, actor);
    cout << "Anul ce doriti a fi adaugat este: ";
    cin >> an;
    try
    {
        serv.adauga(titlu, gen, actor, an);
    }
    catch (Exceptie exceptie)
    {
        cout << exceptie.get_mesaj();
    }
}

void Ui::sterge_ui()
{
    string titlu;
    cout << "Titlul ce doriti a fi sters este: ";
    cin >> titlu;
    try
    {
        serv.sterge(titlu);
    }
    catch (Exceptie exceptie)
    {
        cout << exceptie.get_mesaj();
    }
}

void Ui::modifica_ui()
{
    string titlu, gen, actor, titlu_vechi;
    int an;
    cout << "Titlul ce doriti a fi modificat este: ";
    cin >> titlu_vechi;
    cout << "Titlul nou este: ";
    cin >> titlu;
    cout << "Genul nou este: ";
    cin >> gen;
    cout << "Actorul nou este: ";
    cin >> actor;
    cout << "Anul nou este: ";
    cin >> an;
    try
    {
        serv.modifica(titlu_vechi, titlu, gen, actor, an);
    }
    catch (Exceptie exceptie)
    {
        cout << exceptie.get_mesaj();
    }
}

void Ui::afisare_ui(const VectorDinamic <Film>& filme)
{
    cout << "Filmele sunt:" << endl;
    for (const auto& f : filme) 
    {
        cout << "Titlul: " << f.get_titlu() << " | Genul: " << f.get_gen() << " | Actorul: " << f.get_actor() << " | Anul: " << f.get_an() << endl;
    }
}

void Ui::cauta_ui()
{
    string titlu;
    cout << "Titlul filmului ce doriti a fi cautat este: ";
    cin >> titlu;
    if (serv.cauta(titlu))
        cout << "Exista filmul in lista \n";
    else
        cout << "Nu exista filmul in lista\n";
}

void Ui::filtrare_titlu_ui()
{
    string titlu;
    cout << "Titlul dupa care doriti sa fie filtrata lista este: ";
    cin >> titlu;
    afisare_ui(serv.filtrare_titlu(titlu));
}

void Ui::filtrare_an_ui()
{
    int an;
    cout << "Anul dupa care doriti sa fie filtrata lista este: ";
    cin >> an;
    afisare_ui(serv.filtrare_an(an));
}

int sort_titlu(const Film& f1, const Film& f2) 
{
    return f1.get_titlu().compare(f2.get_titlu());
}

int sort_actor(const Film& f1, const Film& f2)
{
    return f1.get_actor().compare(f2.get_actor());
}

int sort_gen_an(const Film& f1, const Film& f2)
{
    if (f1.get_an() == f2.get_an())
    {
        return (f1.get_gen().compare(f2.get_gen()));
    }
    else {
        return f1.get_an() > f2.get_an();
    }
}

void Ui::sortare_titlu_ui()
{
    auto lista_sortata = serv.sortare(sort_titlu);
    afisare_ui(lista_sortata);
}

void Ui::sortare_actor_ui()
{
    auto lista_sortata = serv.sortare(sort_actor);
    afisare_ui(lista_sortata);
}

void Ui::sortare_an_gen_ui()
{
    auto lista_sortata = serv.sortare(sort_gen_an);
    afisare_ui(lista_sortata);
}


void Ui::run() 
{
    this->adauga_default();
    while (true) 
    {
        meniu();
        int optiune;
        cout << "Optiunea dorita este: ";
        cin >> optiune;
        try 
        {
            switch (optiune) 
            {
                case 1:
                    afisare_ui(serv.getAll());
                    break;
                case 2:
                    adauga_ui();
                    break;
                case 3:
                    sterge_ui();
                    break;
                case 4:
                    modifica_ui();
                    break;
                case 5:
                    cauta_ui();
                    break;
                case 6:
                    filtrare_titlu_ui();
                    break;
                case 7:
                    filtrare_an_ui();
                    break;
                case 8:
                    sortare_titlu_ui();
                    break;
                case 9:
                    sortare_actor_ui();
                    break;
                case 10:
                    sortare_an_gen_ui();
                    break;
                case 11:
                    return;
                default:
                    cout << "Aceasta optiune nu exista"<<endl;
            }
        }
        catch (ExceptieRepo& exceptie) 
        {
            cout << exceptie << endl;
        }
    }
}