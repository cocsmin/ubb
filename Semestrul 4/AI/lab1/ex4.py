# Să se determine cuvintele unui text care apar exact o singură dată 
# în acel text. De ex. cuvintele care apar o singură dată în 
# ”ana are ana are mere rosii ana" sunt: 'mere' și 'rosii'.

#timp - O(n)
#spatiu - O(n)

def cuvinte_unice(text):
    #text - string
    #returns - list
    cuvinte = text.split()
    hashmap = {} 
    
    for cuvant in cuvinte:
        if cuvant in hashmap:
            hashmap[cuvant] += 1
        else:
            hashmap[cuvant] = 1
    
    return [cuvant for cuvant, count in hashmap.items() if count == 1]

from collections import Counter

#timp - O(n)
#spatiu - O(n)

def cuvinte_unice_gpt(text):
    # Împărțim textul în cuvinte
    cuvinte = text.split()
    
    # Numărăm frecvența fiecărui cuvânt
    frecventa = Counter(cuvinte)
    
    # Selectăm doar cuvintele care apar exact o singură dată
    return [cuvant for cuvant, numar in frecventa.items() if numar == 1]


def test():
    text = "ana are ana are mere rosii ana"
    assert cuvinte_unice(text) == ["mere", "rosii"]

test()