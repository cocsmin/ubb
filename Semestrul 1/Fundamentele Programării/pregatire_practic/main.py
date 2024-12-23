from service.service_examene import Service
from domain.validator import ValidatorExamen
from repository.repo_examene import Manager
from repository.repo_examene import FileManager
from console import Console


validator = ValidatorExamen()
repo_examene = FileManager("date.txt")
lista_examene = Service(repo_examene, validator)
consola = Console(lista_examene)
consola.run()