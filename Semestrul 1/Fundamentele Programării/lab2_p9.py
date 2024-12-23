n = int(input('Introduceti numarul n\n'))
def calculeaza_palindrom(numar):
    pal = 0
    while numar != 0:
        cifra = numar % 10
        pal = pal * 10 + cifra
        numar = numar // 10
    return pal
aux =  n
n = calculeaza_palindrom(n)
print("Palindromul numarului", aux , 'este', n)