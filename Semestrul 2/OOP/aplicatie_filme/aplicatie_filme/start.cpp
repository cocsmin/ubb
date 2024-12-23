#include "ui.h"
#include "service.h"
#include "domain.h"
#include "validator.h"
#include "teste.h"
#include "vector.h"

int main()
{
	teste();
	FilmRepo repo;
	Validator valid;
	FilmServ serv(repo, valid);
	Ui ui(serv);
	ui.run();
	//cout << Film::get_contor();
}