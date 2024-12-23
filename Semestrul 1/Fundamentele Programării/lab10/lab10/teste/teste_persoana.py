from domain.persoana import Persoana
from domain.validatorpersoane import ValidatorP
def test_persoana():
    persoana = Persoana(1, 'Andrei', 'Str. Mihai Viteazu')
    assert persoana.get_id() == 1
    assert persoana.get_nume() == 'Andrei'
    assert persoana.get_adresa() == 'Str. Mihai Viteazu'
    persoana.set_nume('Mihai')
    persoana.set_adresa('Str. Mihai Eminescu')
    persoana.set_id(2)
    assert persoana.get_id() == 2
    assert persoana.get_nume() == 'Mihai'
    assert persoana.get_adresa() == 'Str. Mihai Eminescu'
    assert str(persoana) == '2 Mihai Str. Mihai Eminescu'

def test_validator_persoana():
    validatorp = ValidatorP()
    persoana = Persoana(1, 'Andrei', 'Str. Mihai Viteazu')
    validatorp.validare_persoana(persoana)
    persoana = Persoana(-1, 'Andrei', 'Str. Mihai Viteazu')
    try:
        validatorp.validare_persoana(persoana)
        assert False
    except ValueError:
        assert True
    persoana = Persoana(1, '', 'Str. Mihai Viteazu')
    try:
        validatorp.validare_persoana(persoana)
        assert False
    except ValueError:
        assert True
    persoana = Persoana(1, 'Andrei', '')
    try:
        validatorp.validare_persoana(persoana)
        assert False
    except ValueError:
        assert True
    persoana = Persoana(1, '', '')
    try:
        validatorp.validare_persoana(persoana)
        assert False
    except ValueError:
        assert True


test_persoana()
test_validator_persoana()