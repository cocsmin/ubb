class ValidatorE:
    def __init__(self):
        pass
    def validare_eveniment(self, eveniment):
        '''
        Functie ce valideaza un eveniment
        :param: eveniment - obiect de tip Eveniment
        :return: -
        '''
        erori = []
        if eveniment.get_id() < 0:
            erori.append('ID-ul evenimentului nu poate fi negativ!')

        if eveniment.get_zi() < 1 or eveniment.get_zi() > 31:
            erori.append('Ziua evenimentului trebuie sa fie cuprinsa intre 1 si 31!')

        if eveniment.get_luna() < 1 or eveniment.get_luna() > 12:
            erori.append('Luna evenimentului trebuie sa fie cuprinsa intre 1 si 12!')

        if eveniment.get_an() < 2023:
            erori.append('Anul evenimentului trebuie sa fie cel putin 2023!')

        if eveniment.get_timp() < 0 or eveniment.get_timp() > 23:
            erori.append('Timpul evenimentului trebuie sa fie cuprins intre 0 si 23 ore!')

        if eveniment.get_descriere() == '':
            erori.append('Descrierea evenimentului nu poate fi vida!')

        if len(erori) > 0:
            raise ValueError(erori)
            

