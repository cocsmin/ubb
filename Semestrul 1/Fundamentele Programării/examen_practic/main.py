from domain.melodie import Melodie
from domain.validator import Validator
from repository.repo import *
from service.serv import Service
from ui.consola import Console
from teste.tests import Teste

repo_melodii = FileRepository('date.txt')
validator = Validator()
service_melodii = Service(repo_melodii, validator)
consola = Console(service_melodii)
consola.run()

