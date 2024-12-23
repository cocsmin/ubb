import shutil
def copie_continut_fisier(sursa, destinatie):
    '''
    Copiaza continutul unui fisier sursa in fisierul destinatie
    :param sursa: numele/path ul fisierului sursa
    :type sursa: str
    :param destinatie: numele/path ul fisierului destinatie
    :type destinatie: str
    :return: -
    :rtype: -
    '''
    shutil.copyfile(sursa, destinatie)

def elimina_continut_fisier(numefisier):
    '''
    Sterge continutul unui fisier
    :param numefisier: numele/path ul fisierului
    :type numefisier: str
    :return: -
    :rtype: -
    '''
    with open(numefisier, 'w') as f:
        f.write('')