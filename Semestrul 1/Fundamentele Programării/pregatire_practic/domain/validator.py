from domain.examen import Examen
class ValidatorExamen:
    def __init__(self):
        pass

    def validate_data(self, examen):
        '''
        Functie ce valideaza datele de intrare si se asigura ca e totul in regula
        :param examen
        :type examen
        :raises : ValueError pentru orice nu e in regula
        '''
        erori = []
        ok = 1
        data_examen = examen.get_data()
        if data_examen[2] != '.':
            ok = 0

        if not ok:
            erori.append("Data nu este in formatul potrivit!")

        if (int(examen.get_luna()) > 12 or int(examen.get_luna()) < 1):
            erori.append("Data nu este corecta!")

        if (int(examen.get_zi()) > 31 or int(examen.get_zi()) < 1):
            erori.append("Data nu este corecta!")

        ora_examen = examen.get_ora()
        if ora_examen[2] != ':':
            ok = 0

        if not ok:
            erori.append("Ora nu e in format")

        if (int(examen.get_ora_exacta()) > 23 or int(examen.get_ora_exacta()) < 0):
            erori.append("Ora nu e corecta")

        if len(erori) > 0:
            raise ValueError(erori)        
        