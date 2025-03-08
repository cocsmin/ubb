//
// Created by Cosmin Secrier on 10.05.2024.
//

#include "filmGUI.h"


void filmGUI::initializareGUI() {

    QHBoxLayout* lymain = new QHBoxLayout;
    this->setLayout(lymain);

    QWidget* stanga = new QWidget;
    QVBoxLayout* lyst = new QVBoxLayout;
    stanga->setLayout(lyst);

    QWidget* form = new QWidget;
    QFormLayout* lyform = new QFormLayout;
    form->setLayout(lyform);
    edit_titlu = new QLineEdit;
    edit_gen = new QLineEdit;
    edit_an = new QLineEdit;
    edit_actor = new QLineEdit;

    lyform->addRow(lbl_titlu, edit_titlu);
    lyform->addRow(lbl_gen,edit_gen);
    lyform->addRow(lbl_an,edit_an);
    lyform->addRow(lbl_actor, edit_actor);

    btn_adauga = new QPushButton("Adauga film");
    btn_modif = new QPushButton("Modifica film");
    btn_sterge = new QPushButton("Sterge film");
    lyform->addWidget(btn_adauga);
    lyform->addWidget(btn_modif);
    lyform->addWidget(btn_sterge);
    lyst->addWidget(form);


    QVBoxLayout* lyRadioBox = new QVBoxLayout;
    this->group_box->setLayout(lyRadioBox);
    lyRadioBox->addWidget(radio_sort_titlu);
    lyRadioBox->addWidget(radio_sort_actor);
    lyRadioBox->addWidget(radio_sort_angen);

    btn_sortare = new QPushButton("Sorteaza filme");
    lyRadioBox->addWidget(btn_sortare);
    lyst->addWidget(group_box);
    QWidget* form_filtru = new QWidget;
    QFormLayout* lyform_filtru = new QFormLayout;
    form_filtru->setLayout(lyform_filtru);

    titlu_filtru = new QLineEdit();
    lyform_filtru->addRow(lbl_filtru1, titlu_filtru);
    btn_filtru1 = new QPushButton("Filtreaza filmele dupa titlu");
    lyform_filtru->addWidget(btn_filtru1);

    an_filtru = new QLineEdit();
    lyform_filtru->addRow(lbl_filtru2, an_filtru);
    btn_filtru2 = new QPushButton("Filtreaza filmele dupa an");
    lyform_filtru->addWidget(btn_filtru2);

    lyst->addWidget(form_filtru);

    btn_reload = new QPushButton("Reload data");
    lyst->addWidget(btn_reload);

    btn_undo = new QPushButton("Undo");
    lyst->addWidget(btn_undo);


    btn_cos = new QPushButton("Watchlist");
    btn_adaugacos = new QPushButton("Adauga film in watchlist");
    titlucos = new QLineEdit;
    btn_adaugarandom = new QPushButton("Adauga filme random in watchlist");
    nrrandom = new QLineEdit;
    btn_golestecos = new QPushButton("Goleste watchlist");
    edit_export = new QLineEdit;
    this->listacos = new QListWidget();
    export_cos = new QPushButton("Exporta watchlist");

    lyst->addWidget(btn_cos);

    btnCosCRUDGUI = new QPushButton("CosCRUDGUI");
    lyst->addWidget(btnCosCRUDGUI);

    btnCosReadOnlyGUI = new QPushButton("CosReadOnlyGUI");
    lyst->addWidget(btnCosReadOnlyGUI);


    QWidget* dreapta = new QWidget;
    QVBoxLayout* lydr = new QVBoxLayout;
    dreapta->setLayout(lydr);
    /*
    int linii = 10;
    int coloane = 4;
    this->tabel = new QTableWidget{ linii, coloane };

    QStringList header;
    header << "Titlu" << "Gen" << "An aparitie" << "Actor principal";
    this->tabel->setHorizontalHeaderLabels(header);
    this->tabel->horizontalHeader()->setSectionResizeMode(QHeaderView::ResizeToContents);

    */
    btn_sf = new QPushButton("SF");
    btn_actiune = new QPushButton("Actiune");
    btn_comedie = new QPushButton("Comedie");
    btn_altele = new QPushButton("Altele");
    btn_raport = new QPushButton("Raport");

    lydr->addWidget(btn_raport);
    lydr->addWidget(tblV);
    lydr->addWidget(btn_cos);

    btnaddcos = new QPushButton("Adauga film cos");
    btndeletecos = new QPushButton("Sterge film cos");
    btnrandomcos = new QPushButton("Adauga filme random in cos");
    lydr->addWidget(btnaddcos);
    lydr->addWidget(btndeletecos);
    lydr->addWidget(nrrandom);
    lydr->addWidget(btnrandomcos);

    lymain->addWidget(stanga);
    lymain->addWidget(dreapta);
}

