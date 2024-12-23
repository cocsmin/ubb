'''
Scrieți o aplicație care gestionează cheltuielile de familie efectuate într-o lună.
Pentru o cheltuială se vor retine ziua (din luna), suma, tipul cheltuielii (5 categorii:
mâncare, întreținere, îmbrăcăminte, telefon, altele). Aplicația să permită
efectuarea repetată a următoarelor operații:
    1. Adaugă cheltuială
        1. Adaugă o nouă cheltuială (se specifică ziua, suma, tipul)
        2. Actualizează cheltuială (se specifică ziua, suma, tipul)
    2. Ștergere.
        1. Șterge toate cheltuielile pentru ziua dată
        2. Șterge cheltuielile pentru un interval de timp (se dă ziua de început și sfârșit, 
        se șterg toate cheltuielile pentru perioada dată)
        3. Șterge toate cheltuielile de un anumit tip
    3. Căutări.
        1. Tipărește toate cheltuielile mai mari decât o sumă dată
        2. Tipărește toate cheltuielile efectuate înainte de o zi dată și mai mici
        decât o sumă (se dă suma și ziua, se tipăresc toate cheltuielile mai
        mici ca suma dată și efectuate înainte de ziua specificată)
        3. tipărește toate cheltuielile de un anumit tip.
    4. Rapoarte.
        1. Tipărește suma totală pentru un anumit tip de cheltuială
        2. Găsește ziua în care suma cheltuită e maximă
        3. Tipărește toate cheltuielile ce au o anumită sumă
        4. Tipărește cheltuielile sortate după tip
    5. Filtrare.
        1. Elimină toate cheltuielile de un anumit tip
        2. Elimină toate cheltuielile mai mici decât o sumă dată
    6. Undo: Reface ultima operație (lista de cheltuieli revine ce exista înainte de
ultima operație care a modificat lista). 

'''




    #return lista[0]








lista_cheltuieli = []
'''
def optiuni_default():
    ''''''
    Functie ce atribuie niste valori default in lista de cheltuieli
    :param :none
    :return lista_cheltuieli: lista cu cheltuieli default
    :rtype: list
    ''''''
    lista_cheltuieli = [{'nume': 'shaorma', 'zi': 1, 'suma': 50.0, 'tip': 'mancare'}, {'nume': 'curent', 'zi': 2, 'suma': 200.0, 'tip': 'intretinere'}, {'nume': 'converse', 'zi': 3, 'suma': 399.0, 'tip': 'imbracaminte'}]
    print(lista_cheltuieli)
    return lista_cheltuieli

'''








test_sterge_cheltuiala_zi()














test_elimina_cheltuiala()





test_elimina_dupatip()