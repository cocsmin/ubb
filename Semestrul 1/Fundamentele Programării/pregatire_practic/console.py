from service.service_examene import Service
class Console:
    def __init__(self, lista_examene):
        self.__lista_examene = lista_examene

    def afisare_meniu(self):
        print("1 - Adauga examen")
        print("2 - Set data")
        print("5 - Afisare lista curenta de examene")

    def afisare_lista_examene(self, lista_examene):
        for ex in lista_examene:
            print(ex)

    def run(self):
        '''
        Da startul aplicatiei
        '''     
        ok = 'y'
        while ok == 'y' or ok == 'Y':
            self.afisare_meniu()
            optiune = input("Introduceti optiunea dorita: \n")
            if optiune == '1':
                try:
                    data = input("Introduceti data ")
                    ora = input("Introduceti ora ")
                    materia = input("Introduceti materia ")
                    tip = input("Introduceti tipul (examen/restanta) ")
                    self.__lista_examene.adauga_examen(data, ora, materia, tip)
                except ValueError as e:
                    print(e)

            elif optiune == "2":
                self.afisare_lista_examene(self.__lista_examene.sortare_examene())

            elif optiune =='3':
                data = input('Introduceti data: ')
                self.afisare_lista_examene(self.__lista_examene.afisare_tabel(data))

            elif optiune == '5':
                self.afisare_lista_examene(self.__lista_examene.get_lista_examene())
            else:
                print("Optiunea nu exista!\n")

            ok = input("Continuati? (y/n)\n")