from repository.manager_evenimente import RepositoryEvenimente
from repository.manager_persoane import RepositoryPersoane
from repository.manager_inscrieri import RepositoryInscrieri
from domain.persoana import Persoana
from domain.eveniment import Eveniment

def test_adauga_persoana():
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    test_lista_persoane = lista_persoane.get_lista_persoane()
    assert (len(test_lista_persoane) == 1)
    try:
        persoana=Persoana(1,'', 'Strada Plopilor')
        lista_persoane.adauga(persoana)
        assert False
    except ValueError:
        assert True

def test_adauga_eveniment():
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Botez')
    lista_evenimente.adauga_eveniment(eveniment)
    test_lista_evenimente = lista_evenimente.get_lista_evenimente()
    assert (len(test_lista_evenimente) == 1)
    try:
        eveniment=Eveniment(1, 2, 3, 2023, 4, '')
        lista_evenimente.adauga(eveniment)
        assert False
    except ValueError:
        assert True
        
def test_sterge_persoana():
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    lista_persoane.sterge_persoana(1)
    assert (len(RepositoryPersoane.get_lista_persoane(lista_persoane)) == 0)
    persoana=Persoana(2,'Maria', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    assert (len(RepositoryPersoane.get_lista_persoane(lista_persoane)) == 1)

def test_sterge_eveniment():
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Botez')
    lista_evenimente.adauga_eveniment(eveniment)
    lista_evenimente.sterge_eveniment(1)
    assert (len(RepositoryEvenimente.get_lista_evenimente(lista_evenimente)) == 0)
    eveniment=Eveniment(2, 5, 12, 2023, 4, 'Botez')
    lista_evenimente.adauga_eveniment(eveniment)
    assert (len(RepositoryEvenimente.get_lista_evenimente(lista_evenimente)) == 1)

def test_modifica_persoana():
    lista_persoane= RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    lista_persoane = RepositoryPersoane()
    lista_persoane.adauga_persoana(persoana)
    lista_persoane.modifica_persoana(1,'Maria','Strada Plop')
    pers=Persoana(1,'Maria','Strada Plop')
    assert (lista_persoane.gaseste_persoana(1) == pers)

def test_modifica_eveniment():
    lista_evenimente = RepositoryEvenimente()
    eveniment = Eveniment(1, 2, 3, 2023, 4, 'Botez')
    lista_evenimente.adauga_eveniment(eveniment)
    lista_evenimente = RepositoryEvenimente()
    lista_evenimente.adauga_eveniment(eveniment)
    lista_evenimente.modifica_eveniment(1,4, 3, 2022, 5, 'Botez')
    ev= Eveniment(1,4, 3, 2022, 5, 'Botez')
    assert(ev == lista_evenimente.gaseste_eveniment(1))

def test_gaseste_persoana():
    lista_persoane = RepositoryPersoane()
    persoana_initial=Persoana(1,'Ana', 'Strada Plopilor')
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    persoana=Persoana(2,'Ema', 'Strada Teilor')
    lista_persoane.adauga_persoana(persoana)
    persoana=lista_persoane.gaseste_persoana(1)
    assert (persoana==persoana_initial)

def test_gaseste_eveniment():
    eveniment_initial=Eveniment(1, 2, 3, 2023, 4, 'Botez')
    lista_evenimente = RepositoryEvenimente()
    eveniment = Eveniment(1, 2, 3, 2023, 4, 'Botez')
    lista_evenimente.adauga_eveniment(eveniment)
    eveniment = Eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_evenimente.adauga_eveniment(eveniment)
    eveniment=lista_evenimente.gaseste_eveniment(1)
    assert (eveniment==eveniment_initial)
     
def test_gaseste_inscriere():
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(persoana)
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Botez')
    lista_evenimente.adauga_eveniment(eveniment)
    lista_inscrieri= RepositoryInscrieri(lista_persoane,lista_evenimente)
    lista_inscrieri.adauga_inscriere(1,1)
    assert(lista_inscrieri.cauta_inscriere(1,1)==True)
    assert(lista_inscrieri.cauta_inscriere(1,3)==False)


test_adauga_persoana()
test_adauga_eveniment()
test_sterge_persoana()
test_sterge_eveniment()
test_modifica_persoana()
test_modifica_eveniment()
test_gaseste_persoana()
test_gaseste_eveniment()
test_gaseste_inscriere()