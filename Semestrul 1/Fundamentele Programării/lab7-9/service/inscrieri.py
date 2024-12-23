from repository.manager_persoane import RepositoryPersoane
from repository.manager_evenimente import RepositoryEvenimente
from repository.manager_inscrieri import RepositoryInscrieri
from domain.eveniment import Eveniment
from domain.persoana import Persoana
from datetime import datetime
class ServiceInscrieri:
    def __init__(self, repository, lista_evenimente):
        self.__repo = repository
        self.__lista_evenimente = lista_evenimente     

    def get_inscrieri(self):
        '''
        Functie ce returneaza lista de inscrieri
        :param: none
        :return: lista de inscrieri
        :rtype: list
        '''
        return self.__repo.get_lista_inscrieri()

    def adauga_inscriere(self, personID, ID):
        '''
        Functie ce adauga o inscriere in lista
        :param: personID
        :type: int
        :param: ID
        :type: int
        :return: none dar adauga inscrierea in lista
        '''
        self.__repo.adauga_inscriere(personID, ID)

    def get_evenimentele_persoanei(self, personID):
        '''
        Functie ce returneaza lista de evenimente la care este inscrisa o persoana
        :param: personID
        :type: int
        :return: lista de evenimente la care este inscrisa o persoana
        :rtype: list
        '''
        return self.__repo.get_evenimentele_persoanei(personID)
    
    def get_persoanele_evenimentului(self, ID):
        '''
        Functie ce returneaza lista de persoane inscrise la un eveniment
        :param: ID
        :type: int
        :return: lista de persoane inscrise la un eveniment
        :rtype: list
        '''
        return self.__repo.get_persoanele_evenimentului(ID)

    def evenimente_ordonate_descriere(self, personID):
        '''
        Functie ce returneaza lista de evenimente la care este inscrisa o persoana ordonata dupa descriere
        :param: personID
        :type: int
        :return: lista de evenimente la care este inscrisa o persoana ordonata dupa descriere
        :rtype: list
        '''
        evenimentele = self.get_evenimentele_persoanei(personID)
        lista = []
        for index in range(len(evenimentele)):
            eveniment = self.__lista_evenimente.findeveniment(evenimentele[index])
            lista.append(eveniment)
        insertion_sort(lista, key = cmp_descriere, reverse = False)
        return lista
        '''for index1 in range(len(lista)-1):
            eveniment1 = lista[index1]
            for index2 in range(index1, len(lista)):
                eveniment2 = lista[index2]
                if eveniment1.get_descriere() > eveniment2.get_descriere():
                    aux = lista[index1]
                    lista[index1] = lista[index2]
                    lista[index2] = aux
        
        return lista
        '''
    def evenimente_ordonate_data(self, personID):
        '''
        Functie ce returneaza lista de evenimente la care este inscrisa o persoana ordonata dupa data
        :param: personID
        :type: int
        :return: lista de evenimente la care este inscrisa o persoana ordonata dupa data
        :rtype: list
        '''
        evenimentele = self.get_evenimentele_persoanei(personID)
        lista = []
        for index in range(len(evenimentele)):
            eveniment = self.__lista_evenimente.findeveniment(evenimentele[index])
            lista.append(eveniment)
        comb_sort(lista, key = cmp_data, reverse = False)
        return lista
        '''
        for index1 in range(len(lista)-1):
            eveniment1 = lista[index1]
            for index2 in range(index1, len(lista)):
                eveniment2 = lista[index2]
                mare = 0
                if eveniment1.get_an() > eveniment2.get_an():
                    if eveniment1.get_luna() > eveniment2.get_luna():
                        if eveniment1.get_zi() > eveniment2.get_zi():
                            mare = 1
                if mare == 1:
                    aux = lista[index1]
                    lista[index1] = lista[index2]
                    lista[index2] = aux

        return lista
        '''
    def cauta_persoana_lista_persoane(self, persoanele, pers_curenta):
        '''
        Functie ce cauta o persoana in lista de persoane
        :param: persoanele
        :type: list
        :param: pers_curenta
        :type: Persoana
        :return: True/False
        :rtype: bool
        '''
        for index in persoanele:
            if index == pers_curenta:
                return True
        return False
    
    def cauta_eveniment_lista_evenimente(self, evenimente, eve_curent):
        '''
        Functie ce cauta un eveniment in lista de evenimente
        :param: evenimente
        :type: list
        :param: eve_curent
        :type: Eveniment
        :return: True/False
        :rtype: bool
        '''
        for index in evenimente:
            if index == eve_curent:
                return True
        return False
    
    def persoane_inscrise_multe_evenimente(self):
        '''
        Functie ce returneaza lista de persoane care sunt inscrise la mai multe evenimente
        :param: none
        :return: lista de persoane care sunt inscrise la mai multe evenimente
        :rtype: list
        '''
        max = 0
        pers = []
        lista_inscrieri = self.get_inscrieri()
        for index in lista_inscrieri:
            eve = self.get_evenimentele_persoanei(index[0])
            if len(eve) > max:
                max = len(eve)

        for index in lista_inscrieri:
            eve = self.get_evenimentele_persoanei(index[0])
            if len(eve) == max and self.cauta_persoana_lista_persoane(pers, index[0]) == False:
                pers.append(index[0])
        return pers 
    
    def evenimente_multi_participanti(self):
        '''
        Functie ce returneaza lista de evenimente care au multi participanti
        :param: none
        :return: lista de evenimente care au multi participanti
        :rtype: list
        '''
        max = 0
        eve = []
        lista_inscrieri = self.get_inscrieri()
        for index in lista_inscrieri:
            pers = self.get_persoanele_evenimentului(index[1])
            if len(pers) > max:
                max = len(pers)
        for index in lista_inscrieri:
            pers = self.get_persoanele_evenimentului(index[1])
            if len(pers) == max and self.cauta_eveniment_lista_evenimente(eve, index[1]) == False:
                eve.append(index[1])
        return eve
    
