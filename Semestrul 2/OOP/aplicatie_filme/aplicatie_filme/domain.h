#pragma once
#include <string>
#include <iostream>

using std::string;
using std::cout;


class Film
{
private:
    string titlu;
    string gen;
    string actor;
    int an;
    static int contor;
public:
    Film(const string& titlu, const string& gen, const string& actor, int an) :titlu{ titlu }, gen{ gen }, actor{ actor }, an{ an }
    {
        contor++;
    }
    Film(const Film& f) :titlu{ f.titlu }, gen{ f.gen }, actor{ f.actor }, an{ f.an } 
    {
        //cout << "constr copie reusita\n";
        contor++;
    }
    Film() 
    {
        contor++;
    }
    ~Film()
    {
        contor--;
    }
    string get_titlu() const;
    string get_gen() const;
    string get_actor() const;
    int get_an() const;
    static int get_contor() { return contor; }
};
