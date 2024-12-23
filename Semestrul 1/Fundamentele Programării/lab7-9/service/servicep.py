from domain.persoana import Persoana
from domain.validatorpersoane import ValidatorP
from repository.manager_persoane import RepositoryPersoane

class ServiceP:
    def __init__(self, repo_persoane, validatorp):
        self.__repo_persoane = repo_persoane
        self.__validatorp = validatorp
        
    def findpersoana(self, personID):
        '''
        Functie ce returneaza o persoana dupa id-ul dat
        :param: personID
        :type: int
        :return: persoana cu personID-ul dat
        :rtype: Persoana
        '''
        return self.__repo_persoane.gaseste_persoana(personID)

    def get_persoane(self):
        '''
        Functie ce returneaza lista de persoane
        :param: none
        :return: lista de persoane
        :rtype: list
        '''
        return self.__repo_persoane.get_lista_persoane()
    
    
    def store_persoana(self, personID, nume, adresa):
        '''
        Functie ce adauga o persoana in lista
        :param: personID
        :type: int
        :param: nume
        :type: string
        :param: adresa
        :type: string 
        :return: none dar adauga persoana in lista
        '''
        persoana = Persoana(personID, nume, adresa)
        self.__validatorp.validare_persoana(persoana)
        self.__repo_persoane.adauga_persoana(persoana)

    def adauga_by_default_persoane(self):
        '''
        Functie ce adauga cateva persoane in lista
        :param: none
        :return: none dar adauga mai multe persoane in lista
        '''
        self.store_persoana(1, 'Secrier Cosmin', 'Strada Muresului')
        self.store_persoana(2, 'Alexandra', 'Strada Teiului')
        self.store_persoana(3, 'Raluca', 'Strada Tulcea')
        self.store_persoana(4, 'Andrei', 'Strada Lacului')
        self.store_persoana(5, 'Mihai', 'Strada Dragostei')

    def delete_persoana(self, personID):
        '''
        Functie ce sterge o persoana din lista
        :param: personID
        :type: int
        :return: none dar lista de persoane se modifica prin stergerea persoanei cu personID-ul dat
        '''
        self.__repo_persoane.sterge_persoana(personID)
    
    def modifica_persoana(self, personID, nume_nou, adresa_noua):
        '''
        Functie ce modifica o persoana din lista
        :param: personID
        :type: int
        :param: nume_nou
        :type: string
        :param: adresa_noua
        :type: string
        :return: none dar lista de persoane se modifica prin modificarea persoanei cu personID-ul dat
        '''
        self.__repo_persoane.modifica_persoana(personID, nume_nou, adresa_noua)

    def cauta_persoana(self, personID):
        '''
        Functie ce cauta daca o persoana exista in lista de persoane
        :param: personID
        :type: int
        :return: True/false
        :rtype: bool
        '''
        return self.__repo_persoane.cauta_persoana(personID)
    