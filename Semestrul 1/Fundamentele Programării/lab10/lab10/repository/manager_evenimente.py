from domain.eveniment import Eveniment
class RepositoryEvenimente:
    def __init__(self):
        self.__lista_evenimente = []
    
    def get_lista_evenimente(self):
        '''
        Functie ce returneaza lista de evenimente
        :param: none
        :return: lista de evenimente
        :rtype: list
        '''
        return self.__lista_evenimente
    
    
    def adauga_eveniment(self, eveniment):
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
        #zi, luna, an = self.data_corecta(data)
        #eveniment = Eveniment(ID, zi, luna, an, timp, descriere)
        eveniment_cautat = self.gaseste_eveniment(eveniment.get_id())
        if eveniment_cautat is not None:
            raise ValueError("ID-ul exista deja!")
        self.__lista_evenimente.append(eveniment)

    def sterge_eveniment(self, ID):
        '''
        Functie ce sterge un eveniment din lista
        :param: ID
        :type: int
        :return: none dar lista de evenimente se modifica prin stergerea evenimentului cu ID-ul dat
        '''
        self.__lista_evenimente = [eveniment for eveniment in self.__lista_evenimente if eveniment.get_id() != ID]

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
        for eveniment in self.__lista_evenimente:
            if eveniment.get_id() == ID:
                eveniment.set_zi(zi)
                eveniment.set_luna(luna)
                eveniment.set_an(an)
                eveniment.set_timp(timp)
                eveniment.set_descriere(descriere)
        
    def __str__(self):
        string =''
        for eveniment in self.__lista_evenimente:
            string+= eveniment.__str__() + ','

    def gaseste_eveniment(self, id):
        '''
        Functie ce gaseste un eveniment din lista
        :param: id
        :type: int
        :return: evenimentul cu id-ul dat
        :rtype: Eveniment
        '''
        for eveniment in self.__lista_evenimente:
            if eveniment.get_id() == id:
                return eveniment
        return None
    
class FileRepositoryEvenimente(RepositoryEvenimente):
    def __init__(self, nume_fisier):
        RepositoryEvenimente.__init__(self)
        self.__nume_fisier = nume_fisier
        self.__incarcare_fisier()

    def __incarcare_fisier(self):
        with open(self.__nume_fisier, 'r') as f:
            linii = f.readlines()
            for linie in linii:
                if linie == '\n':
                   break
                elemente = linie.split(' ')
                elemente = [element.strip() for element in elemente]
                id = int(elemente[0])
                data = elemente[1]
                zi, luna, an = data.split('/')
                zi = int(zi)
                luna = int(luna)
                an = int(an)
                timp = int(elemente[2])
                descriere = elemente[3]
                eveniment = Eveniment(id, zi, luna, an, timp, descriere)
                RepositoryEvenimente.adauga_eveniment(self, eveniment)

    def __salvare_fisier(self):
        with open(self.__nume_fisier, 'w') as f:
            lista_evenimente = RepositoryEvenimente.get_lista_evenimente(self)
            for eveniment in lista_evenimente:
                f.write(eveniment.__str__() + '\n')

    def adauga_eveniment(self, eveniment):
        RepositoryEvenimente.adauga_eveniment(self, eveniment)
        self.__salvare_fisier()

    def adauga_by_default(self):
        self.store_eveniment(1, 10, 2, 2024, 2, 'Majorat')
        self.store_eveniment(2, 5, 3, 2024, 4, 'Meeting')
        self.store_eveniment(3, 9, 12, 2024, 3, 'Nunta')
        self.store_eveniment(4, 3, 7, 2024, 6, 'Botez')
        self.store_eveniment(5, 8, 10, 2024, 4, 'Petrecere')

    def sterge_eveniment(self, ID):
        '''
        Functie ce sterge un eveniment din lista
        :param: ID
        :type: int
        :return: none dar lista de evenimente se modifica prin stergerea evenimentului cu ID-ul dat
        :rtype: -
        '''
        RepositoryEvenimente.sterge_eveniment(self, ID)
        self.__salvare_fisier()

    def modifica_eveniment(self, ID, zi_noua, luna_noua, an_nou, timp_nou, descriere_noua):
        '''
        Functie ce modifica un eveniment din lista
        :param: ID
        :type: int
        :param: zi_noua
        :type: int
        :param: luna_noua
        :type: int
        :param: an_nou
        :type: int
        :param: timp_nou
        :type: int
        :param: descriere_noua
        :type: string
        :return: none dar lista de evenimente se modifica prin modificarea evenimentului cu ID-ul dat
        :rtype: -
        '''
        RepositoryEvenimente.modifica_eveniment(self, ID, zi_noua, luna_noua, an_nou, timp_nou, descriere_noua)
        self.__salvare_fisier()

    def get_lista_evenimente(self):
        '''
        Functie ce returneaza lista de evenimente
        :param: none
        :return: lista de evenimente
        :rtype: list
        '''
        return RepositoryEvenimente.get_lista_evenimente(self)
    
    def gaseste_eveniment(self, ID):
        '''
        Functie ce gaseste un eveniment din lista
        :param: ID
        :type: int
        :return: evenimentul cu ID-ul dat
        :rtype: Eveniment
        '''
        return RepositoryEvenimente.gaseste_eveniment(self, ID)
