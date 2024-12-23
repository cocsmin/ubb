from domain.melodie import Melodie
class Repository:
    def __init__(self):
        self.__lista_melodii = []

    def adaugare_melodie(self, melodie):
        '''
        Functie ce adauga o melodie in lista de melodii
        :param melodie - melodia ce se vrea adaugata
        :type melodie - Melodie
        '''
        self.__lista_melodii.append(melodie)

    def modificare_melodie(self, titlu, artist, gen_nou, data_noua):
        '''
        Functie ce modifica o melodie 
        :param titlu - titlul melodiei care se doreste a fi modificata
        :type titlu - str
        :param artist - artistul melodiei care se va modifica
        :type artist - str
        :param gen_nou - genul nou ce ii va fi atribuit melodiei
        :type gen_nou - str
        :param data_noua - data noua ce ii va fi atribuita melodiei
        :type data_noua - str (respecta formatul)
        :return - None dar modifica lista de melodii
        '''
        for melodie in self.__lista_melodii:
            if melodie.get_titlu() == titlu and melodie.get_artist() == artist:
                melodie.set_gen(gen_nou)
                melodie.set_data(data_noua)

    def get_lista_melodii(self):
        '''
        Functie ce returneaza lista de melodii
        :return lista_melodii
        :rtype list
        '''
        return self.__lista_melodii
        
class FileRepository(Repository):
    def __init__(self, nume_fisier):
        Repository.__init__(self)
        self.__nume_fisier = nume_fisier
        self.__incarcare_fisier()

    def __incarcare_fisier(self):
        '''
        Functie ce incarca datele din fisier
        '''
        try:
            with open(self.__nume_fisier, 'r') as f:
                linii = f.readlines()
                for linie in linii:
                    if linie == '\n':
                        break
                    elemente = linie.split(',')
                    elemente = [element.strip() for element in elemente]
                    titlu = elemente[0]
                    artist = elemente[1]
                    gen = elemente[2]
                    data = elemente[3]
                    melodie = Melodie(titlu, artist, gen, data)
                    Repository.adaugare_melodie(self, melodie)
        except IOError:
            print('Fisierul nu a putut fi gasit!')

    def __salvare_fisier(self):
        '''
        Functie ce salveaza modificarile in fisier
        '''
        with open (self.__nume_fisier, 'w') as f:
            for melodie in Repository.get_lista_melodii(self):
                linie = melodie.get_titlu() + ',' + melodie.get_artist() + ',' + melodie.get_gen() + ',' + melodie.get_data() + '\n'
                f.write(linie)

    def adaugare_melodie(self, melodie):
        '''
        Functie ce adauga o melodie in lista de melodii si salveaza in fisier
        :param melodie - melodia ce se vrea adaugata
        :type melodie - Melodie
        '''
        Repository.adaugare_melodie(self, melodie)
        self.__salvare_fisier()

    def modificare_melodie(self, titlu, artist, gen_nou, data_noua):
        '''
        Functie ce modifica o melodie si salveaza in fisier
        :param titlu - titlul melodiei care se doreste a fi modificata
        :type titlu - str
        :param artist - artistul melodiei care se va modifica
        :type artist - str
        :param gen_nou - genul nou ce ii va fi atribuit melodiei
        :type gen_nou - str
        :param data_noua - data noua ce ii va fi atribuita melodiei
        :type data_noua - str (respecta formatul)
        :return - None dar modifica lista de melodii
        '''
        Repository.modificare_melodie(self, titlu, artist, gen_nou, data_noua)
        self.__salvare_fisier()

    def get_lista_melodii(self):
        lista = Repository.get_lista_melodii(self)
        return lista