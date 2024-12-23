from service.servicee import ServiceE
from service.servicep import ServiceP
from service.inscrieri import ServiceInscrieri
class Consola:
    '''
    Clasa ce contine functiile de afisare a meniului si de citire a unei optiuni
    '''
    def __init__(self, lista_persoane, lista_evenimente, lista_inscrieri):
        self.__lista_persoane = lista_persoane
        self.__lista_evenimente = lista_evenimente
        self.__lista_inscrieri = lista_inscrieri
                 
    def afisare_meniu(self):
        '''
        Functie ce afiseaza optiunile disponibile in aplicatie
        :param: none
        :return: none
        '''
        print('1. Operatii pe lista de persoane')
        print('1.1. Adaugare persoana')
        print('1.2. Stergere persoana')
        print('1.3. Modificare persoana')
        print('2. Operatii pe lista de evenimente')
        print('2.1. Adaugare eveniment')
        print('2.2. Stergere eveniment')
        print('2.3. Modificare eveniment')
        print('3. Cautari')
        print('3.1. Cautare persoana in lista')
        print('3.2. Cautare eveniment in lista')
        print('4. Inscriere persoana la eveniment')
        print('5. Rapoarte')
        print('5.1. Lista de evenimente la care participa o persoana ordonat alfabetic dupa descriere')
        print('5.2. Lista de evenimente la care participa o persoana ordonat alfabetic dupa data')
        print('5.3. Persoanele participante la cele mai multe evenimente')
        print('5.4. Primele 20% evenimente cu cei mai mulți participanți (descriere, număr participanți)')
        print('10. Generarea a [n] evenimente')
    def optiune(self):
        '''
        Functie ce citeste optiunea aleasa de utilizator
        :param: none
        :return: lista de optiuni
        :rtype: list
        '''
        lista_string = input('Introduceti optiunea dorita(numerele trebuie sa fie separate prin punct, precum 1.1):\n')
        lista_optiuni = []
        lista_optiuni = [int(element) for element in lista_string.split('.')]
        return lista_optiuni

    def prima_optiune(self, lista):
        '''
        Functia returneaza prima optiune aleasa
        :param lista: lista de optiuni
        :type lista: list
        :return: prima optiune
        :rtype: int
        '''
        return lista[0]

    def adoua_optiune(self, lista):
        '''
        Functia returneaza a doua optiune aleasa
        :param lista: lista de optiuni
        :type lista: list
        :return: a doua optiune
        :rtype: int
        '''

        return lista[1]

    def afisare_lista_persoane(self, lista_persoane):
        '''
        Functie ce afiseaza lista de persoane
        :param: lista_persoane
        :type: list
        :return: none
        '''
        for persoana in lista_persoane:
            print(persoana)

    def afisare_lista_evenimente(self, lista_evenimente):
        '''
        Functie ce afiseaza lista de evenimente
        :param: lista_evenimente
        :type: list
        :return: none
        '''
        for eveniment in lista_evenimente:
            print(eveniment)

    def afisare_lista_inscrieri(self, lista_inscrieri):
        '''
        Functie ce afiseaza lista de inscrieri
        :param: lista_inscrieri
        :type: list
        :return: none
        '''
        for inscriere in lista_inscrieri:
            persoana = self.__lista_persoane.findpersoana(inscriere[0])
            eveniment = self.__lista_evenimente.findeveniment(inscriere[1])
            print(persoana, ' --- ', eveniment)


    def run(self):
        '''
        Functie ce ruleaza aplicatia
        :param: none
        :return: none
        '''
        ok = 'y'
        while ok == 'y' or ok == 'Y':
            self.afisare_meniu()
            lista_optiuni = self.optiune()
            if self.prima_optiune(lista_optiuni) == 1:
                if self.adoua_optiune(lista_optiuni) == 1:
                    try:
                        personID = int(input('Introduceti ID-ul persoanei: '))
                        nume = input('Introduceti numele persoanei: ')
                        adresa = input('Introduceti adresa persoanei: ')
                        self.__lista_persoane.store_persoana(personID, nume, adresa)
                        self.afisare_lista_persoane(self.__lista_persoane.get_persoane())
                    except ValueError as e:
                        print(e)
                
                elif self.adoua_optiune(lista_optiuni) == 2:
                    try:
                        personID = int(input('Introduceti ID-ul persoanei pe care doriti sa o stergeti din lista: '))
                        self.__lista_persoane.delete_persoana(personID)
                        self.afisare_lista_persoane(self.__lista_persoane.get_persoane())

                    except ValueError as e:
                        print(e)
                
                elif self.adoua_optiune(lista_optiuni) == 3:
                    try:
                        personID = int(input('Introduceti ID-ul persoanei pe care doriti sa o modificati: '))
                        nume_nou = input('Introduceti noul nume al persoanei: ')
                        adresa_noua = input('Introduceti noua adresa a persoanei: ')
                        self.__lista_persoane.modifica_persoana(personID, nume_nou, adresa_noua)
                        self.afisare_lista_persoane(self.__lista_persoane.get_persoane())

                    except ValueError as e:
                        print(e)

            elif self.prima_optiune(lista_optiuni) == 2:
                if self.adoua_optiune(lista_optiuni) == 1:
                    try:
                        ide = int(input('Introduceti ID-ul evenimentului: '))
                        data = input('Introduceti data evenimentului: ')
                        timp = int(input('Introduceti timpul evenimentului(in ore): '))
                        descriere = input('Introduceti descrierea evenimentului: ')
                        data = data.split('/')
                        zi = int(data[0])
                        luna = int(data[1])
                        an = int(data[2])
                        self.__lista_evenimente.store_eveniment(ide, zi, luna, an, timp, descriere)
                        self.afisare_lista_evenimente(self.__lista_evenimente.get_evenimente())

                    except ValueError as e:
                        print(e)

                elif self.adoua_optiune(lista_optiuni) == 2:
                    try:
                        ide = int(input('Introduceti ID-ul evenimentului pe care doriti sa il stergeti din lista: '))
                        self.__lista_evenimente.delete_eveniment(ide)
                        self.afisare_lista_evenimente(self.__lista_evenimente.get_evenimente())
                    
                    except ValueError as e:
                        print(e)

                elif self.adoua_optiune(lista_optiuni) == 3:
                    try:
                         ide = int(input('Introduceti ID-ul evenimentului pe care doriti sa il modificati: '))
                         data_noua = input('Introduceti noua data a evenimentului: ')
                         data_noua = data_noua.split('/')
                         zi = int(data_noua[0])
                         luna = int(data_noua[1])
                         an = int(data_noua[2])
                         timp_nou = int(input('Introduceti noul timp al evenimentului: '))
                         descriere_noua = input('Introduceti noua descriere a evenimentului: ')
                         self.__lista_evenimente.modifica_eveniment(ide, zi, luna, an, timp_nou, descriere_noua)
                         self.afisare_lista_evenimente(self.__lista_evenimente.get_evenimente())

                    except ValueError as e:
                        print(e)
            
            elif self.prima_optiune(lista_optiuni) == 3:
                if self.adoua_optiune(lista_optiuni) == 1:
                    try:
                        personID = int(input('Introduceti ID-ul persoanei pe care doriti sa o cautati: '))
                        print(self.__lista_persoane.findpersoana(personID))
                    except ValueError as e:
                        print(e)

                elif self.adoua_optiune(lista_optiuni) == 2:
                    try:
                        ide = int(input('Introduceti ID-ul evenimentului pe care doriti sa il cautati: '))
                        print(self.__lista_evenimente.findeveniment(ide))

                    except ValueError as e:
                        print(e)

            elif self.prima_optiune(lista_optiuni) == 4:
                personID = int(input('Introduceti ID-ul persoanei pe care doriti sa o inscrieti la eveniment: '))
                ide = int(input('Introduceti ID-ul evenimentului la care doriti sa o inscrieti pe persoana: '))
                self.__lista_inscrieri.adauga_inscriere(personID, ide)
                self.afisare_lista_inscrieri(self.__lista_inscrieri.get_inscrieri())

            elif self.prima_optiune(lista_optiuni) == 5:
                if self.adoua_optiune(lista_optiuni) == 1:
                    personID = int(input('Introduceti ID-ul persoanei: '))
                    persoana = self.__lista_persoane.findpersoana(personID)
                    lista = self.__lista_inscrieri.evenimente_ordonate_descriere(personID)
                    for eveniment in lista:
                        print(eveniment)
                elif self.adoua_optiune(lista_optiuni) == 2:
                    personID = int(input('Introduceti ID-ul persoanei: '))
                    persoana = self.__lista_persoane.findpersoana(personID)
                    lista = self.__lista_inscrieri.evenimente_ordonate_data(personID)
                    for eveniment in lista:
                        print(eveniment)

                elif self.adoua_optiune(lista_optiuni) == 3:
                    pers = self.__lista_inscrieri.persoane_inscrise_multe_evenimente()
                    for id in pers:
                        print(self.__lista_persoane.findpersoana(id))

                elif self.adoua_optiune(lista_optiuni) == 4:
                    eve = self.__lista_inscrieri.evenimente_multi_participanti()
                    lungime = len(eve)/5
                    poz_cur = 0
                    for id in eve:
                        if poz_cur <= lungime:
                            ev = self.__lista_evenimente.findeveniment(id)
                            print(ev.get_descriere())
                        poz_cur += 1

            elif self.prima_optiune(lista_optiuni) == 10:
                n = int(input('Introduceti numarul de evenimente pe care doriti sa le generati folosind functia RANDOM\n'))
                self.__lista_evenimente.generaree(n)
                self.afisare_lista_evenimente(self.__lista_evenimente.get_evenimente())

            print('Doriti sa continuati? (Y/N)')
            ok = input()
