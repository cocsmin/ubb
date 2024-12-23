from cheltuiala import *
from utils import make_list_copy

def citeste_cheltuiala(lista_cheltuieli, lista_undo, nume, zi, suma, tip):
    '''
    Citeste o noua cheltuiala (nume, ziua din luna, suma, tipul)
    :return: none
    '''
    cheltuiala = creeaza_cheltuiala(nume, zi, suma, tip)
    validare_cheltuiala(cheltuiala)
    lista_undo.append(make_list_copy(lista_cheltuieli))
    adauga_cheltuiala(lista_cheltuieli,lista_undo, cheltuiala)

def undo(lista_cheltuieli, lista_undo):
    '''
    Functie ce face undo la ultima operatie
    :param lista_cheltuieli : lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :return: lista de cheltuieli modificata
    :rtype: list
    '''
    lista_cheltuieli = lista_undo.pop()
    return lista_cheltuieli

def actualizeaza_cheltuiala(lista_cheltuieli, lista_undo, indice, nume, zi, bani, tip):
    '''
    Actualizeaza o cheltuiala
    :param indice: indicele cheltuielii din lista
    :type indice: int
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :return: none dar modifica lista_cheltuieli
    :rtype: none
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    validare_cheltuiala([nume, zi, bani, tip])
    lista_cheltuieli[indice] = [nume, zi, bani, tip]

def adauga_cheltuiala(lista_cheltuieli,lista_undo, cheltuiala):
    '''
    Adauga o cheltuiala cu parametrii cititi deja in lista cu cheltuieli
    :param lista_cheltuieli: lista cu cheltuielile
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param cheltuiala: cheltuiala noua
    :type cheltuiala: list
    :return: none ; modifica lista_cheltuieli adaugand la sfarsit noua cheltuiala
    :rtype: none
    '''
    #cheltuiala = citeste_cheltuiala()
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_cheltuieli.append(cheltuiala)

def afisare_cheltuiala(lista_cheltuieli, indice):
    '''
    Functie ce are ca unic scop afisarea unei cheltuieli din lista
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param indice: indicele cheltuielii din lista
    :type indice: int
    :return: afiseaza cheltuiala
    :rtype: afisare?
    '''
    print(lista_cheltuieli[indice])

def sterge_cheltuiala_zi(lista_cheltuieli, lista_undo, zi):
    '''
    Functie ce sterge toate cheltuielile dintr-o zi data
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param zi: ziua din luna
    :type zi: int
    :return: lista fara cheltuielile din ziua data
    :rtype: list
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = []
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if cheltuiala[1] != zi]

    return lista_noua

def sterge_cheltuieli_interval(lista_cheltuieli, lista_undo, zi_inceput, zi_sfarsit):
    '''
    Functie ce sterge toate cheltuielile dintr-un interval de timp dat
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param zi_inceput: ziua de inceput a intervalului
    :type zi_inceput: int
    :param zi_sfarsit: ziua de sfarsit a intervalului
    :type zi_sfarsit: int
    :return: lista fara cheltuielile din intervalul de timp dat
    :rtype: list
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = []
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if cheltuiala[1] < zi_inceput or cheltuiala[1] > zi_sfarsit]
    return lista_noua

def sterge_cheltuieli_tip(lista_cheltuieli, lista_undo, tip):
    '''
    Functie ce sterge toate cheltuielile de un anumit tip
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param tip: tipul de cheltuiala
    :type tip: str
    :return: lista fara cheltuielile de un anumit tip
    :rtype: list
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = []
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if cheltuiala[3] != tip]
    return lista_noua

def cheltuieli_mai_mari(lista_cheltuieli, lista_undo, sum):
    '''
    Functie ce cauta toate cheltuielile ce au suma mai mare decat o suma citita anterior
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param sum: suma citita anterior
    :type sum: float
    :return: none
    :rtype: none
    '''
    lista_noua = []
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if get_suma(cheltuiala) > sum]
    return lista_noua


def cheltuieli_inaintedesimaimici(lista_cheltuieli, lista_undo, data, sum):
    '''
    Functie ce cauta toate cheltuielile care au fost efectuate inainte de o zi din luna data 
    de utilizator si mai mici decat o suma data
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param data: ziua din luna 
    :type data: int
    :param sum: suma citita anterior
    :type sum: float
    :return: none
    :rtype: none
    '''
    lista_noua = []
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if (get_suma(cheltuiala) < sum) and (get_zi(cheltuiala) < data)]
    return lista_noua

