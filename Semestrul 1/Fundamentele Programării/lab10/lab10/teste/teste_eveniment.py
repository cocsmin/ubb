from domain.eveniment import Eveniment
from domain.validatorevenimente import ValidatorE
def test_eveniment():
    eveniment = Eveniment(1, 1, 1, 2023, 1, 'Nunta')
    assert eveniment.get_id() == 1
    assert eveniment.get_zi() == 1
    assert eveniment.get_luna() == 1
    assert eveniment.get_an() == 2023
    assert eveniment.get_timp() == 1
    assert eveniment.get_descriere() == 'Nunta'
    eveniment.set_zi(2)
    eveniment.set_luna(2)
    eveniment.set_an(2024)
    eveniment.set_timp(2)
    eveniment.set_descriere('Botez')
    eveniment.set_id(2)
    assert eveniment.get_id() == 2
    assert eveniment.get_zi() == 2
    assert eveniment.get_luna() == 2
    assert eveniment.get_an() == 2024
    assert eveniment.get_timp() == 2
    assert eveniment.get_descriere() == 'Botez'
    assert str(eveniment) == '2 2/2/2024 2 Botez'

def test_validator_eveniment():
    validatore = ValidatorE()
    eveniment = Eveniment(1, 1, 1, 2023, 1, 'Nunta')
    validatore.validare_eveniment(eveniment)
    eveniment = Eveniment(-1, 1, 1, 2023, 1, 'Nunta')
    try:
        validatore.validare_eveniment(eveniment)
        assert False
    except ValueError:
        assert True
    eveniment = Eveniment(1, -1, 1, 2023, 1, 'Nunta')
    try:
        validatore.validare_eveniment(eveniment)
        assert False
    except ValueError:
        assert True
    eveniment = Eveniment(1, 1, -1, 2023, 1, 'Nunta')
    try:
        validatore.validare_eveniment(eveniment)
        assert False
    except ValueError:
        assert True
    eveniment = Eveniment(1, 1, 1, 2022, 1, 'Nunta')
    try:
        validatore.validare_eveniment(eveniment)
        assert False
    except ValueError:
        assert True
    eveniment = Eveniment(1, 1, 1, 2023, -1, 'Nunta')
    try:
        validatore.validare_eveniment(eveniment)
        assert False
    except ValueError:
        assert True

test_eveniment()
test_validator_eveniment()