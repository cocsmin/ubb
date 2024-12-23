from repository.manager_evenimente import RepositoryEvenimente
from repository.manager_persoane import RepositoryPersoane

class RepositoryInscrieri:
    def __init__(self, repo_persoane, repo_evenimente):
        self.__repo_persoane = repo_persoane
        self.__repo_evenimente = repo_evenimente
        self.__lista_inscrieri = []
        
    def get_lista_inscrieri(self):
        '''
        Functie ce returneaza lista de inscrieri
        :param: none
        :return: lista de inscrieri
        :rtype: list
        '''
        return self.__lista_inscrieri
    
    def cauta_persoana(self, id_persoana):
        '''
        Functie ce cauta daca o persoana exista in lista de persoane
        :param: id_persoana
        :type: int
        :return: True/false
        :rtype: bool
        '''
        for persoana in self.__repo_persoane.get_lista_persoane():
            if persoana.get_id() == id_persoana:
                return True
        return False
    
    def cauta_eveniment(self, id_eveniment):
        '''
        Functie ce cauta daca un eveniment exista in lista de evenimente
        :param: id_eveniment
        :type: int
        :return: True/false
        :rtype: bool
        '''
        for eveniment in self.__repo_evenimente.get_lista_evenimente():
            if eveniment.get_id() == id_eveniment:
                return True
        return False
    
    def cauta_inscriere(self, id_persoana, id_eveniment):
        '''
        Functie ce cauta daca o inscriere exista in lista de inscrieri
        :param: id_persoana
        :type: int
        :param: id_eveniment
        :type: int
        :return: True/false
        :rtype: bool
        '''
        for persoana, eveniment in self.__lista_inscrieri:
            if persoana == id_persoana and eveniment == id_eveniment:
                return True
        return False

    def adauga_inscriere(self, id_persoana, id_eveniment):
        '''
        Functie ce adauga o inscriere in lista
        :param: id_persoana
        :type: int
        :param: id_eveniment
        :type: int
        :return: none dar adauga inscrierea in lista
        '''
        if self.cauta_persoana(id_persoana) == False:
            raise ValueError('Persoana cu id-ul dat nu exista!')
        if self.cauta_eveniment(id_eveniment) == False:
            raise ValueError('Evenimentul cu id-ul dat nu exista!')
        if self.cauta_inscriere(id_persoana, id_eveniment) == True:
            raise ValueError('Inscrierea exista deja!')
        self.__lista_inscrieri.append((id_persoana, id_eveniment))

    def get_evenimentele_persoanei(self, id):
        '''
        Functie ce returneaza lista de evenimente la care este inscrisa o persoana
        :param: id
        :type: int
        :return: lista de evenimente la care este inscrisa persoana cu id-ul dat
        :rtype: list
        '''
        eve = []
        for id_persoana, id_eveniment in self.__lista_inscrieri:
            if id_persoana == id:
                eve.append(id_eveniment)
        return eve
    
    def get_persoanele_evenimentului(self, id):
        '''
        Functie ce returneaza lista de persoane inscrise la un eveniment
        :param: id
        :type: int
        :return: lista de persoane inscrise la evenimentul cu id-ul dat
        :rtype: list
        '''
        pers = []
        for id_persoana, id_eveniment in self.__lista_inscrieri:
            if id_eveniment == id:
                pers.append(id_persoana)
        return pers


        
    