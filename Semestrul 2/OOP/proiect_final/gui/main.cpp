#include "gui.h"
#include "../filmservice.h"
#include "../teste.h"
#include <QApplication>
#include <QLineEdit>
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    QLineEdit w("Introdu text");
    //FilmRepoLab repo;
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmService service{repo, valid, cos};
    //UI ui{service};
    //TestAll();
    //ui.start();
    w.show();
    return a.exec();
}
