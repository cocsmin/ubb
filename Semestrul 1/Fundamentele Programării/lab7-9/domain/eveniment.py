class Eveniment:
    def __init__(self, ID, data_zi, data_luna, data_an , timp, descriere):
        self.ID = ID
        self.zi = data_zi
        self.luna = data_luna
        self.an = data_an
        self.timp = timp
        self.descriere = descriere

    def data_corecta(self, data):
        '''
        Functie ce primeste ca parametru data si o returneaza sub alti 3 parametrii specifici pentru zi, luna, an
        :param: data
        :type: string
        '''
        data = data.split('/')
        zi = int(data[0])
        luna = int(data[1])
        an = int(data[2])
        return zi, luna, an
    

    def get_id(self):
        return self.ID
    
    def get_zi(self):
        return self.zi
    
    def get_luna(self):
        return self.luna
    
    def get_an(self):
        return self.an
    
    def get_timp(self):
        return self.timp
    
    def get_descriere(self):
        return self.descriere
    
    def set_zi(self, zi_noua):
        self.zi = zi_noua

    def set_luna(self, luna_noua):
        self.luna = luna_noua

    def set_an(self, an_nou):
        self.an = an_nou

    def set_timp(self, timp_nou):
        self.timp = timp_nou

    def set_descriere(self, descriere_noua):
        self.descriere = descriere_noua

    def set_id(self, ID_nou):
        self.ID = ID_nou

    def __str__(self):
        return str(self.get_id()) + ' ' + str(self.get_zi()) + '/' + str(self.get_luna()) + '/' + str(self.get_an()) + ' ' + str(self.get_timp()) + ' ' + self.get_descriere()

    def get_data(self):
        return str(self.zi) + '/' + str(self.luna) + '/' + str(self.an)