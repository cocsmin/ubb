#include "domain.h"
#include <string>

int Film::contor = 0;

string Film::get_titlu() const
{
	return titlu;
}

string Film::get_gen() const
{
	return gen;
}

string Film::get_actor() const
{
	return actor;
}

int Film::get_an() const
{
	return an;
}
