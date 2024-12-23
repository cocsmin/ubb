class Persoana:
    def __init__(self, personID, nume, adresa):
        self.personID = personID
        self.nume = nume
        self.adresa = adresa

    def get_id(self):
        return self.personID
    
    def get_nume(self):
        return self.nume
    
    def get_adresa(self):
        return self.adresa
    
    def set_nume(self, nume_nou):
        self.nume = nume_nou

    def set_adresa(self, adresa_noua):
        self.adresa = adresa_noua

    def set_id(self, personID_nou):
        self.personID = personID_nou

    def __str__(self):
        return str(self.get_id()) + ' ' + self.get_nume() + ' ' + self.get_adresa()

    