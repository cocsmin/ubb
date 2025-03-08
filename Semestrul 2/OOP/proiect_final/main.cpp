//
// Created by Cosmin Secrier on 28.03.2024.
//
#include "filmservice.h"
#include "ui.h"
#include "teste.h"
#include "validator.h"
#include <QtWidgets/QApplication>
#include "filmGUI.h"

int randuri = 10000;
int coloane = 100;

int main(int argc, char *argv[]){
    QApplication a(argc, argv);
    string numefisier = "/Users/cosmin/CLionProjects/oop_lab7/filme.txt";
    double probabilitate;
    //cin >> probabilitate;
    //FilmRepoFile repo(numefisier);
    //FilmRepoLab repo;
    //repo.set_probabilitate(probabilitate);
    FilmRepo repo;
    Validator valid;
    CosFilme cos(repo);
    FilmService service{repo, valid, cos};
   // UI ui{service};
    TestAll();
    //ui.start();
    filmGUI gui {service};
    gui.show();
    return a.exec();
}
