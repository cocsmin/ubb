class ValidatorP:
    def __init__(self):
        pass

    def validare_persoana(self, persoana):
        '''
        Functie ce valideaza o persoana
        :param: persoana - obiect de tip Persoana
        :return: -
        '''
        erori = []
        if persoana.get_id() < 0:
            erori.append('ID-ul persoanei nu poate fi negativ!')

        if persoana.get_nume() == '':
            erori.append('Numele persoanei nu poate fi vid!')

        if persoana.get_adresa() == '':
            erori.append('Adresa persoanei nu poate fi vida!')

        if len(erori) > 0:
            raise ValueError(erori)
        
    