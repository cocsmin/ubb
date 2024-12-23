from service.servicee import ServiceE
from domain.validatorevenimente import ValidatorE


def test_servicee():
    validatore = ValidatorE()
    servicee = ServiceE(validatore)
    servicee.store_eveniment(1, 1, 1, 2023, 1, 'Nunta')
    servicee.store_eveniment(2, 2, 2, 2024, 2, 'Botez')
    assert len(servicee.get_evenimente()) == 2
    servicee.delete_eveniment(1)
    assert len(servicee.get_evenimente()) == 1
    servicee.store_eveniment(2, 3, 3, 2025, 3, 'Cununie')
    assert servicee.findeveniment(2).get_id() == 2
    assert servicee.findeveniment(2).get_zi() == 3
    assert servicee.get_evenimente()[0].get_id() == 2
    assert servicee.get_evenimente()[0].get_zi() == 3
    assert servicee.get_evenimente()[0].get_luna() == 3
    assert servicee.get_evenimente()[0].get_an() == 2025
    assert servicee.get_evenimente()[0].get_timp() == 3
    assert servicee.get_evenimente()[0].get_descriere() == 'Cununie'
    servicee.delete_eveniment(2)
    assert len(servicee.get_evenimente()) == 0

    

test_servicee()