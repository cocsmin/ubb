//
// Created by Cosmin Secrier on 26.05.2024.
//

#ifndef OOP_LAB7_COSREADONLYGUI_H
#define OOP_LAB7_COSREADONLYGUI_H

#endif //OOP_LAB7_COSREADONLYGUI_H
#include <QWidget>
#include <QPainter>
#include "cosfilme.h"
#include "QDebug"

class Histograma : public QWidget, public Observator{

private:
    CosFilme& cos;

public:
    Histograma(CosFilme& cos) : cos { cos }{
        cos.adauga_obs(this);
    };

    void update() override{
        repaint();
    }

    void paintEvent(QPaintEvent* eveniment) override {
        QPainter p { this };
        srand(time(NULL));
        for (const auto& u : cos.getAllCos()) {
            int x = rand() % 300;
            int y = rand() % 300;
            qDebug() << x << " " << y << "\n";
            p.drawRect(x,y,20,u.get_an_aparitie() / 10);
            p.drawImage(x,y, QImage("/Users/cosmin/Downloads/336908403_545746501009628_4677563040883600996_n.jpg"));
        }
    }
};