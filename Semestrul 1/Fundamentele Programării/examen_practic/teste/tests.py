from domain.melodie import Melodie
from repository.repo import *
from service.serv import Service
from domain.validator import Validator
class Teste:
    def __init__(self):
        pass

    def teste_validator(self):
        val = Validator()
        melodie = Melodie(' ', 'Florin Salam', 'MANELE', '15.05.2012')
        try:
            val.valideaza(melodie)
            assert False

        except ValueError:
            assert True

    def teste_repository(self):
        melodie = Melodie('A.D.I.D.A.S.', 'Korn', 'Rock', '15.05.2012')
        test_repo = Repository()
        test_repo.adaugare_melodie(melodie)
        assert (len(test_repo.get_lista_melodii()) == 1)
        test_repo.modificare_melodie('A.D.I.D.A.S.', 'Korn', 'Jazz', '15.05.1997')
        assert (test_repo.get_lista_melodii()[0].get_gen() == 'Jazz')
        assert (test_repo.get_lista_melodii()[0].get_data() == '15.05.1997')
        


test = Teste()
test.teste_validator()
test.teste_repository()