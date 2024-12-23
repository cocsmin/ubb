from cheltuiala import *

def test_creare_cheltuiala():
    '''
    Functie ce testeaza functia creare_cheltuiala
    :param : none
    :return: none
    '''
    cheltuiala1 = creeaza_cheltuiala('shaorma', 1, 50.0, 'mancare')
    assert(get_nume(cheltuiala1) == 'shaorma')
    assert(get_zi(cheltuiala1) == 1)
    assert(get_suma(cheltuiala1) == 50.0)
    assert(get_tip(cheltuiala1) == 'mancare')

def test_gets():
    '''
    Functie ce testeaza functiile get_nume, get_zi, get_suma, get_tip
    :param : none
    :return: none
    '''
    cheltuiala1 = creeaza_cheltuiala('shaorma', 1, 50.0, 'mancare')
    assert(get_nume(cheltuiala1) == 'shaorma')
    assert(get_zi(cheltuiala1) == 1)
    assert(get_suma(cheltuiala1) == 50.0)
    assert(get_tip(cheltuiala1) == 'mancare')


def test_validare_cheltuiala():
    '''
    Functie ce testeaza functia validare_cheltuiala
    :param : none
    :return: none
    '''
    cheltuiala1 = ['shaorma', 1, 50.0, 'mancare']
    try:
        validare_cheltuiala(cheltuiala1)
        assert(True)
    except ValueError:
        assert(False)
    
    cheltuiala2 = ['curent', 0, 200.0, 'intretinere']
    try:
        validare_cheltuiala(cheltuiala2)
        assert(False)
    except ValueError:
        assert(True)

    cheltuiala3 = ['converse', 3, -399.0, 'imbracaminte']
    try:
        validare_cheltuiala(cheltuiala3)
        assert(False)
    except ValueError:
        assert(True)

    cheltuiala4 = ['telefon', 4, 2000.0, 'facturi']
    try:
        validare_cheltuiala(cheltuiala4)
        assert(False)
    except ValueError:
        assert(True)


    