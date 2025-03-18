# Considerându-se o matrice cu n x m elemente binare (0 sau 1) 
# sortate crescător pe linii, să se identifice indexul liniei care 
# conține cele mai multe elemente de 1.
# De ex. în matricea
# [[0,0,0,1,1],
# [0,1,1,1,1],
# [0,0,1,1,1]]
# a doua linie conține cele mai multe elemente 1.

#timp - O(n*m)
#spatiu - O(1)

def numar_de_unu(matrice, linie, m):
    #matrice - vector bidimensional
    #linie - int
    #m - int
    #returns - int
    index = 0
    for j in range(m):
        if matrice[linie][j] == 1:
            return m - index
        
        index += 1

def cei_mai_multi(matrice, n, m):
    #matrice - vector bidimensional
    #n - int
    #m - int
    #returns - int
    maxim = -1
    indice = -1
    for i in range(n):
        if numar_de_unu(matrice, i, m) > maxim:
            maxim = numar_de_unu(matrice, i, m)
            indice = i
    
    return indice + 1

#timp - O(nlogm)
#spatiu - O(1)

#GPT
def prima_pozitie_1(linie):
    """Returnează indexul primului 1 din linia sortată (sau m dacă nu există)."""
    st, dr = 0, len(linie) - 1
    while st <= dr:
        mid = (st + dr) // 2
        if linie[mid] == 1:
            dr = mid - 1  # Caut mai la stânga
        else:
            st = mid + 1  # Caut mai la dreapta
    return st  # Primul index cu 1 sau m dacă nu există

def linia_cu_cei_mai_multi_unu(matrice):
    """Returnează indexul liniei cu cei mai mulți de 1."""
    max_unu, index_linie = 0, -1
    for i, linie in enumerate(matrice):
        pozitie_1 = prima_pozitie_1(linie)
        nr_unu = len(linie) - pozitie_1  # Nr. de 1 = total - index primul 1
        if nr_unu > max_unu:
            max_unu, index_linie = nr_unu, i
    return index_linie

def test():
    matrice = [[0,0,0,1,1],
               [0,1,1,1,1],
               [0,0,1,1,1]]
    assert(cei_mai_multi(matrice, 3, 5) == 2)

test()

    