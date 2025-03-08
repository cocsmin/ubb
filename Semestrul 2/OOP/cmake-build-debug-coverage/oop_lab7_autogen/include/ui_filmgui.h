/********************************************************************************
** Form generated from reading UI file 'filmgui.ui'
**
** Created by: Qt User Interface Compiler version 6.7.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_FILMGUI_H
#define UI_FILMGUI_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_filmgui
{
public:

    void setupUi(QWidget *filmgui)
    {
        if (filmgui->objectName().isEmpty())
            filmgui->setObjectName("filmgui");
        filmgui->resize(400, 300);

        retranslateUi(filmgui);

        QMetaObject::connectSlotsByName(filmgui);
    } // setupUi

    void retranslateUi(QWidget *filmgui)
    {
        filmgui->setWindowTitle(QCoreApplication::translate("filmgui", "filmgui", nullptr));
    } // retranslateUi

};

namespace Ui {
    class filmgui: public Ui_filmgui {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_FILMGUI_H
