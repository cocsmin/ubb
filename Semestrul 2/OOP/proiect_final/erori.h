//
// Created by Cosmin Secrier on 04.04.2024.
//

#pragma once
#include <string>

using namespace std;

class Exception{
    string msj;
public:
    Exception(string m) : msj { m } {}

    string get_mesaj(){
        return msj;
    }
};
