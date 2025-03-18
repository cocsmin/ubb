# Să se determine ultimul (din punct de vedere alfabetic) cuvânt care 
# poate apărea într-un text care conține mai multe cuvinte 
# separate prin ” ” (spațiu). De ex. ultimul (dpdv alfabetic) 
# cuvânt din ”Ana are mere rosii si galbene” este cuvântul "si".

#timp: O(n)
#spatiu: O(n)

def alfabetic (sir):
    #sir - string
    #returns string
    sir_mic = sir.split()
    ultimul = ""
    for cuvant in sir_mic:
        if cuvant.lower() > ultimul.lower():
            ultimul = cuvant

    return ultimul

#timp: O(n)
#spatiu: O(n)
def alfabetic_gpt (sir):
    #sir - string
    cuvinte = text.split()

    cuvinte_sortate = sorted(cuvinte, key = str.lower)

    return cuvinte_sortate[-1]

def test():
    sir_test = "Ana are mere rosii si galbene"
    assert alfabetic(sir_test) == "si"
    sir_test2 = "Ana are mere rosii Si galbene"
    assert alfabetic(sir_test2) == "Si"



test()