from domain.validatorevenimente import ValidatorE
from domain.eveniment import Eveniment
from repository.manager_evenimente import RepositoryEvenimente
import random
class ServiceE:
    def __init__(self, repo_evenimente, validatore):
        self.__repo_evenimente = repo_evenimente
        self.__validatore = validatore

    def get_evenimente(self):
        '''
        Functie ce returneaza lista de evenimente
        :param: none
        :return: lista de evenimente
        :rtype: list
        '''
        return self.__repo_evenimente.get_lista_evenimente()
    
    def findeveniment(self, ID):
        '''
        Functie ce returneaza un eveniment dupa id-ul dat
        :param: ID
        :type: int
        :return: evenimentul cu ID-ul dat
        :rtype: Eveniment
        '''
        return self.__repo_evenimente.gaseste_eveniment(ID)
    
    def store_eveniment(self, ID, zi, luna, an, timp, descriere):
        '''
        Functie ce adauga un eveniment in lista
        :param: ID
        :type: int
        :param: data
        :type: string
        :param: timp
        :type: int
        :param: descriere
        :type: string
        :return: none dar adauga evenimentul in lista
        '''
        eveniment = Eveniment(ID, zi, luna, an, timp, descriere)
        self.__validatore.validare_eveniment(eveniment)
        self.__repo_evenimente.adauga_eveniment(eveniment)

    def adauga_by_default_evenimente(self):  
        '''
        Functie ce adauga cateva evenimente in lista
        :param: none
        :return: none dar adauga mai multe evenimente in lista
        '''
        self.store_eveniment(1, 10, 2, 2024, 2, 'Majorat')
        self.store_eveniment(2, 5, 3, 2024, 4, 'Meeting')
        self.store_eveniment(3, 9, 12, 2024, 3, 'Nunta')
        self.store_eveniment(4, 3, 7, 2024, 6, 'Botez')
        self.store_eveniment(5, 8, 10, 2024, 4, 'Petrecere')

    def delete_eveniment(self, ID):
        '''
        Functie ce sterge un eveniment din lista
        :param: ID
        :type: int
        :return: none dar lista de evenimente se modifica prin stergerea evenimentului cu ID-ul dat
        '''
        self.__repo_evenimente.sterge_eveniment(ID)

    def afisare_evenimente(self):
        
        print(self.__repo_evenimente.__str__())


    def generaree(self, numar):
        '''
        Functie ce genereaza 'numar' evenimente in lista
        :param numar: numarul citit
        :type int
        '''
        evenimente_de_ales = ['Nunta', 'Botez', 'Cununie', 'Ziua Alexandrei', 'Concert SPP', 'Untold', 'Electric Castle', 'Neversea', 'Sfantul Daniel', 'Balul bobocilor']
        #timp_de_ales = ['2', '1', '5', '3', '4', '6', '7', '8', '9', '12']
        for index in range(numar):
            ide = random.randint(5,500)
            data_zi = random.randint(1, 31)
            data_luna = random.randint(1, 12)
            data_an = random.randint(2024, 2030)
            timp = random.randint(1,12)
            descriere = random.choice(evenimente_de_ales)
            evenimentul = Eveniment(ide, data_zi, data_luna, data_an, timp, descriere)
            #self.store_eveniment(ide, data_zi, data_luna, data_an, timp, descriere)
            self.__repo_evenimente.adauga_eveniment(evenimentul)
            print(evenimentul.__str__())

    def modifica_eveniment(self, ID, zi, luna, an, timp, descriere):
        '''
        Functie ce modifica un eveniment din lista
        :param: ID
        :type: int
        :param: data
        :type: string
        :param: timp
        :type: int
        :param: descriere
        :type: string
        :return: none dar lista de evenimente se modifica prin modificarea evenimentului cu ID-ul dat
        '''
        #zi, luna, an = self.data_corecta(data)
        self.__repo_evenimente.modifica_eveniment(ID, zi, luna, an, timp, descriere)

    def cauta_eveniment(self, id):
        '''
        Functie ce cauta un eveniment in lista
        :param: id
        :type: int
        :return: evenimentul cu id-ul dat
        :rtype: Eveniment
        '''
        return self.__repo_evenimente.gaseste_eveniment(id)
    