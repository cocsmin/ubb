//
// Created by Cosmin Secrier on 10.05.2024.
//
#pragma once
#include <vector>
#include <string>
#include <QtWidgets/QApplication>
#include <QLabel>
#include <QPushButton>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>
#include <QLineEdit>
#include <QTableWidget>
#include <QMessageBox>
#include <QHeaderView>
#include <QGroupBox>
#include <QRadioButton>
#include <qlistwidget.h>
#include <QStringList>
#include "filmservice.h"
#include "CosCRUDGUI.h"
#include "CosReadOnlyGUI.h"
#include "modeltabel.h"
using std::vector;
using std::string;

class filmGUI:public QWidget {
private:
    FilmService& service;
    TableModel* modelTabel;
    //QListView* lstV = new QListView;
    QTableView* tblV = new QTableView;

    QLabel* lbl_titlu = new QLabel{ "Titlul filmului:" };
    QLabel* lbl_gen = new QLabel{ "Genul filmului:" };
    QLabel* lbl_an = new QLabel{ "Anul aparitiei filmului:" };
    QLabel* lbl_actor = new QLabel{ "Actorul principal al filmului:" };

    QLineEdit* edit_titlu;
    QLineEdit* edit_gen;
    QLineEdit* edit_an;
    QLineEdit* edit_actor;

    QListWidget* rpt = new QListWidget{};
    QPushButton* btn_raport;
    QPushButton* btn_adauga;
    QPushButton* btn_modif;
    QPushButton* btn_sterge;
    QPushButton* btn_sf;
    QPushButton* btn_comedie;
    QPushButton* btn_actiune;
    QPushButton* btn_altele;
    QGroupBox* group_box = new QGroupBox(tr("Tip sortare"));

    QRadioButton* radio_sort_titlu = new QRadioButton(QString::fromStdString("Titlu"));
    QRadioButton* radio_sort_actor = new QRadioButton(QString::fromStdString("Actor"));
    QRadioButton* radio_sort_angen = new QRadioButton(QString::fromStdString("An + Gen"));
    QPushButton* btn_sortare;

    QLabel* lbl_filtru1 = new QLabel{ "Titlul dupa care se filtreaza:" };
    QLineEdit* titlu_filtru;
    QPushButton* btn_filtru1;

    QLabel* lbl_filtru2 = new QLabel{ "Anul aparitiei dupa care se filtreaza:" };
    QLineEdit* an_filtru;
    QPushButton* btn_filtru2;

    QPushButton* btn_reload;

    QPushButton* btn_undo;

    QTableWidget* tabel;
    QListWidget* lista;

    QPushButton* btn_cos;
    QPushButton* btn_adaugacos;
    QLineEdit* titlucos;
    QPushButton* btn_adaugarandom;
    QLineEdit* nrrandom;
    QPushButton* btn_golestecos;
    QListWidget* listacos;
    QLineEdit* edit_export;
    QPushButton* export_cos;
    QLabel* lbl_random = new QLabel{ "Numar filme random:" };
    QLabel* lbl_export = new QLabel{ "Nume fisier:" };
    QLabel* lbl_titlucos = new QLabel{"Titlu film: "};

    QPushButton* btnCosCRUDGUI;
    QPushButton* btnCosReadOnlyGUI;

    CosGUILista* fereastraCos;
    Histograma* fereastraFiguri;

    QPushButton* btnaddcos;
    QPushButton* btndeletecos;
    QPushButton* btnrandomcos;

    void initializareGUI();
    void conectare();
    void reloadFilme(vector <Film> filme);
    void reloadCos(vector <Film> filme);

public:
    filmGUI(FilmService& service) : service{ service } {
        initializareGUI();
        modelTabel = new TableModel{service.get_filme() };
        tblV->setModel(modelTabel);
        conectare();
        //reloadFilme(service.get_filme());
    }
    void adauga_gui();
    void modifica_gui();
    void sterge_gui();
    void reloadRaport(std::unordered_map<string, DTO> raport);
};