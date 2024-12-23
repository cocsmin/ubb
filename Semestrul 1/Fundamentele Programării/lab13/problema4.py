#Se dă o listă de numere întregi a1,...an, determinați toate sub-secvențele 
#(ordinea elementelor este menținută) strict crescătoare.
def consistent(varianta):
    '''
    Verifica daca toate elementele din subsir sunt in ordine crescatoare
    '''
    if len(varianta) >= 1:
        for i in range(len(varianta)-1):
            if varianta[i] >= varianta[i+1]:
                return False
        return True
    
def solutie(varianta, k, subsir):
    '''
    Verifica daca subsirul este de lungime k si daca nu a mai fost gasit pana acum
    '''
    if len(varianta) != k:
        return False
    for sir in subsir:
        if sir == varianta:
            return False
    return True

def backtrack(start, lista, k, subsir):
    varianta = []
    for i in range(start, len(lista)):
        varianta.append(lista[i])
        if consistent(varianta):
            if solutie(varianta, k, subsir):
                print(varianta)
                subsir.append(varianta)
                varianta = []
                backtrack(start + 1, lista, k , subsir)
def varianta_iterativa(lista):
    for i in range(len(lista)):
        varianta = []
        varianta.append(lista[i])
        for j in range(i + 1, len(lista)):
            if lista[j] > varianta[-1]:
                varianta.append(lista[j])
                print(varianta)
            else :
                break
n = int(input("Introduceti numarul de elemente din lista: "))
lista = []
for i in range(n):
    lista.append(int(input("Introduceti elementul: ")))
print("Rezultatul cu varianta recursiva")
subsir = []
for k in range(2, n):
    backtrack(0, lista, k, subsir)
print("Rezultatul cu varianta iterativa")
varianta_iterativa(lista)
