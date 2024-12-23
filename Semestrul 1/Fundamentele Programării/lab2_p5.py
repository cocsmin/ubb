n = int(input('Introduceti numarul n\n'))
def verifica_prim(x):
    numar_divizori = 0
    for i in range(1,x+1):
        if x % i == 0:
            numar_divizori += 1
    if (numar_divizori > 2):
        return False
    else:
        return True

for i in range (n,n**n):
    if (verifica_prim(i)):
        p = i
        break

for i in range (p+2,n**n):
    if (verifica_prim(i) and (i == p + 2)):
        q=i
        print(p)
        print(q)
        break
    p=p+2


