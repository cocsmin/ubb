
def creeaza_cheltuiala(nume, zi, suma, tip):
    '''
    Creeaza o cheltuiala
    :param nume: numele cheltuielii
    :type nume: str
    :param zi: ziua din luna pt cheltuiala
    :type zi: str
    :param suma: suma alocata cheltuielii
    :type suma: str
    :param tip: categoria din care face parte cheltuiala(mancare/intretinere/imbracaminte/telefon/altele)
    :type tip: str
    :return: cheltuiala creata
    :rtype: list
    '''
    ziua = int(zi)
    bani = float(suma)
    #return {'nume': nume, 'zi': ziua, 'suma': bani, 'tip': tip}
    return [nume, ziua, bani, tip]


def get_nume(cheltuiala):
    '''
    Functie ce returneaza numele unei cheltuieli din lista
    :param cheltuiala: cheltuiala din lista
    :type cheltuiala: list
    :return: numele cheltuielii
    :rtype: str
    '''
    return cheltuiala[0]

def get_zi(cheltuiala):
    '''
    Functie ce returneaza ziua unei cheltuieli din lista
    :param cheltuiala: cheltuiala din lista
    :type cheltuiala: list
    :return: ziua cheltuielii
    :rtype: int
    '''
    return cheltuiala[1]

def get_suma(cheltuiala):
    '''
    Functie ce returneaza suma unei cheltuieli din lista
    :param cheltuiala: cheltuiala din lista
    :type cheltuiala: list
    :return: suma cheltuielii
    :rtype: float
    '''
    return cheltuiala[2]

def get_tip(cheltuiala):
    '''
    Functie ce returneaza tipul unei cheltuieli din lista
    :param cheltuiala: cheltuiala din lista
    :type cheltuiala: list
    :return: tipul cheltuielii
    :rtype: str
    '''
    return cheltuiala[3]

def validare_cheltuiala(cheltuiala):
    '''
    Functie ce verifica daca o cheltuiala este valida
    :param cheltuiala: cheltuiala ce trebuie verificata
    :type cheltuiala: list
    :return: True daca cheltuiala este valida, False in caz contrar
    :rtype: bool
    '''
    erori = []
    if get_nume(cheltuiala) == ' ':
        erori.append('Nume incorect. Numele nu poate fi vid')
    if get_zi(cheltuiala) < 1 or get_zi(cheltuiala) > 31:
        erori.append('Zi incorecta. Ziua trebuie sa fie intre 1 si 31')
    if get_suma(cheltuiala) < 0:
        erori.append('Suma incorecta. Suma trebuie sa fie pozitiva')
    if get_tip(cheltuiala) not in ['mancare', 'intretinere', 'imbracaminte', 'telefon', 'altele']:
        erori.append('Tip incorect. Tipul trebuie sa fie mancare/intretinere/imbracaminte/telefon/altele')
    if len(erori) > 0:
        raise ValueError(erori)