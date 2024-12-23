class Examen:
    def __init__(self, data, ora, materia, tip):
        self.data = data
        self.ora = ora
        self.materia = materia
        self.tip = tip
    
    def get_data(self):
        return self.data
    
    def get_zi(self):
        dat = self.data.split('.')
        return dat[0]
    
    def get_luna(self):
        dat = self.data.split('.')
        return dat[1]

    def get_ora(self):
        return self.ora
    
    def get_ora_exacta(self):
        min = self.ora.split(':')
        return min[0]
    
    def get_minut_exact(self):
        min = self.ora.split(':')
        return min[1]

    def get_materia(self):
        return self.materia
    
    def get_tip(self):
        return self.tip
    
    def __str__(self):
        return str(self.get_data()) + '/' + str (self.get_ora()) + '/' + str(self.get_materia()) + '/' + str(self.get_tip())


