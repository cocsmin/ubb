from domain.validatorpersoane import ValidatorP
from domain.validatorevenimente import ValidatorE
from service.servicep import ServiceP
from service.servicee import ServiceE
from service.inscrieri import ServiceInscrieri
from repository.manager_evenimente import RepositoryEvenimente
from repository.manager_persoane import RepositoryPersoane
from repository.manager_inscrieri import RepositoryInscrieri
from domain.persoana import Persoana
from domain.eveniment import Eveniment

def test_adauga_persoana():
    validatorp = ValidatorP()
    repo = RepositoryPersoane()
    lista_persoane = ServiceP(repo, validatorp)
    lista_persoane.store_persoana(1,'Ana', 'Strada Plopilor')
    test_lista_persoane = lista_persoane.get_persoane()
    assert (len(test_lista_persoane) == 1)
    try:
        lista_persoane.store_persoana(1,'', 'Strada Plopilor')
        assert False
    except ValueError:
        assert True

def test_adauga_eveniment():
    validatore = ValidatorE()
    repo = RepositoryEvenimente()
    lista_evenimente = ServiceE(repo,validatore)
    lista_evenimente.store_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    test_lista_evenimente = lista_evenimente.get_evenimente()
    assert (len(test_lista_evenimente) == 1)
    try:
        lista_evenimente.store_eveniment(1, 2, 3, 2023, 4, '')
        assert False
    except ValueError:
        assert True
        
def test_sterge_persoana():
    validatorp = ValidatorP()
    repo = RepositoryPersoane()
    lista_persoane = ServiceP(repo,validatorp)
    lista_persoane.store_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.delete_persoana(1)
    assert (len(ServiceP.get_persoane(lista_persoane)) == 0)
    lista_persoane.store_persoana(2,'Maria', 'Strada Plopilor')
    assert (len(ServiceP.get_persoane(lista_persoane)) == 1)

def test_sterge_eveniment():
    validatore = ValidatorE()
    repo = RepositoryEvenimente()
    lista_evenimente = ServiceE(repo,validatore)
    lista_evenimente.store_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.delete_eveniment(1)
    assert (len(ServiceE.get_evenimente(lista_evenimente)) == 0)
    lista_evenimente.store_eveniment(2, 5, 12, 2023, 4, 'Boardgames')
    assert (len(ServiceE.get_evenimente(lista_evenimente)) == 1)

def test_modifica_persoana():
    validatorp = ValidatorP()
    repo = RepositoryPersoane(validatorp)
    persoana =Persoana(1,'Maria','Strada Plop')
    lista_persoane = ServiceP(repo,validatorp)
    lista_persoane.store_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.modifica_persoana(1,'Maria','Strada Plop')
    assert (persoana == lista_persoane.cauta_persoana(1))

def test_modifica_eveniment():
    validatore = ValidatorE()
    repo = RepositoryEvenimente(validatore)
    eveniment = Eveniment(1,4, 3, 2022, 5, 'Boardgames')
    lista_evenimente = ServiceE(repo,validatore)
    lista_evenimente.store_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.modifica_eveniment(1,4, 3, 2022, 5, 'Boardgames')
    assert( eveniment == lista_evenimente.cauta_eveniment(1))

def test_cauta_persoana():
    validatorp = ValidatorP()
    repo = RepositoryPersoane(validatorp)
    lista_persoane = ServiceP(repo,validatorp)
    persoana_initial=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.store_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.store_persoana(2,'Ema', 'Strada Teilor')
    persoana=lista_persoane.cauta_persoana(1)
    assert (persoana==persoana_initial)

def test_cauta_eveniment():
    validatore = ValidatorE()
    repo = RepositoryEvenimente(validatore)
    eveniment_initial=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente = ServiceE(repo,validatore)
    lista_evenimente.store_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.store_eveniment(2, 5, 9, 2020, 3, 'Books')
    eveniment=lista_evenimente.cauta_eveniment(1)
    assert (eveniment==eveniment_initial)
    
def test_adauga_inscriere():
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(eveniment)
    lista_inscrieri= RepositoryInscrieri(lista_persoane,lista_evenimente)
    lista_inscrieri.adauga_inscriere(1,1)
    assert(lista_inscrieri.get_lista_inscrieri()==[[1, 1]])
    
def test_evenimente_ordonate_descriere():
    validatorp = ValidatorP()
    validatore = ValidatorE()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceE(repo1,validatore)
    lista_persoane = ServiceP(repo2,validatorp)
    repo3=RepositoryInscrieri(lista_persoane,lista_evenimente)
    lista_inscrieri=ServiceInscrieri(repo3,lista_evenimente)
    lista_evenimente.adauga_by_default_evenimente()
    lista_persoane.adauga_by_default_persoane()
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(1,3)
    lista_evenimente_ordonate=lista_inscrieri.evenimente_ordonate_descriere(1)
    assert(lista_evenimente_ordonate[0]==lista_evenimente[0])
    assert(lista_evenimente_ordonate[1]==lista_evenimente[2])
    assert(lista_evenimente_ordonate[2]==lista_evenimente[1])
    assert(len(lista_evenimente_ordonate)==3)
    
def test_evenimente_ordonate_data():
    validatorp = ValidatorP()
    validatore = ValidatorE()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceE(repo1,validatore)
    lista_persoane = ServiceP(repo2,validatorp)
    repo3=RepositoryInscrieri(lista_persoane,lista_evenimente)
    lista_inscrieri=ServiceInscrieri(repo3,lista_evenimente)
    lista_evenimente.adauga_by_default_evenimente()
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(1,3)
    lista_evenimente_ordonate=lista_inscrieri.evenimente_ordonate_data(1)
    assert(lista_evenimente_ordonate[0]==lista_evenimente[0])
    assert(lista_evenimente_ordonate[1]==lista_evenimente[2])
    assert(lista_evenimente_ordonate[2]==lista_evenimente[1])
    assert(len(lista_evenimente_ordonate)==3)
    
def test_persoane_inscrise_multe_evenimente():
    validatorp = ValidatorP()
    validatore = ValidatorE()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceE(repo1, validatore)
    lista_persoane = ServiceP(repo2, validatorp)
    repo3=RepositoryInscrieri(lista_persoane,lista_evenimente)
    lista_inscrieri=ServiceInscrieri(repo3,lista_evenimente)
    lista_evenimente.adauga_by_default_evenimente()
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(2,3)
    lista_inscrieri.adauga_inscriere(2,5)
    lista_inscrieri.adauga_inscriere(3,1)
    persoanele=lista_inscrieri.persoane_inscrise_multe_evenimente()
    assert(persoanele[0].get_nume()=='Mircea')
    assert(persoanele[0].get_adresa()=='Strada Plopilor')
    assert(persoanele[1].get_nume()=='Ana')
    assert(persoanele[1].get_adresa()=='Strada Teiului')
    assert(len(persoanele)==2)
    
    
test_adauga_eveniment()
test_adauga_persoana()
test_adauga_inscriere()
test_cauta_eveniment()
test_cauta_persoana()
test_modifica_eveniment()
test_modifica_persoana()
test_sterge_eveniment()
test_sterge_persoana()
