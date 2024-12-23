from repository.repo import FileRepository
from domain.validator import Validator
from domain.melodie import Melodie
import random
class Service:
    def __init__(self, repo_melodii, validator):
        self.__melodii = repo_melodii
        self.__validator = validator
    def adaugare_melodie(self, melodie):
        '''
        Functie ce valideaza dupa care adauga o melodie in lista
        :param melodie
        :type Melodie
        '''
        self.__validator.valideaza(melodie)
        self.__melodii.adaugare_melodie(melodie)

    def modificare_melodie(self, titlu, artist, gen_nou, data_noua):
        '''
        Functie ce modifica melodia cu titlul dat
        :param titlu - titlul melodiei care se doreste a fi modificata
        :type titlu - str
        :param gen_nou - genul nou ce ii va fi atribuit melodiei
        :type gen_nou - str
        :param data_noua - data noua ce ii va fi atribuita melodiei
        :type data_noua - str (respecta formatul)
        :return - None dar modifica lista de melodii
        '''
        melodie = Melodie(titlu, artist, gen_nou, data_noua)
        self.__validator.valideaza(melodie)
        self.__melodii.modificare_melodie(titlu, artist, gen_nou, data_noua)

    def get_lista_melodii(self):
        '''
        Functie ce returneaza lista de melodii
        :return lista_melodii
        :rtype list
        '''    
        self.__melodii.get_lista_melodii()

    def creeaza_random(self, numar_melodii, lista_titluri, lista_artisti):
        '''
        Functie ce se foloseste de lista de titluri si lista de artisti oferita de utilizator pentru a genera *numar_melodii* melodii prin random
        :param numar_melodii - nr de mel
        :type int
        :param lista_titluri
        :type list
        :param lista_artisti
        :type list
        '''
        for i in range(int(numar_melodii)):
            titlu = random.choice([titlu for titlu in lista_titluri])
            artist = random.choice([artist for artist in lista_artisti])
            gen = random.choice(['Rock', 'Pop', 'Jazz'])
            zi = random.randint(0,31)
            luna = random.randint(1,12)
            an = random.randint(0, 2024)
            data = str(zi) + '.' + str(luna) + '.' + str(an)
            melodie = Melodie(titlu, artist, gen, data)
            self.__melodii.adaugare_melodie(melodie)

    def sorteaza_melodii(self):
         '''
         Functie ce sorteaza melodiile dupa data aparitiei
         '''


    
def MergeSort(lista, start, end):
        '''
        Functie generala de MergeSort 
        '''
        if end - start <= 1:
            return
        mid = (start + end)//2
        MergeSort(lista, start, mid)
        MergeSort(lista, mid, end)
        merge(lista, start, mid, end)

def merge(lista, start, mid, end):
        '''
        Functie ce concateneaza 2 liste
        :param start: pozitia de start
        :param mid: poz de mijloc:
        :param end: poz de final
        :type int
        '''
        n1 = mid - start + 1
        n2 = end - mid
        Left = []
        Right = []
        for i in range(n1):
            Left.append(lista[i])

        for j in range(n2):
            Right.append(lista[j])
        i = 0
        j = 0
        k = start
        while i < n1 and j < n2:
            if Left[i] <= Right[j]:
                lista[k] = Left[i]
                i+=1
            elif Right[j] < Left[i]:
                lista[k] = Right[j]
                j+=1
            k+=1

        while i < n1:
            lista[k] = Left[i]
            i+=1
            k+=1

        while j < n2:
            lista[k] = Right[j]
            j+=1
            k+=1

        return lista
    
 