from repository.repository_haine import FileRepositoryHaine
class ServiceHaine:
    def __init__(self, repo_haine):
        self.__repo_haine = repo_haine
        self.__lista_comenzi = []
    def haine_tip(self, tip_cautat):
        '''
        Functie ce afiseaza toate hainele din lista cu tipul cautat
        :param tip_cautat: tipul citit de la tastatura
        :type tip_cautat: str
        :return : nimic, dar afiseaza lista de haine
        '''
        lista_noua = []
        for haina in self.__repo_haine.get_lista():
            if haina.get_tip() == tip_cautat:
                lista_noua.append(haina)

        return lista_noua
    
    def comanda_noua(self, id_haina, nr_bucati, nume_client, adresa):
        '''
        Functie ce creeaza o noua comanda de haine
        :param id_haina: id-ul hainei comandate
        :type id_haina: int
        :param nr_bucati: numarul de bucati comadnate
        :type nr_bucati: int
        :param: nume_client: numele clientului
        :type nume_client: str
        :param: adresa: adresa la care va fi livrata comanda
        :type adresa: str
        :return lista cu comenzi in care se adauga comanda respectiva
        :return type: list
        '''
        comanda = {}
        comanda['id_haina'] = id_haina
        comanda['nr_bucati'] = nr_bucati
        comanda['nume_client'] = nume_client
        comanda['adresa client'] = adresa
        #comanda.append(nr_bucati)
        #comanda.append(nume_client)
        #comanda.append(adresa)
        total = 0
        for haina in self.__repo_haine.get_lista():
            if haina.get_id() == id_haina:
                pret = int(haina.get_pret())
                total = nr_bucati * pret
        self.__lista_comenzi.append(comanda)

    def get_lista_comenzi(self):
        '''
        Functie ce returneaza lista cu toate comenzile efectuate
        '''
        return self.__lista_comenzi