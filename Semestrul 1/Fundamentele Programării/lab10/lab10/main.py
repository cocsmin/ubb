from domain.validatorpersoane import ValidatorP
from domain.validatorevenimente import ValidatorE
from repository.manager_persoane import FileRepositoryPersoane
from repository.manager_evenimente import FileRepositoryEvenimente
from repository.manager_inscrieri import RepositoryInscrieri
from service.servicep import ServiceP
from service.servicee import ServiceE
from service.inscrieri import ServiceInscrieri
from ui.consola import Consola

validator_persoane = ValidatorP()
validator_evenimente = ValidatorE()
repo_persoane = FileRepositoryPersoane('data/persoane.txt')
repo_evenimente = FileRepositoryEvenimente('data/evenimente.txt')
lista_persoane = ServiceP(repo_persoane, validator_persoane)
lista_evenimente = ServiceE(repo_evenimente, validator_evenimente)
repo_inscrieri = RepositoryInscrieri(repo_persoane, repo_evenimente)
lista_inscrieri = ServiceInscrieri(repo_inscrieri, lista_evenimente)
consola = Consola(lista_persoane, lista_evenimente, lista_inscrieri)

consola.run()