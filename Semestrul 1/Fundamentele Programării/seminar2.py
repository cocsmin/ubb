def print_menu():
    print('1. Citire lista')
    print('2. Adaugare task\n3. Filtrare\n4. Afisare pe zile\n')
    print('5. Iesire')

def read_list():
    lista = input ('Introduceti taskurile dvs: \n ')
    taskuri = lista.split(', ')
    return taskuri


def read_task():
    descriere = input('Descriere:')
    zi_deadline = int(input('Zi deadline:'))
    status = input('Status:')
    return descriere+'_'+str(zi_deadline)+'_'+status

def adauga_in_lista()
    task = read_task()
    my_list.append(task)


lista_taskuri = []

while True:
    print_menu()
    optiune=int(input('Introduceti optiunea\n'))
    if optiune == 1:
        lista_taskuri = read_list()
        print('Lista este' , lista_taskuri)

    if optiune == 2:
        """descriere, zi , status = read_task()
        print(descriere)
        print(zi)
        print(status)
        """
        adauga_in_lista(lista_taskuri)
        print(lista_taskuri)
    if optiune == 5:
        break
