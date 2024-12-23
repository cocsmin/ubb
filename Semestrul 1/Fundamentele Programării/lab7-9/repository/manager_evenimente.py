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