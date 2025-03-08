//
// Created by Cosmin Secrier on 20.04.2024.
//
#include "film.h"
class ActiuneUndo {
public:
    virtual void doUndo() = 0;

    virtual ~ActiuneUndo() {};
};

class UndoAdauga : public ActiuneUndo {
    Film filmAdaugat;
    RepoAbstract& Rep;

public:
    UndoAdauga(RepoAbstract& Rep, const Film& f) : Rep{ Rep }, filmAdaugat{ f } {}

    void doUndo() override {
        Rep.sterge(filmAdaugat);
    }
};

class UndoModifica : public ActiuneUndo {
    Film filmNou, filmVechi;
    RepoAbstract& Rep;

public:
    UndoModifica(RepoAbstract& Rep, Film& filmVechi, Film& filmNou) : Rep{ Rep }, filmVechi{ filmVechi }, filmNou{ filmNou } {}

    void doUndo() override {
        Rep.sterge(filmNou);
        Rep.adauga(filmVechi);
    }
};

class UndoSterge : public ActiuneUndo {
    Film filmSters;
    RepoAbstract& Rep;
public:
    UndoSterge(RepoAbstract& Rep, Film& filmSters) : Rep{ Rep }, filmSters{ filmSters } {}

    void doUndo() override {
        Rep.adauga(filmSters);
    }
};