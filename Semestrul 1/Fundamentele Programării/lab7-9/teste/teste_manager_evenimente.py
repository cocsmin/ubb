from domain.validatorevenimente import ValidatorE
from repository.manager_evenimente import RepositoryEvenimente
def test_managere():
    validatore = ValidatorE()
    managere = RepositoryEvenimente(validatore)
    managere.adauga_eveniment(1, 1, 1, 2023, 1, 'Nunta')
    managere.adauga_eveniment(2, 2, 2, 2024, 2, 'Botez')
    assert len(managere.get_lista_evenimente()) == 2
    managere.sterge_eveniment(1)
    assert len(managere.get_lista_evenimente()) == 1
    managere.modifica_eveniment(2, 3, 3, 2025, 3, 'Cununie')
    assert managere.get_lista_evenimente()[0].get_id() == 2
    assert managere.get_lista_evenimente()[0].get_zi() == 3
    assert managere.get_lista_evenimente()[0].get_luna() == 3
    assert managere.get_lista_evenimente()[0].get_an() == 2025
    assert managere.get_lista_evenimente()[0].get_timp() == 3
    assert managere.get_lista_evenimente()[0].get_descriere() == 'Cununie'
    managere.sterge_eveniment(2)
    assert len(managere.get_lista_evenimente()) == 0

test_managere()