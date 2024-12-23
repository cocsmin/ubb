from cheltuiala import *
from manage_cheltuiala import *
def afisare_meniu():
    print('MENIU')
    print('1. Adaugă cheltuială')
    print('1.1. Adaugă o nouă cheltuială (se specifică ziua, suma, tipul)')
    print('1.2. Actualizează cheltuială (se specifică ziua, suma, tipul)')
    print('2. Ștergere.')
    print('2.1. Șterge toate cheltuielile pentru ziua dată')
    print('2.2. Șterge cheltuielile pentru un interval de timp (se dă ziua de început și sfârșit, se șterg toate cheltuielile pentru perioada dată)')
    print('2.3. Șterge toate cheltuielile de un anumit tip')
    print('3. Cautari.')
    print('3.1. Tipărește toate cheltuielile mai mari decât o sumă dată')
    print('3.2. Tipărește toate cheltuielile efectuate înainte de o zi dată și mai mici decât o sumă (se dă suma și ziua, se tipăresc toate cheltuielile mai mici ca suma dată și efectuate înainte de ziua specificată)')
    print('3.3. tipărește toate cheltuielile de un anumit tip.')
    print('4. Rapoarte.')
    print('4.1. Tipărește suma totală pentru un anumit tip de cheltuială')
    print('4.2. Găsește ziua în care suma cheltuită e maximă')
    print('4.3. Tipărește toate cheltuielile ce au o anumita suma.')
    print('4.4. Tipărește cheltuielile sortate după tip')
    print('5. Filtrare.')
    print('5.1. Elimină toate cheltuielile de un anumit tip')
    print('5.2. Elimină toate cheltuielile mai mici decât o sumă dată')
    print('6. Undo: Reface ultima operație (lista de cheltuieli revine ce exista înainte de ultima operație care a modificat lista). ')
    print('10. Adauga elementele default in lista')
    print('15. Introdu comanda smechera')
    print('0. Iesire')


def optiune():
    '''
    Functia primeste optiunea utilizatorului si o face o lista
    :return: o lista
    :rtype: list

    '''
    lista_string = input('Introduceti optiunea dorita(numerele trebuie sa fie separate prin punct, precum 1.1):\n')
    lista_optiuni = []
    lista_optiuni = [int(element) for element in lista_string.split('.')]
    return lista_optiuni

def prima_optiune(lista):
    '''
    Functia returneaza prima optiune aleasa
    :param lista: lista de optiuni
    :type lista: list
    :return: prima optiune
    :rtype: int
    '''
    return lista[0]

def adoua_optiune(lista):
    '''
    Functia returneaza a doua optiune aleasa
    :param lista: lista de optiuni
    :type lista: list
    :return: a doua optiune
    :rtype: int
    '''

    return lista[1]