void filmGUI::conectare() {
    QObject::connect(btn_adauga, &QPushButton::clicked, this, &filmGUI::adauga_gui);
    QObject::connect(btn_modif, &QPushButton::clicked, this, &filmGUI::modifica_gui);
    QObject::connect(btn_sterge, &QPushButton::clicked, this, &filmGUI::sterge_gui);

    QObject::connect(btn_undo, &QPushButton::clicked, [&]() {
        try {
            this->service.undo();
            this->reloadFilme(this->service.get_filme());
            QMessageBox::information(this, "Info", QString::fromStdString("Undo realizat cu succes"));
        }
        catch (Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_mesaj()));
        }
        catch (ExceptieRepo& r){
            QMessageBox::warning(this, "Warning", QString::fromStdString(r.get_mesaj()));
        }
    });

    QObject::connect(btn_sortare, &QPushButton::clicked, [&]() {
        if (this->radio_sort_titlu->isChecked())
            this->reloadFilme(service.sortare(1));
        else if (this->radio_sort_actor->isChecked())
            this->reloadFilme(service.sortare(2));
        else if (this->radio_sort_angen->isChecked())
            this->reloadFilme(service.sortare(3));
    });

    QObject::connect(btn_filtru1, &QPushButton::clicked, [&](){
        string fil = this->titlu_filtru->text().toStdString();
        this->reloadFilme(service.filtrareTitlu(fil));
        titlu_filtru->clear();
    });

    QObject::connect(btn_filtru2, &QPushButton::clicked, [&](){
        int fil = this->an_filtru->text().toInt();
        this->reloadFilme(service.filtrareAnAparitie((fil)));
        an_filtru->clear();
    });

    QObject::connect(btn_reload, &QPushButton::clicked, [&](){
        this->reloadFilme(service.get_filme());
    });
    QObject::connect(tblV->selectionModel(), &QItemSelectionModel::selectionChanged,[&](){
        if (tblV->selectionModel()->selectedIndexes().isEmpty()) {
            edit_titlu->setText("");
            edit_gen->setText("");
            edit_an->setText("");
            edit_actor->setText("");
            return;
        }
        int selRow = tblV->selectionModel()->selectedIndexes().at(0).row();
        auto cel0Index = tblV->model()->index(selRow, 0);
        auto cel0Value = tblV->model()->data(cel0Index, Qt::DisplayRole).toString();
        edit_titlu->setText(cel0Value);
        auto cel1Index = tblV->model()->index(selRow, 1);
        auto cel1Value = tblV->model()->data(cel1Index, Qt::DisplayRole).toString();
        edit_gen->setText(cel1Value);
        auto cel2Index = tblV->model()->index(selRow, 2);
        auto cel2Value = tblV->model()->data(cel2Index, Qt::DisplayRole).toString();
        edit_an->setText(cel2Value);
        auto cel3Index = tblV->model()->index(selRow, 3);
        auto cel3Value = tblV->model()->data(cel3Index, Qt::DisplayRole).toString();
        edit_actor->setText(cel3Value);
    });


    QObject::connect(btn_cos, &QPushButton::clicked, [&](){
        QWidget* fereastra_cos = new QWidget;
        QFormLayout* lycos = new QFormLayout;
        fereastra_cos->setLayout(lycos);
        titlucos = new QLineEdit;
        lycos->addRow("Titlu film: ", titlucos);
        lycos->addWidget(btn_adaugacos);
        lycos->addRow(lbl_random, nrrandom);
        lycos->addWidget(btn_adaugarandom);
        lycos->addWidget(btn_golestecos);
        edit_export = new QLineEdit;
        lycos->addRow(lbl_export, edit_export);
        lycos->addWidget(export_cos);
        lycos->addWidget(listacos);
        fereastra_cos->show();
    });

    QObject::connect(btn_adaugacos, &QPushButton::clicked, [&]() {
        try {
            string titlu = titlucos->text().toStdString();
            titlucos->clear();
            this->service.adauga_cos(titlu);
            this->reloadCos(this->service.getAllCos());
            QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat cu succes."));
        }
        catch (ExceptieRepo& r) {
            QMessageBox::warning(this, "Warning", "Filmul nu exista!");
        }
    });
    QObject::connect(btnaddcos, &QPushButton::clicked, [&]() {
        try {
            string titlu = edit_titlu->text().toStdString();
            edit_titlu->clear();
            this->service.adauga_cos(titlu);
            this->reloadCos(this->service.getAllCos());
            QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat cu succes."));

        }
        catch (ExceptieRepo& r) {
            QMessageBox::warning(this, "Warning", "Filmul nu exista!");
        }
    });

    QObject::connect(btn_adaugarandom, &QPushButton::clicked, [&]() {
        try {
            int nr = nrrandom->text().toInt();
            nrrandom->clear();
            this->service.adauga_random(nr);
            this->reloadCos(this->service.getAllCos());
        }
        catch (ExceptieRepo& r){
            QMessageBox::warning(this, "Warning", "Filmul nu exista");
        }
    });
    QObject::connect(btnrandomcos, &QPushButton::clicked, [&]() {
        try {
            int nr = nrrandom->text().toInt();
            nrrandom->clear();
            this->service.adauga_random(nr);
            this->reloadCos(this->service.getAllCos());
        }
        catch (ExceptieRepo& r){
            QMessageBox::warning(this, "Warning", "Filmul nu exista");
        }
    });

    QObject::connect(btn_raport, &QPushButton::clicked, [&](){
            try{
                std::unordered_map<string, DTO> raport = service.frecvente_gen();
                reloadRaport(raport);
                rpt->show();
            }
            catch (Exception&){

            }
    });

    QObject::connect(btn_golestecos, &QPushButton::clicked, [&](){
        try {
            this->service.sterge_cos();
            this->reloadCos(this->service.getAllCos());
        }
        catch (ExceptieRepo& r){
            QMessageBox::warning(this, "Warning", QString::fromStdString(r.get_mesaj()));
        }
    });

    QObject::connect(btndeletecos, &QPushButton::clicked, [&](){
       try{
           string titlu = edit_titlu->text().toStdString();
           edit_titlu->clear();
           this->service.sterge_filmcos(titlu);
           QMessageBox::information(this, "Info", QString::fromStdString("Film sters din cos cu succes."));
           this->reloadCos(this->service.getAllCos());
       }
       catch (ExceptieRepo& r){
           QMessageBox::warning(this, "Warning", QString::fromStdString(r.get_mesaj()));
       }
    });

    QObject::connect(export_cos, &QPushButton::clicked, [&]() {
        try {
            string fisier = edit_export->text().toStdString();
            edit_export->clear();
            this->service.export_cos(fisier);
            listacos->clear();
            this->service.sterge_cos();
            this->reloadCos(this->service.getAllCos());
        }
        catch (Exception& e){
            QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_mesaj()));
        }
    });

    QObject::connect(btnCosCRUDGUI, &QPushButton::clicked, [&](){
       auto fereastraCos = new CosGUILista{ service.getCos() };
       fereastraCos->show();
    });

    QObject::connect(btnCosReadOnlyGUI, &QPushButton::clicked, [&](){
       auto fereastraFiguri = new Histograma{ service.getCos() };
       fereastraFiguri->show();
    });
}