def cheltuieli_tip(lista_cheltuieli, lista_undo, tipul):
    '''
    Functie ce cauta toate cheltuielile de un anumit tip(mancare/intretinere/imbracaminte/telefon/altele)
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param tipul: tipul de cheltuiala
    :type tipul: str
    :return: none
    :rtype: none
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = []
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if (get_tip(cheltuiala) == tipul)]
    return lista_noua

def suma_totala(lista_cheltuieli, lista_undo, tipul):
    '''
    Functie ce calculeaza si returneaza suma totala pentru un anumit tip de cheltuiala
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param tipul: tipul de cheltuiala
    :type tipul: str
    :return sumatip: suma calculata pentru un anumit tip de cheltuiala
    :rtype: float
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    sumatip = 0.0
    for cheltuiala in lista_cheltuieli:
        if (get_tip(cheltuiala) == tipul):
            sumatip += get_suma(cheltuiala)

    return sumatip

def suma_maxima(lista_cheltuieli, lista_undo):
    '''
    Functie ce cauta suma maxima pentru toate cheltuielile
    :param : lista cu cheltuieli
    :type : list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :return suma_max: suma maxima dintre cheltuieli
    :rtype: float
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    suma_max = -1.0
    for cheltuiala in lista_cheltuieli:
        if (get_suma(cheltuiala) > suma_max):
            suma_max = get_suma(cheltuiala)

    return suma_max

def zi_cheltuitoare(lista_cheltuieli, lista_undo, suma):
    '''
    Functie ce gaseste ziua in care suma cheltuita a fost maxima
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param suma: suma maxima
    :type suma: float
    :return zi_cautata: ziua in care suma e maxima
    :rtype: int
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    zi_cautata = -1
    for cheltuiala in lista_cheltuieli:
        if (get_suma(cheltuiala) == suma):
            zi_cautata = get_zi(cheltuiala)

    return zi_cautata

def cheltuieli_sumax(lista_cheltuieli, lista_undo, suma):
    '''
    Functie ce cauta cheltuielile care au aceeasi suma cu o suma introdusa de utilizator
    :param lista_cheltuieli: lista cu cheltuieli
    :type lista_cheltuieli: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param suma: suma cautata
    :type suma: float
    :return: none
    :rtype: none
    '''
    lista_noua = []
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if (get_suma(cheltuiala) == suma)]
    return lista_noua

def cheltuieli_sortate(lista_cheltuieli, lista_undo):
    '''
    Functie care sorteaza cheltuielile dupa tip
    :param : lista cu cheltuieli
    :type : list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :return: none
    :rtype: none
    '''
    lista_noua = []
    lista_undo.append(make_list_copy(lista_cheltuieli))
    for cheltuiala in lista_cheltuieli:
        if (get_tip(cheltuiala) == 'altele'):
            lista_noua.append(cheltuiala)

    for cheltuiala in lista_cheltuieli:
        if (get_tip(cheltuiala) == 'imbracaminte'):
            lista_noua.append(cheltuiala)

    for cheltuiala in lista_cheltuieli:
        if (get_tip(cheltuiala) == 'intretinere'):
            lista_noua.append(cheltuiala)

    for cheltuiala in lista_cheltuieli:
        if (get_tip(cheltuiala) == 'mancare'):
            lista_noua.append(cheltuiala)

    for cheltuiala in lista_cheltuieli:
        if (get_tip(cheltuiala) == 'telefon'):
            lista_noua.append(cheltuiala)

    return lista_noua

def elimina_cheltuiala(lista_cheltuieli, lista_undo, indice):
    '''
    Functie care elimina o cheltuiala din lista
    :param lista: lista cu cheltuieli
    :type lista: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param indice: indicele cheltuielii din lista
    :type indice: int
    :return lista_cheltuieli_noua: lista fara cheltuiala cu indicele dat
    :rtype: list
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_cheltuieli_noua = []
    lista_cheltuieli_noua = [cheltuiala for cheltuiala, id in enumerate(lista_cheltuieli) if id != indice]
    return lista_cheltuieli_noua

def elimina_dupatip(lista_cheltuieli, lista_undo, tipul):
    '''
    Functie care elimina toate cheltuielile de un anumit tip din lista
    :param lista: lista cu cheltuieli
    :type lista: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param tipul: tipul de cheltuiala
    :type tipul: str
    :return: lista fara cheltuielile de un anumit tip
    :rtype: list
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = []
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if cheltuiala[3] != tipul]
    return lista_noua

def elimina_maimici(lista_cheltuieli, lista_undo, suma):
    '''
    Functie care elimina toate cheltuielile mai mici decat o suma citita
    :param lista: lista cu cheltuieli
    :type lista: list
    :param lista_undo: lista de undo
    :type lista_undo: list
    :param suma: suma citita
    :type suma: float
    :return: lista fara cheltuielile mai mici decat suma citita
    :rtype: list
    '''
    lista_undo.append(make_list_copy(lista_cheltuieli))
    lista_noua = []
    lista_noua = [cheltuiala for cheltuiala in lista_cheltuieli if cheltuiala[2] > suma]
    return lista_noua