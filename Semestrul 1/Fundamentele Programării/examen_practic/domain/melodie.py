class Melodie:
    def __init__(self, titlu, artist, gen, data):
        self.titlu = titlu
        self.artist = artist
        self.gen = gen
        self.data = data

#gettere
    def get_titlu(self):
        return self.titlu
    
    def get_artist(self):
        return self.artist
    
    def get_gen(self):
        return self.gen
    
    def get_data(self):
        return self.data
    

#settere
    def set_titlu(self, titlu_nou):
        self.titlu = titlu_nou

    def set_artist(self, artist_nou):
        self.artist = artist_nou

    def set_gen(self, gen_nou):
        self.gen = gen_nou

    def set_data(self, data_noua):
        self.data = data_noua


    def __str__(self):
        return str(self.titlu) + ',' + str(self.artist) + ',' + str(self.gen) + ',' + str(self.data)