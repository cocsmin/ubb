//
// Created by Cosmin Secrier on 26.05.2024.
//

#include <QWidget>
#include <QTimer>
#include <QHBoxLayout>
#include <QPushButton>
#include <QListWidget>
#include <QTableWidget>
#include <QString>
#include <QLabel>
#include <vector>
#include "cosfilme.h"
#include "observator.h"

class CosGUILista:public QWidget, public Observator{
private:
    CosFilme& cos;
    QListWidget* lista;
    QPushButton* btn;
    QPushButton* btn_random;
    void loadLista(const vector<Film>& filme){
        lista->clear();
        for (auto& f : filme){
            auto item = new QListWidgetItem(QString::fromStdString(f.get_titlu() + " "
                    + f.get_gen() + " " + std::to_string(f.get_an_aparitie()) + " " + f.get_actor()));
            lista->addItem(item);
        }
    }
    void initializare(){
        QVBoxLayout* ly = new QVBoxLayout;
        lista = new QListWidget;
        ly->addWidget(lista);
        btn = new QPushButton("Clear cos");
        ly->addWidget(btn);

        btn_random = new QPushButton("Genereaza 3 filme");
        ly->addWidget(btn_random);
        this->setLayout(ly);
    }

    void conectare(){
        cos.adauga_obs(this);
        QObject::connect(btn, &QPushButton::clicked, [&](){
            cos.sterge_cos();
            loadLista(cos.getAllCos());
        });
        QObject::connect(btn_random, &QPushButton::clicked, [&](){
           cos.baga_mare(3);
            loadLista(cos.getAllCos());
        });
    }
public:
    CosGUILista(CosFilme& cos) : cos{ cos }{
        initializare();
        conectare();
        loadLista(cos.getAllCos());
    }
    void update() override{
        loadLista(cos.getAllCos());
    }

    ~CosGUILista(){
        cos.sterge_obs(this);
    }

};
