from domain.haine import Haina
class FileRepositoryHaine:
    def __init__(self, nume_fisier):
        self.__lista_haine = []
        self.__nume_fisier = nume_fisier
        self.__load_file()

    def __load_file(self):
        '''
        Functie ce incarca datele din fisierul dat
        '''
        with open(self.__nume_fisier,'r') as f:
            linii = f.readlines()
            for linie in linii:
                date = linie.split(',')
                date = [element.strip() for element in date]
                id = int(date[0])
                fabricant = date[1]
                tip = date[2]
                pret = int(date[3])
                haina_noua = Haina(id, fabricant, tip, pret)
                self.__lista_haine.append(haina_noua)

    def get_lista(self):
        '''
        Functie ce returneaza lista de haine
        :return : lista de haine
        '''
        return self.__lista_haine