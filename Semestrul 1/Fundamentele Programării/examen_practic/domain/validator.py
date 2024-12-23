from domain.melodie import Melodie
class Validator:
    def __init__(self):
        pass

    def valideaza(self, melodie):
        '''
        Functie ce se asigura ca o melodie este valida (data corecta, gen rock pop sau jazz, trebuie sa apara artist si titlu)
        :param melodie - melodia ce urmeaza a fi validata
        :type melodie - Melodie
        '''
        erori = []
        data = melodie.get_data()
        if len(data) < 10:
            erori.append('Eroare! Data invalida!')
            raise ValueError(erori)
        if (data[2] != '.') or (data[5] != '.'):
            erori.append('Eroare! Data are un format invalid!')
        
        zi = int(data[0]) * 10 + int(data[1])
        luna = int(data[3]) * 10 + int(data[4])
        an = int(data[6]) * 1000 + int(data[7]) * 100 + int(data[8]) * 10 + int(data[9])
        if (zi < 1) or (zi > 31) or (luna < 1) or (luna > 12) or (an < 0) or (an > 2024):
            erori.append('Eroare! Data nu este corecta')

        genuri = ['Rock' ,'Pop', 'Jazz']
        ok = False
        for gen in genuri:
            if gen == melodie.get_gen():
                ok = True

        if not ok:
            erori.append('Eroare! Genul trebuie sa fie Rock/Pop/Jazz! ')

        if melodie.get_artist() == ' ':
            erori.append("Eroare! Trebuie sa existe un artist!")

        if melodie.get_titlu() == ' ':
            erori.append('Eroare! Trebuie sa existe un titlu!')

        if len(erori) > 0:
            raise ValueError(erori)
