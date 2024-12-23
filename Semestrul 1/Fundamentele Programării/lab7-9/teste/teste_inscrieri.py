from service.inscrieri import Inscrieri
from service.servicep import ServiceP
from service.servicee import ServiceE
from domain.validatorpersoane import ValidatorP
from domain.validatorevenimente import ValidatorE
def test_inscrieri():
    validatorp = ValidatorP()
    validatore = ValidatorE()
    servicep = ServiceP(validatorp)
    servicee = ServiceE(validatore)
    inscrieri = Inscrieri(servicep, servicee)
    servicep.store_persoana(1, 'Andrei', 'Str. Muresului')
    servicep.store_persoana(2, 'Mihai', 'Str. Tulcea')
    servicee.store_eveniment(1, 17, 12, 2024, 2, 'Nunta')
    servicee.store_eveniment(2, 18, 12, 2024, 2, 'Botez')
    inscrieri.adauga_inscriere(1, 1)
    inscrieri.adauga_inscriere(1, 2)
    assert len(inscrieri.get_inscrieri()) == 2
    assert inscrieri.get_inscrieri()[0].get_id() == 1

test_inscrieri()