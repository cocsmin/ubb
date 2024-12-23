from domain.validatorpersoane import ValidatorP
from domain.validatorevenimente import ValidatorE
from repository.manager_persoane import RepositoryPersoane
from repository.manager_evenimente import RepositoryEvenimente
from repository.manager_inscrieri import RepositoryInscrieri
from service.servicep import ServiceP
from service.servicee import ServiceE
from service.inscrieri import ServiceInscrieri
from ui.consola import Consola

validator_persoane = ValidatorP()
validator_evenimente = ValidatorE()
repo_persoane = RepositoryPersoane()
repo_evenimente = RepositoryEvenimente()
lista_persoane = ServiceP(repo_persoane, validator_persoane)
lista_evenimente = ServiceE(repo_evenimente, validator_evenimente)
repo_inscrieri = RepositoryInscrieri(repo_persoane, repo_evenimente)
lista_inscrieri = ServiceInscrieri(repo_inscrieri, lista_evenimente)
lista_persoane.adauga_by_default_persoane()
lista_evenimente.adauga_by_default_evenimente()
consola = Consola(lista_persoane, lista_evenimente, lista_inscrieri)

consola.run()