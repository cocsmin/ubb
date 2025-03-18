# Să se genereze toate numerele (în reprezentare binară) cuprinse 
# între 1 și n. De ex. dacă n = 4, numerele sunt: 1, 10, 11, 100.

def binar(n):
    #n - int
    #returns - string
    rezultat = ""
    while n > 0:
        rezultat = str(n % 2) + rezultat
        n = n // 2 
    return rezultat

def numere_binare(n):
    #n - int
    #returns - list
    lista_rezultat = []
    for i in range(1, n+1):
        lista_rezultat.append(binar(i))

    return lista_rezultat

#timp - O(n)
#spatiu - O(n)
def numere_binare_gpt(n):
    return [bin(i)[2:] for i in range(1, n + 1)]


def test():
    n = 4
    assert numere_binare(n) == ['1', '10', '11', '100']

test()