def insertion_sort(lista, key=lambda x:x, reverse=False):
    '''
    Functie ce sorteaza lista prin insertie
    :param lista
    :type lista: list
    :param key
    :type key: str
    :param reverse
    :type reverse: False
    :return: -
    '''
    n = len(lista)
    if n<=1:
        return lista
    for i in range(1,n):
        j = i - 1
        while j>=0 and key(lista[j]) > key(lista[i]):
            lista[j], lista[i] = lista[i], lista[j]
            j -= 1
    if reverse:
        lista.reverse()

def getNextGap(gap):
    gap = (gap*10)//13
    if gap<1:
        return 1
    return gap

def comb_sort(lista, key=lambda x:x, reverse=False):
    '''
    Functie ce sorteaza lista prin insertie
    :param lista
    :type lista: list
    :param key
    :type key: str
    :param reverse
    :type reverse: False
    :return: -
    '''
    n = len(lista)
    gap = n
    swapped = True
    while gap != 1 or swapped == 1:
        gap = getNextGap(gap)
        swapped = False
        for i in range(0, n - gap):
            if key(lista[i]) > key(lista[i + gap]):
                lista[i], lista[i + gap] = lista[i + gap], lista[i]
                swapped = True
    if reverse:
        lista.reverse()

def cmp_descriere(element):
    '''
    Functie ce returneaza descrierea aflata pe poz 5 a elementului din lista
    :param: element
    :type: list
    :return: valoarea de pe poz 5
    :rtype: str
    '''

    return element[5]

def cmp_data(element):
    '''
    Functie ce returneaza data aflata pe pozitia 3-2-1 a elementului din lista
    :param: element
    :type list
    :return: data evenimentului
    :rtype: date
    '''
    data = datetime.date(element[3], element[2], element[1])
    return data