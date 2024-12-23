from domain.validatorpersoane import ValidatorP
from repository.manager_persoane import RepositoryPersoane
def test_managerp():
    validatorp = ValidatorP()
    managerp = RepositoryPersoane(validatorp)
    managerp.adauga_persoana(1, 'Andrei', 'Str. Mihai Viteazu')
    managerp.adauga_persoana(2, 'Mihai', 'Str. Mihai Eminescu')
    assert len(managerp.get_lista_persoane()) == 2
    managerp.sterge_persoana(1)
    assert len(managerp.get_lista_persoane()) == 1
    managerp.modifica_persoana(2, 'Mihai', 'Str. Mihai Viteazu')
    assert managerp.get_lista_persoane()[0].get_adresa() == 'Str. Mihai Viteazu'
    assert managerp.get_lista_persoane()[0].get_nume() == 'Mihai'
    assert managerp.get_lista_persoane()[0].get_id() == 2
    assert str(managerp.get_lista_persoane()[0]) == '2 Mihai Str. Mihai Viteazu'
    managerp.sterge_persoana(2)
    assert len(managerp.get_lista_persoane()) == 0

test_managerp()