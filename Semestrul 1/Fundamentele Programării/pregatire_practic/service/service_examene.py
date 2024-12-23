from repository.repo_examene import *
from domain.validator import *
from datetime import date
class Service:
    def __init__(self, repo_examene, validator):
        self.__repo_examene = repo_examene
        self.__validator = validator

    def gaseste_examen(self, materie):
        return self.__repo_examene.gaseste_examen(materie)

    def get_lista_examene(self):
        return self.__repo_examene.get_lista_examene()
    
    def adauga_examen(self, data, ora, materie, tip):
        examen = Examen(data, ora , materie, tip)
        self.__validator.validate_data(examen)
        self.__repo_examene.adauga_examen(examen)

    def sortare_examene(self):
        lista_noua = []
        lista = self.__repo_examene.get_lista_examene()
        data = date.today()
        for ex in lista:
            if (ex.get_luna == data.month) and (ex.get_zi == data.day + 1):
                lista_noua.append(ex)
        
        return sorted(lista_noua, key = lambda ex : ex.get_ora())
    
    def afisare_tabel(self, data):
        lista_noua = []
        data_noua = data.split('.')
        zi = int(data_noua[0])
        for ex in self.__repo_examene.get_lista_examene():
            if (int(ex.get_zi()) >= zi) and (int(ex.get_zi()) <= zi + 3):
                lista_noua.append(ex)

        return lista_noua