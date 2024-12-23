class Haina:
    def __init__(self, id, fabricant, tip, pret):
        '''
        Functia de initializare
        :param id: id-ul hainei
        :type id: int
        :param fabricant: denumirea fabricantului
        :type fabricant: str
        :param tip: tipul de haina
        :type tip: str
        :param pret: pretul hainei
        :type pret: int
        '''
        self.__id = id
        self.__fabricant = fabricant
        self.__tip = tip
        self.__pret = pret

    #gettere, functii ce returneaza un anumit atribut al unui obiect 'haina'

    def get_id(self):
        return self.__id
    
    def get_fabricant(self):
        return self.__fabricant
    
    def get_tip(self):
        return self.__tip
    
    def get_pret(self):
        return self.__pret
    
    #settere, functii ce modifica un anumit atribut al unui obiect 'haina'

    def set_fabricant(self, fabricant_nou):
        self.__fabricant = fabricant_nou

    def set_tip(self, tip_nou):
        self.__tip = tip_nou

    def set_pret(self, pret_nou):
        self.__pret = pret_nou

    def __str__(self):
        '''
        Functie ce returneaza un obiect de tip haina intr-un mod prezentabil
        '''
        return str(self.__id) + ', ' + self.__fabricant + ', ' + self.__tip + ', ' + str(self.__pret)
    
