from ui.consola import Consola
from repository.repository_haine import FileRepositoryHaine
from service.service_haine import ServiceHaine
from teste.teste_domain import *
from teste.teste_service_haine import *
repo_haine = FileRepositoryHaine('date.txt')
serv_haine = ServiceHaine(repo_haine)
consola = Consola(serv_haine)
test1 = TestDomain
test1.ruleaza_teste()
#test2 = TesteService
#test2.ruleaza_teste()
consola.run()