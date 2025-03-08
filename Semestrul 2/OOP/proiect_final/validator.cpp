//
// Created by Cosmin Secrier on 04.04.2024.
//

#include "validator.h"
#include "erori.h"

bool validString(string str){
    if (str.size() == 0)
        return false;
    for (int i = 0; i < str.size(); i++){
        if (!((str.at(i) >= 'a' && str.at(i) <= 'z') || (str.at(i) >= 'A' && str.at(i) <= 'Z') ||
        str.at(i) == ' '))
            return false;
    }
    return true;
}

void Validator::valideaza_film(string titlu, string gen, int an_aparitie, string actor) {
    string erori;

    if (!validString(titlu))
        erori.append("Titlul este invalid! \n");
    if (!validString(gen))
        erori.append("Genul filmului este invalid! \n");
    if (!validString(actor))
        erori.append("Numele actorului este invalid! \n");
    if (an_aparitie <= 0)
        erori.append("Anul aparitiei este invalid! \n");

    if (erori.size() > 0)
        throw Exception(erori);
}