void filmGUI::reloadFilme(vector<Film> filme) {
    /*
    this->tabel->clearContents();
    this->tabel->setRowCount(int(filme.size()));

    int linie = 0;
    for (auto& film : filme){
        this->tabel->setItem(linie, 0, new QTableWidgetItem(QString::fromStdString(film.get_titlu())));
        this->tabel->setItem(linie, 1, new QTableWidgetItem(QString::fromStdString(film.get_gen())));
        this->tabel->setItem(linie, 2, new QTableWidgetItem(QString::number(film.get_an_aparitie())));
        this->tabel->setItem(linie, 3, new QTableWidgetItem(QString::fromStdString(film.get_actor())));
        linie++;
    }
    */
    modelTabel->setFilme(filme);
}

void filmGUI::reloadCos(vector<Film> filme) {
    this->listacos->clear();
    for (const auto& elem : filme){
        auto item = new QListWidgetItem(QString::fromStdString(elem.get_titlu() + " " + elem.get_gen()
                + " " + std::to_string(elem.get_an_aparitie()) + " " + elem.get_actor()));
        this->listacos->addItem(item);
    }
}

void filmGUI::adauga_gui() {
    try {
        string titlu = edit_titlu->text().toStdString();
        string gen = edit_gen->text().toStdString();
        int an = edit_an->text().toInt();
        string actor = edit_actor->text().toStdString();

        edit_titlu->clear();
        edit_gen->clear();
        edit_an->clear();
        edit_actor->clear();

        this->service.adauga(titlu, gen, an, actor);
        this->reloadFilme(this->service.get_filme());

        QMessageBox::information(this, "Info", QString::fromStdString("Film adaugat cu succes"));
    }
    catch (ExceptieRepo& r){
        QMessageBox::warning(this, "Warning", QString::fromStdString(r.get_mesaj()));
    }
    catch (Exception& e){
        QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_mesaj()));
    }
}

