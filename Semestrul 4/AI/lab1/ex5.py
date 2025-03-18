# Pentru un șir cu n elemente care conține valori din mulțimea 
# {1, 2, ..., n - 1} astfel încât o singură valoare se repetă de 
# două ori, să se identifice acea valoare care se repetă. 
# De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori.


#timp - O(n)
#spatiu - O(n)
def valoare_repetata(sir):
    #sir - list
    #returns - int
    numere_intalnite = set()
    for numar in sir:
        if numar in numere_intalnite:
            return numar 
        numere_intalnite.add(numar) 


from collections import Counter

#timp - O(n)
#spatiu - O(n)
def valoare_repetata_gpt(sir):
    # Numărăm frecvența fiecărui număr din șir
    frecventa = Counter(sir)
    
    # Căutăm și returnăm numărul care apare de două ori
    for numar, count in frecventa.items():
        if count == 2:
            return numar
        
def test():
    sir = [1, 2, 3, 4, 2]
    assert valoare_repetata(sir) == 2

test()