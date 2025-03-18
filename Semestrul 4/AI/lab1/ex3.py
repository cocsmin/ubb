# Să se determine produsul scalar a doi vectori rari care conțin numere reale. 
# Un vector este rar atunci când conține multe elemente nule. 
# Vectorii pot avea oricâte dimensiuni. De ex. produsul scalar a 2 vectori 
# unisimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.

#timp - O(n)
#spatiu - O(1)
def produs_scalar(v1, v2):
    #v1 - list
    #v2 - list
    #returns - int
    produs = 0
    for i in range(len(v1)):
        produs = produs + v1[i] * v2[i]
    return produs


#timp - O(k)
#spatiu - O(k) , cu k < n nr de elem nenule ale vectorilor pt ca sunt vectori rari
def produs_scalar_gpt(v1, v2):
    
    # Dictionare cu indicii și valorile elementelor nenule
    v1_nenul = {i: v1[i] for i in range(len(v1)) if v1[i] != 0}
    v2_nenul = {i: v2[i] for i in range(len(v2)) if v2[i] != 0}
    
    # Intersectăm indicii și calculăm produsul scalar
    produs = sum(v1_nenul[i] * v2_nenul[i] for i in v1_nenul if i in v2_nenul)
    
    return produs

def test():
    v1 = [1, 0, 2, 0, 3]
    v2 = [1, 2, 0, 3, 1]
    assert produs_scalar(v1, v2) == 4

test()