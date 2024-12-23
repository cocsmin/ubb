from domain.examen import Examen
class Manager:
    def __init__(self):
        self.lista_examene = []

    def get_lista_examene(self):
        '''
        Functie ce returneaza lista de examene
        return: lista de examene
        rtype: list
        '''
        return self.lista_examene
    
    def gaseste_examen(self, materie):
        '''
        Functie ce cauta un examen in lista curentra de examene
        :param examen
        :type examen
        :return examenul daca l a gasit sau none

        '''
        for ex in self.lista_examene:
            if ex.get_materia() == materie:
                return ex
        return None
    def adauga_examen(self, examen):
        '''
        Functie prin care se adauga un examen in lista
        param: examen
        type: examen

        '''
        ex = self.gaseste_examen(examen.get_materia())
        if ex is not (None):
            if ex.get_tip() == examen.get_tip():
                raise ValueError("Nu pot exista 2 examene de acelasi fel la aceeasi materie")
        self.lista_examene.append(examen)


class FileManager(Manager):
    def __init__(self, nume_fisier):
        Manager.__init__(self)
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
                    
                    elemente = linie.split('/')
                    elemente = [element.strip() for element in elemente]
                    data = elemente[0]
                    ora = elemente[1]
                    materia = elemente[2]
                    tip = elemente[3]
                    examen = Examen(data, ora, materia, tip)
                    Manager.adauga_examen(self, examen)
        except IOError:
            print("Fisierul nu exista!")

    def __salvare_fisier(self):
        '''
        Functie ce salveaza datele in fisier
        '''
        with open(self.__nume_fisier, 'w') as f:
            lista_examene = Manager.get_lista_examene(self)
            for examen in lista_examene:
                linie = examen.get_data() + "/" + examen.get_ora() + '/' + examen.get_materia() + '/' + examen.get_tip() + '\n'
                f.write(linie)


    def adauga_examen(self, examen):
        Manager.adauga_examen(self, examen)
        self.__salvare_fisier()

    def get_lista_examene(self):
        return Manager.get_lista_examene(self)
    
    def gaseste_examen(self, materie):
        return Manager.gaseste_examen(self, materie)