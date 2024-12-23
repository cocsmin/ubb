from domain.persoana import Persoana
class RepositoryPersoane:
    def __init__(self):
        self.__lista_persoane = []

    def get_lista_persoane(self):
        '''
        Functie ce returneaza lista de persoane
        :param: none
        :return: lista de persoane
        :rtype: list
        '''
        return self.__lista_persoane

    def adauga_persoana(self, persoana):
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
        persoana_gasita= self.gaseste_persoana(persoana.get_id())
        if persoana_gasita is not(None):
            raise ValueError("Exista deja persoana cu acest id.")
        self.__lista_persoane.append(persoana)
        

    def sterge_persoana(self, personID):
        '''
        Functie ce sterge o persoana din lista
        :param: personID
        :type: int
        :return: none dar lista de persoane se modifica prin stergerea persoanei cu personID-ul dat
        '''
        self.__lista_persoane = [persoana for persoana in self.__lista_persoane if persoana.get_id() != personID]
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
        for persoana in self.__lista_persoane:
            if persoana.get_id() == personID:
                persoana.set_nume(nume_nou)
                persoana.set_adresa(adresa_noua)

    def gaseste_persoana(self, id):
        '''
        Functie ce gaseste o persoana din lista
        :param: id
        :type: int
        :return: persoana cu id-ul dat
        :rtype: Persoana
        '''
        for persoana in self.__lista_persoane:
            if persoana.get_id() == id:
                return persoana
        return None  