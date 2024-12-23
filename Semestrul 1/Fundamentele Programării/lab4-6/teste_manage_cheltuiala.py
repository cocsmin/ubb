from manage_cheltuiala import *

def test_sterge_cheltuiala_zi():
    '''
    Functie ce testeaza functia sterge_cheltuiala_zi
    :param : none
    :return: none
    :rtype: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte']]
    lista_cheltuieli_test = sterge_cheltuiala_zi(lista_cheltuieli_test, [], 1)
    assert(len(lista_cheltuieli_test) == 2)
    assert(get_zi(lista_cheltuieli_test, 0) != 1)
    assert(get_zi(lista_cheltuieli_test, 1) != 1)

def test_sterge_cheltuieli_interval():
    '''
    Functie ce testeaza functia sterge_cheltuieli_interval
    :param : none
    :return: none
    :rtype: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lista_cheltuieli_test = sterge_cheltuieli_interval(lista_cheltuieli_test, [], 1, 3)
    assert(len(lista_cheltuieli_test) == 2)
    assert(get_nume(lista_cheltuieli_test, 0) == 'telefon')
    assert(get_nume(lista_cheltuieli_test, 1) == 'caiete')

def test_sterge_cheltuieli_tip():
    '''
    Functie ce testeaza functia sterge_cheltuieli_tip
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lista_cheltuieli_test = sterge_cheltuieli_tip(lista_cheltuieli_test, [], 'altele')
    assert(len(lista_cheltuieli_test) == 3)
    assert(get_tip(lista_cheltuieli_test, 0) != 'altele')
    assert(get_tip(lista_cheltuieli_test, 1) != 'altele')
    assert(get_tip(lista_cheltuieli_test, 2) != 'altele')

def test_elimina_cheltuiala():
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lungime = len(lista_cheltuieli_test)
    lista_cheltuieli_test = elimina_cheltuiala(lista_cheltuieli_test, [], 1)
    assert(len(lista_cheltuieli_test) == lungime - 1)

def test_elimina_dupatip():
    '''
    Functie ce testeaza functia elimina_dupatip
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lungime = len(lista_cheltuieli_test)
    lista_cheltuieli_test = elimina_dupatip(lista_cheltuieli_test, [], 'altele')
    assert(len(lista_cheltuieli_test) == lungime - 2)

def test_elimina_maimici():
    '''
    Functie ce testeaza functia elimina_maimici
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lungime = len(lista_cheltuieli_test)
    lista_cheltuieli_test = elimina_maimici(lista_cheltuieli_test, [], 100)
    assert(len(lista_cheltuieli_test) == lungime - 1)

def test_eliminadupatip():
    '''
    Functie ce testeaza functia elimina_dupatip
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lungime = len(lista_cheltuieli_test)
    lista_cheltuieli_test = elimina_dupatip(lista_cheltuieli_test, [], 'altele')
    assert(len(lista_cheltuieli_test) == lungime - 2)

def test_eliminacheltuiala():
    '''
    Functie ce testeaza functia elimina_cheltuiala
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lungime = len(lista_cheltuieli_test)
    lista_cheltuieli_test = elimina_cheltuiala(lista_cheltuieli_test, [], 1)
    assert(len(lista_cheltuieli_test) == lungime - 1)
    assert(get_nume(lista_cheltuieli_test, 0) != 'curent')
    assert(get_nume(lista_cheltuieli_test, 1) != 'curent')
    assert(get_nume(lista_cheltuieli_test, 2) != 'curent')
    assert(get_nume(lista_cheltuieli_test, 3) != 'curent')
    assert(get_nume(lista_cheltuieli_test, 4) != 'curent')

def test_suma_maxima():
    '''
    Functie ce testeaza functia suma_maxima
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['curent', 2, 200.0, 'intretinere'], ['shaorma', 1, 50.0, 'mancare'], ['converse', 3, 399.0, 'imbracaminte'], ['caiete', 5, 100.0, 'altele'], ['telefon', 4, 2000.0, 'altele']]
    maxima = suma_maxima(lista_cheltuieli_test, [])
    assert(maxima == 2000.0)

def test_suma_totala():
    '''
    Functie ce testeaza functia suma_totala
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['curent', 2, 200.0, 'intretinere'], ['shaorma', 1, 50.0, 'mancare'], ['converse', 3, 399.0, 'imbracaminte'], ['caiete', 5, 100.0, 'altele'], ['telefon', 4, 2000.0, 'altele']]
    suma = suma_totala(lista_cheltuieli_test, [], 'altele')
    assert(suma == 2100.0)

def test_cheltuieli_sortate():
    '''
    Functie ce testeaza functia cheltuieli_sortate
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['curent', 2, 200.0, 'intretinere'], ['shaorma', 1, 50.0, 'mancare'], ['converse', 3, 399.0, 'imbracaminte'], ['caiete', 5, 100.0, 'altele'], ['telefon', 4, 2000.0, 'altele']]
    lista_cheltuieli_test = cheltuieli_sortate(lista_cheltuieli_test, [])
    assert(get_nume(lista_cheltuieli_test, 0) == 'shaorma')
    assert(get_nume(lista_cheltuieli_test, 1) == 'curent')
    assert(get_nume(lista_cheltuieli_test, 2) == 'converse')
    assert(get_nume(lista_cheltuieli_test, 3) == 'telefon')
    assert(get_nume(lista_cheltuieli_test, 4) == 'caiete')

def test_zi_cheltuitoare():
    '''
    Functie ce testeaza functia zi_cheltuitoare
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['curent', 2, 200.0, 'intretinere'], ['shaorma', 1, 50.0, 'mancare'], ['converse', 3, 399.0, 'imbracaminte'], ['caiete', 5, 100.0, 'altele'], ['telefon', 4, 2000.0, 'altele']]
    zi = zi_cheltuitoare(lista_cheltuieli_test,[], 2000.0)
    assert(zi == 4)



def test_undo():
    '''
    Functie ce testeaza functia undo
    :param : none
    :return: none
    '''
    lista_cheltuieli_test = [['shaorma', 1, 50.0, 'mancare'], ['curent', 2, 200.0, 'intretinere'], ['converse', 3, 399.0, 'imbracaminte'], ['telefon', 4, 2000.0, 'altele'], ['caiete', 5, 100.0, 'altele']]
    lista_undo_test = []
    lista_undo_test.append(lista_cheltuieli_test)
    lista_cheltuieli_test = undo(lista_cheltuieli_test, lista_undo_test)
    assert(len(lista_cheltuieli_test) == 0)
