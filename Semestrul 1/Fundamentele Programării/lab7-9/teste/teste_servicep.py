from service.servicep import ServiceP
from domain.validatorpersoane import ValidatorP

def test_servicep():
    validatorp = ValidatorP()
    servicep = ServiceP(validatorp)
    servicep.store_persoana(1, 'Andrei', 'Str. Mihai Viteazu')
    servicep.store_persoana(2, 'Mihai', 'Str. Mihai Eminescu')
    assert len(servicep.get_persoane()) == 2
    servicep.delete_persoana(1)
    assert len(servicep.get_persoane()) == 1
    servicep.store_persoana(2, 'Mihai', 'Str. Mihai Viteazu')
    assert servicep.findpersoana(2).get_adresa() == 'Str. Mihai Viteazu'
    assert servicep.findpersoana(2).get_nume() == 'Mihai'
    assert servicep.findpersoana(2).get_id() == 2
    assert str(servicep.findpersoana(2)) == '2 Mihai Str. Mihai Viteazu'
    servicep.delete_persoana(2)
    assert len(servicep.get_persoane()) == 0

test_servicep()