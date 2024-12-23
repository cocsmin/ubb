from service.serv import Service
from domain.melodie import Melodie
from repository.repo import *
class Console:
    def __init__(self, service_melodii):
        self.__service = service_melodii

    def print_menu(self):
        print('1. Modificati o melodie ')
        print('2. Creati aleator melodii')
        print('3. Exportati melodiile sortate')

    def afisare_melodii(lista_melodii):
        '''
        Functie ce permite afisarea listei de melodii
        :param lista_melodii - lista ce se doreste a fi afisata
        ;type list
        '''
        for melodie in lista_melodii:
            print(melodie)


    def run(self):
        ok = 'y'
        while ok == 'y' or ok == 'Y':
            #self.__service.
            self.print_menu()
            optiune = input("Introduceti optiunea dorita: \n")
            if optiune == '1':
                try:
                    titlu = input("Introduceti titlul melodiei pe care doriti sa o modificati: \n")
                    artist = input("Introduceti artistul al carei melodii doriti sa o modificati: \n")
                    gen_nou = input('Introduceti noul gen al melodiei: \n')
                    data_noua = input('Introduceti data noua a melodiei: \n')
                    self.__service.modificare_melodie(titlu, artist, gen_nou, data_noua)
                except ValueError as e:
                    print(e)
            elif optiune == '2':
                numar_melodii = input('Introduceti numarul de melodii pe care doriti sa le generati: \n')
                lista_titluri = input('Introduceti lista de titluri cu care doriti sa generati melodii: \n')
                lista_artisti = input('Introduceti lista de artisti cu care doriti sa generati melodii: \n')
                self.__service.creeaza_random(numar_melodii, lista_titluri, lista_artisti)
                print(numar_melodii)
            elif optiune == '3':
                pass

            else:
                print('Optiunea nu exista! \n')

            ok = input('Doriti sa continuati? (Y/N) \n')
