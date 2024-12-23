def afisare_meniu():
    '''
    Afiseaza o interfata sub forma unui meniu pentru a alege optiunea dorita
    
    '''
    print('1. Citire lista')
    print('2. Gasirea secventei de lungime maxima care respectă: p=1 sau in oricare trei elemente consecutive exista o valoarea care se repeta.')
    print('3. Gasirea secventei de lungime maxima care respectă: p=1 sau diferentele (x[j+1] - x[j]) si (x[j+2] - x[j+1]) au semne contrare, pentru j=i..i+p-2.')
    print('4. Gasirea secventei de lungime maxima care respecta: are oricare doua elemente consecutive sunt de semne contrare.')
    print('5. Iesire')

lista_numere = []
def citire_lista():
    '''
    Citeste o lista de numere si o transforma din string in numere intregi
    '''
    lst = input('Introduceti lista ')
    lst = lst.split(', ')
    lst = [numar.strip() for numar in lst]
    lst = [int(numar) for numar in lst]
    return lst

def repetitie(x,y,z):
    '''
    O functie ce verifica daca cel putin 2 din 3 valori sunt identice
    '''
    if x == y:
        return True
    if y == z:
        return True
    if x == z:
        return True
    return False

def cautare_secventa2(lst):
    '''
    Functia care rezolva cerinta de la optiunea 2
    '''
    secventa_maxima = []
    secventa_curenta = []
    for i in range(len(lst) - 2):
        if repetitie(lst[i],lst[i + 1],lst[i + 2]) or repetitie(lst[i],lst[i - 1],lst[i - 2]):
            secventa_curenta.append(lst[i])
        else:
            if len(secventa_curenta) > len(secventa_maxima):
                secventa_maxima = secventa_curenta
            secventa_curenta = []
    if len(secventa_curenta) > len(secventa_maxima):
        secventa_maxima = secventa_curenta
    return secventa_maxima

def cautare_secventa3(lst, i, p):
    '''
    Functia care rezolva cerinta de la optiunea 3
    '''
    secventa_maxima = []
    secventa_curenta = []
    for pozitie in range(i + p - 2):
        if (lst[pozitie + 1] - lst[pozitie])*(lst[pozitie + 2] - lst[pozitie + 1]) < 0:
            secventa_curenta.append(lst[pozitie])
        else:
            if len(secventa_curenta) > len(secventa_maxima):
                secventa_maxima = secventa_curenta
            secventa_curenta = []
    if len(secventa_curenta) > len(secventa_maxima):
        secventa_maxima = secventa_curenta
    return secventa_maxima

def cautare_secventa4(lst):
    '''
    Functia care rezolva cerinta de la optiunea 4
    '''
    secventa_maxima = []
    secventa_curenta = []
    for i in range(len(lst)-1):
        if lst[i] * lst[i + 1] < 0:
            secventa_curenta.append(lst[i])
        else:
            if len(secventa_curenta) > len(secventa_maxima):
                secventa_maxima = secventa_curenta
            secventa_curenta = []
    if len(secventa_curenta) > len(secventa_maxima):
        secventa_curenta.append(lst[i + 1])
        secventa_maxima = secventa_curenta
    return secventa_maxima

def test_cautare_secventa4():
    '''
    Testeaza functia care este folosita in rezolvarea cerintei de la optiunea 4
    '''
    lista = [1, -1, 2, 3, 4, -5, 6, -7, 8, -9]
    assert cautare_secventa4(lista) == [4, -5, 6, -7, 8, -9]
    assert cautare_secventa4([0, 1, 2, 3, 4, 5]) == []
    assert cautare_secventa4([1, -1, 2]) == [1, -1, 2]
    print('Toate testele au fost trecute cu succes\n')

test_cautare_secventa4()

while True:
    afisare_meniu()
    optiune=int(input('Introduceti optiunea \n'))
    if optiune == 1:
        lista_numere = citire_lista()
        print('Lista numerelor este' , lista_numere)
    if optiune == 2:
        secventa_cautata = cautare_secventa2(lista_numere)
        print('Secventa cautata este ',secventa_cautata)

    if optiune == 3:
        i=int(input('Introduceti i\n'))
        p=int(input('Introduceti p\n'))
        secventa_cautata = cautare_secventa3(lista_numere,i,p)
        print('Secventa cautata este ',secventa_cautata)

    if optiune == 4:
        secventa_cautata = cautare_secventa4(lista_numere)
        print('Secventa cautata este ',secventa_cautata)

    if optiune == 5:
        break

