def make_list_copy(list):
    '''
    Functie ce creeaza o copie a unei liste
    :param list: lista ce trebuie copiata
    :type list: list
    :return: copia listei
    :rtype: list
    '''
    copie = []
    for element in list:
        copie.append(element)
    return copie