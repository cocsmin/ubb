# Pentru un șir cu n numere întregi care conține și duplicate, 
# să se determine elementul majoritar (care apare de mai mult 
# de n / 2 ori). De ex. 2 este elementul majoritar 
# în șirul [2,8,7,2,2,5,2,3,1,2,2].

from collections import Counter

#timp - O(n)
#spatiu - O(n)
def element_majoritar(sir):
    #sir - list
    #returns - int
    frecventa = Counter(sir)
    prag = len(sir) // 2
    for element, count in frecventa.items():
        if count > prag:
            return element
    
    return None


#timp - O(n)
#spatiu - O(1)
def element_majoritar_gpt(sir):
    # Faza 1: Identificarea unui candidat
    candidat = None
    contor = 0
    
    for numar in sir:
        if contor == 0:
            candidat = numar
            contor = 1
        elif numar == candidat:
            contor += 1
        else:
            contor -= 1
    
    # Faza 2: Verificarea dacă candidatul este majoritar
    if candidat is not None:
        count = sir.count(candidat)  # Numărăm aparițiile candidatului
        if count > len(sir) // 2:
            return candidat
    
    return None  # Dacă nu există niciun element majoritar

def test():
    sir = [2, 8, 7, 2, 2, 5, 2, 3, 1, 2, 2]
    assert element_majoritar(sir) == 2

test()