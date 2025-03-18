# Să se determine al k-lea cel mai mare element al unui șir 
# de numere cu n elemente (k < n). De ex. al 2-lea cel mai mare
# element din șirul [7,4,6,3,9,1] este 7.

#timp - O(nlogn)
#spatiu - O(1)
def cel_mai_mare_k(sir, k):
    #sir - list
    #k - int
    #returns - int
    sir.sort(reverse = True)
    return sir[k - 1]

import heapq

#timp - O(nlogk)
#spatiu - O(k)
def k_cel_mai_mare_heap(sir, k):
    heap = sir[:k]  # Luăm primele k elemente
    heapq.heapify(heap)  # Transformăm într-un heap minim
    
    for numar in sir[k:]:  # Parcurgem restul elementelor
        if numar > heap[0]:  # Dacă e mai mare ca minimul din heap
            heapq.heappop(heap)  # Eliminăm minimul
            heapq.heappush(heap, numar)  # Adăugăm noul număr
    
    return heap[0]  # Cel mai mic element din heap e al k-lea cel mai mare


def test():
    sir = [7, 4, 6, 3, 9, 1]
    assert cel_mai_mare_k(sir, 2) == 7

test()