# Considerându-se o matrice cu n x m elemente întregi și o listă 
# cu perechi formate din coordonatele a 2 căsuțe din matrice
# ((p,q) și (r,s)), să se calculeze suma elementelor din 
# sub-matricile identificate de fieare pereche.

# De ex, pt matricea
# [[0, 2, 5, 4, 1],
# [4, 8, 2, 3, 7],
# [6, 3, 4, 6, 2],
# [7, 3, 1, 8, 3],
# [1, 5, 7, 9, 4]]
# și lista de perechi ((1, 1) și (3, 3)), ((2, 2) și (4, 4)), 
# suma elementelor din prima sub-matrice este 38, 
# iar suma elementelor din a 2-a sub-matrice este 44.


#timp - O(n*m)
#spatiu - O(1)
def suma_submatrice(matrice, p, q, r, s):
    #matrice - vector bidimensional
    #p,q,r,s - int
    #returns - int
    suma = 0
    for i in range(p, r + 1):
        for j in range(q, s + 1):
            suma += matrice[i][j]

    return suma

#GPT
#timp - O(n*m)
#spatiu - O(n*m)
def compute_prefix_sum(matrix):
    n = len(matrix)
    m = len(matrix[0])
    prefix_sum = [[0] * m for _ in range(n)]

    # Construim matricea de prefix sum
    for i in range(n):
        for j in range(m):
            prefix_sum[i][j] = matrix[i][j]
            if i > 0:
                prefix_sum[i][j] += prefix_sum[i-1][j]
            if j > 0:
                prefix_sum[i][j] += prefix_sum[i][j-1]
            if i > 0 and j > 0:
                prefix_sum[i][j] -= prefix_sum[i-1][j-1]
    
    return prefix_sum

def sum_submatrix(prefix_sum, p, q, r, s):
    result = prefix_sum[r][s]
    if p > 0:
        result -= prefix_sum[p-1][s]
    if q > 0:
        result -= prefix_sum[r][q-1]
    if p > 0 and q > 0:
        result += prefix_sum[p-1][q-1]
    return result

# Exemplu de utilizare
matrix = [
    [0, 2, 5, 4, 1],
    [4, 8, 2, 3, 7],
    [6, 3, 4, 6, 2],
    [7, 3, 1, 8, 3],
    [1, 5, 7, 9, 4]
]

queries = [((1, 1), (3, 3)), ((2, 2), (4, 4))]

# Calculăm matricea de prefix sum
prefix_sum = compute_prefix_sum(matrix)

# Calculăm sumele pentru fiecare pereche de coordonate
# for (p, q), (r, s) in queries:
#     print(f"Suma pentru sub-matricea {(p, q)} -> {(r, s)}: {sum_submatrix(prefix_sum, p, q, r, s)}")

def test():
    matrice = [
    [0, 2, 5, 4, 1],
    [4, 8, 2, 3, 7],
    [6, 3, 4, 6, 2],
    [7, 3, 1, 8, 3],
    [1, 5, 7, 9, 4]]

    assert suma_submatrice(matrice, 1, 1, 3, 3) == 38
    assert suma_submatrice(matrice, 2, 2, 4, 4) == 44

test()