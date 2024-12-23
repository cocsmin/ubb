#pragma once
#include <string>
using namespace std;

class Validator 
{
public:
	void validare(string titlu, string gen, string actor, int an);
};

class Exceptie
{
	string mesaj;
public:
	Exceptie(string m) : mesaj{ m } 
	{
	}

	string get_mesaj() {
		return mesaj;
	}
};