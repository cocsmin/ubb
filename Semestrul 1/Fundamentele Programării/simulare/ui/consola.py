from service.service_haine import ServiceHaine
class Consola:
    def __init__(self, service_haine):
        self.__haine = service_haine
    def __print_menu(self):
        '''
        Functie care afiseaza cele 2 functionalitati si optiunea de afisare disponibile in aplicatie
        '''
        print("1. Prima functionalitate")
        print('2. A doua functionalitate')
        print('3. Afisare comenzi existente')

    def afisare_haine(self, lista_haine):
        '''
        Functie ce afiseaza lista de haine
        :param lista_haine: lista de haine
        :type lista_haine: list
        '''
        for haina in lista_haine:
            print(haina)

    def afisare_comenzi(self, lista_comenzi):
        '''
        Functie ce afiseaza lista de comenzi
        :param lista_comenzi: lista de comenzi
        :type lista_comenzi: list
        '''
        for comanda in lista_comenzi:
            print(comanda)

    def run(self):
        ok = 'y'
        while ok == 'y' or ok == 'Y':
            self.__print_menu()
            optiune = input('Introduceti optiunea dorita:\n')
            if optiune == '1':
                tip_cautat = input("Introduceti tipul de haina cautat \n")
                self.afisare_haine(self.__haine.haine_tip(tip_cautat))
            elif optiune == '2':
                id_haina = input("Introduceti id-ul pentru haina cautata \n")
                nr_bucati = input('Introduceti numarul de bucati pentru haina cautata \n')
                nume_client = input("Introduceti numele clientului care efectueaza comanda \n")
                adresa = input("Introduceti adresa clientului \n")
                self.__haine.comanda_noua(id_haina, nr_bucati, nume_client, adresa)
            elif optiune == '3':
                self.afisare_comenzi(self.__haine.get_lista_comenzi())

            ok = input('Doriti sa continuati?(Y/N)\n')
