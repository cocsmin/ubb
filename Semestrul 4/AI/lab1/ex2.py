# Să se determine distanța Euclideană între două locații identificate 
# prin perechi de numere. 
# De ex. distanța între (1,5) și (4,1) este 5.0

import math

class punct:
    def __init__(self, x, y):
        self.x = x
        self.y = y

#timp - O(1)
#spatiu - O(1)

def distanta(A, B):
    #A - punct
    #B - punct
    #returns - float
    rezultat = math.sqrt((B.x - A.x) ** 2 + (B.y - A.y) ** 2)
    return rezultat

#timp - O(1)
#spatiu - O(1)

def distanta_gpt(p1, p2):
    return math.sqrt((p2[0] - p1[0]) ** 2 + (p2[1] - p1[1]) ** 2)


def test():
    A = punct(1, 5)
    B = punct(4, 1)
    assert distanta(A, B) == 5.0

test()