void filmGUI::modifica_gui() {
    try {
        string titlu = edit_titlu->text().toStdString();
        string gen = edit_gen->text().toStdString();
        int an = edit_an->text().toInt();
        string actor = edit_actor->text().toStdString();

        edit_titlu->clear();
        edit_gen->clear();
        edit_an->clear();
        edit_actor->clear();

        this->service.modifica(titlu, gen, an, actor);
        this->reloadFilme(this->service.get_filme());

        QMessageBox::information(this,"Info", QString::fromStdString("Film modificat cu succes"));
    }
    catch (ExceptieRepo& e){
        QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_mesaj()));
    }
    catch (Exception& r){
        QMessageBox::warning(this, "Warning", QString::fromStdString(r.get_mesaj()));
    }
}

void filmGUI::reloadRaport(std::unordered_map<string, DTO> raport) {
    rpt->clear();

    for (auto& film : raport){
        rpt->addItem(QString::fromStdString(film.first) + " " + QString::number(film.second.getCount()));;
    }
}

void filmGUI::sterge_gui() {
    try {
        string titlu = edit_titlu->text().toStdString();
        string gen = edit_gen->text().toStdString();
        int an = edit_an->text().toInt();
        string actor = edit_actor->text().toStdString();

        edit_titlu->clear();
        edit_gen->clear();
        edit_an->clear();
        edit_actor->clear();

        this->service.sterge(titlu);
        this->reloadFilme(this->service.get_filme());

        QMessageBox::information(this, "Info", QString::fromStdString("Film sters cu succes"));
    }
    catch (ExceptieRepo& e){
        QMessageBox::warning(this, "Warning", QString::fromStdString(e.get_mesaj()));
    }
    catch (Exception& r){
        QMessageBox::warning(this, "Warning", QString::fromStdString(r.get_mesaj()));
    }
}