def start(): 
    ok = 'y'
    lista_cheltuieli = []
    lista_undo = []
    while ok == 'y' or ok == 'Y':
        afisare_meniu()
        lista_optiuni = []
        lista_optiuni = optiune()
        if prima_optiune(lista_optiuni) == 1:
            if adoua_optiune(lista_optiuni) == 1:
                try:
                    nume = input('Introduceti numele cheltuielii:\n')
                    zi = input('Introduceti ziua din luna:\n')
                    suma = input('Introduceti suma alocata cheltuielii\n')
                    tip = input('Introduceti tipul alocat cheltuielii(mancare/intretinere/imbracaminte/telefon/altele):\n')
                    citeste_cheltuiala(lista_cheltuieli, lista_undo, nume, zi, suma, tip)
                    print(lista_cheltuieli)
                except ValueError as e:
                    print(e)
            elif adoua_optiune(lista_optiuni) == 2:
                indice = int(input('Introduceti indicele cheltuielii pe care doriti sa o actualizati:\n'))
                nume = input('Introduceti noul nume al cheltuielii:\n')
                ziua = int(input('Introduceti noua zi a cheltuielii:\n'))
                bani = float(input('Introduceti noua suma a cheltuielii:\n'))
                tip = input('Introduceti noul tip al cheltuielii:\n')
                try:
                    actualizeaza_cheltuiala(lista_cheltuieli, lista_undo, indice, nume, ziua, bani, tip)
                    print(lista_cheltuieli)
                except ValueError as e:
                    print(e)

        elif prima_optiune(lista_optiuni) == 2:
            if adoua_optiune(lista_optiuni) == 1:
                ziua_data = int(input('Introduceti ziua pentru care vreti sa fie sterse cheltuielile:\n'))
                lista_cheltuieli = sterge_cheltuiala_zi(lista_cheltuieli, lista_undo, ziua_data)
                print(lista_cheltuieli)
            elif adoua_optiune(lista_optiuni) == 2:
                zi_inceput = int(input('Introduceti ziua de inceput:\n'))
                zi_sfarsit = int(input('Introduceti ziua de sfarsit:\n'))
                lista_cheltuieli = sterge_cheltuieli_interval(lista_cheltuieli, lista_undo, zi_inceput, zi_sfarsit)
                print(lista_cheltuieli)
            elif adoua_optiune(lista_optiuni) == 3:
                tipul_dat = input('Introduceti tipul pe care doriti sa-l eliminati (mancare/intretinere/imbracaminte/telefon/altele): \n')
                lista_cheltuieli = sterge_cheltuieli_tip(lista_cheltuieli, lista_undo, tipul_dat)
                print(lista_cheltuieli)
        elif prima_optiune(lista_optiuni) == 3:
            if adoua_optiune(lista_optiuni) == 1:
                suma_data = float(input('Introduceti suma:\n'))
                lista_de_afisat= []
                lista_de_afisat = cheltuieli_mai_mari(lista_cheltuieli, lista_undo, suma_data)
                print(lista_de_afisat)
            elif adoua_optiune(lista_optiuni) == 2:
                ziua_data = int(input('Introduceti ziua:\n'))
                suma_data = float(input('Introduceti suma:\n'))
                lista_de_afisat = []
                lista_de_afisat = cheltuieli_inaintedesimaimici(lista_cheltuieli, lista_undo, ziua_data, suma_data)
                print(lista_de_afisat)
            elif adoua_optiune(lista_optiuni) == 3:
                tipul_dat = input('Introduceti tipul cautat(mancare/intretinere/imbracaminte/telefon/altele): \n')
                lista_de_afisat = []
                lista_de_afisat = cheltuieli_tip(lista_cheltuieli, lista_undo, tipul_dat)
                print(lista_de_afisat)
        elif prima_optiune(lista_optiuni) == 4:
            if adoua_optiune(lista_optiuni) == 1:
                tipul_dat = input('Introduceti tipul cautat(mancare/intretinere/imbracaminte/telefon/altele): \n')
                suma_cautata = suma_totala(lista_cheltuieli, lista_undo, tipul_dat)
                print('Suma totala pentru tipul de cheltuiala cautat este:',suma_cautata)
            elif adoua_optiune(lista_optiuni) == 2:
                maxima = suma_maxima(lista_cheltuieli, lista_undo)
                zi = zi_cheltuitoare(lista_cheltuieli, lista_undo, maxima)
                print('Ziua in care suma cheltuita e maxima este:', zi)
            elif adoua_optiune(lista_optiuni) == 3:
                suma_data = float(input('Introduceti suma cautata:\n'))
                lista_de_afisat = cheltuieli_sumax(lista_cheltuieli, lista_undo, suma_data)
                print(lista_de_afisat)
            elif adoua_optiune(lista_optiuni) == 4:
                lista_de_afisat = []
                lista_de_afisat = cheltuieli_sortate(lista_cheltuieli, lista_undo)
                print(lista_de_afisat)
        elif prima_optiune(lista_optiuni) == 5:
            if adoua_optiune(lista_optiuni) == 1:
                tipul_dat = input('Introduceti tipul pe care doriti sa-l eliminati (mancare/intretinere/imbracaminte/telefon/altele): \n')
                lista_cheltuieli = sterge_cheltuieli_tip(lista_cheltuieli, lista_undo, tipul_dat)
                print(lista_cheltuieli)
            elif adoua_optiune(lista_optiuni) == 2:
                suma = float(input('Introduceti suma:\n'))
                lista_cheltuieli = elimina_maimici(lista_cheltuieli, lista_undo, suma)
                print(lista_cheltuieli)
        elif prima_optiune(lista_optiuni) == 6:
            lista_cheltuieli = undo(lista_cheltuieli, lista_undo)
            print(lista_cheltuieli)

        elif prima_optiune(lista_optiuni) == 15:
            string = input('Introduceti comenzile dorite: \n')
            comenzi = string.split('; ')
            for id in comenzi:
                comanda = id.split(' ')
                if (comanda[0] == 'Elimina'):
                    #elimina din lista cheltuielile cu tipul precizat in comanda
                    lista_cheltuieli = elimina_dupatip(lista_cheltuieli, lista_undo, comanda[1])
                    print(lista_cheltuieli)
                elif (comanda[0] == 'Suma'):
                    #afiseaza cheltuielile din lista care au suma egala cu cea citita
                    cheltuieli_sumax(lista_cheltuieli, lista_undo, float(comanda[1]))
                elif (comanda[0] == 'Afisare'):
                    #afiseaza cheltuiala din lista cu indicele precizat in comanda
                    afisare_cheltuiala(lista_cheltuieli, int(comanda[1]))




        elif prima_optiune(lista_optiuni) == 10:
            #optiuni_default()
            pass

        elif prima_optiune(lista_optiuni) == 0:
            break

        else:
            raise ValueError('Optiunea aleasa nu exista! Incercati din nou\n')
        
        ok = input('Doriti sa continuati?(Y/N)\n')    








def test_prima_optiune():
    assert(prima_optiune([1,2]) == 1)
    assert(prima_optiune([4,5]) == 4)
    assert(prima_optiune([2,4]) == 2)
    assert(prima_optiune([0]) == 0)

def test_adoua_optiune():
    assert(adoua_optiune([1,2]) == 2)
    assert(adoua_optiune([4,5]) == 5)
    assert(adoua_optiune([1,1]) == 1)
    assert(adoua_optiune([5,2]) == 2)