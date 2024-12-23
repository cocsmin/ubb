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
    

class FileRepositoryPersoane(RepositoryPersoane):
    def __init__(self, nume_fisier):
        RepositoryPersoane.__init__(self)
        self.__nume_fisier = nume_fisier
        self.__incarcare_fisier()

    def __incarcare_fisier(self):
        '''
        Functie ce incarca datele din fisier
        :param: none
        :return: none dar incarca datele din fisier
        '''
        try:
            with open(self.__nume_fisier, 'r') as f:
                linii = f.readlines()
                for linie in linii:
                    if linie == '\n':
                        break
                    elemente = linie.split(',')
                    elemente = [element.strip() for element in elemente]
                    personID = int(elemente[0])
                    nume = elemente[1]
                    adresa = elemente[2]
                    persoana = Persoana(personID, nume, adresa)
                    RepositoryPersoane.adauga_persoana(self, persoana)
        except IOError:
            print("Fisierul nu exista!")

    def __salvare_fisier(self):
        '''
        Functie ce salveaza datele in fisier
        :param: none
        :return: none dar salveaza datele in fisier
        '''
        with open(self.__nume_fisier, 'w') as f:
            lista_persoane = RepositoryPersoane.get_lista_persoane(self)
            for persoana in lista_persoane:
                linie = str(persoana.get_id()) + ',' + persoana.get_nume() + ',' + persoana.get_adresa() + '\n'
                f.write(linie)

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
        RepositoryPersoane.adauga_persoana(self, persoana)
        self.__salvare_fisier()

    def sterge_persoana(self, personID):
        '''
        Functie ce sterge o persoana din lista
        :param: personID
        :type: int
        :return: none dar lista de persoane se modifica prin stergerea persoanei cu personID-ul dat
        '''
        RepositoryPersoane.sterge_persoana(self, personID)
        self.__salvare_fisier()

    def adauga_by_default_persoane(self):
        pers = Persoana(1, 'Secrier Cosmin', 'Strada Muresului')
        self.adauga_persoana(pers)
        pers = Persoana(2, 'Alexandra', 'Strada Teiului')
        self.adauga_persoana(pers)
        pers = Persoana(3, 'Raluca', 'Strada Tulcea')
        self.adauga_persoana(pers)
        pers = Persoana(4, 'Andrei', 'Strada Lacului')
        self.adauga_persoana(pers)
        pers = Persoana(5, 'Mihai', 'Strada Dragostei')
        self.adauga_persoana(pers)


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
        RepositoryPersoane.modifica_persoana(self, personID, nume_nou, adresa_noua)
        self.__salvare_fisier()

    def get_lista_persoane(self):
        return RepositoryPersoane.get_lista_persoane(self)

    def gaseste_persoana(self, id):
        '''
        Functie ce gaseste o persoana din lista
        :param: id
        :type: int
        :return: persoana cu id-ul dat
        :rtype: Persoana
        '''
        return RepositoryPersoane.gaseste_persoana(self, id)