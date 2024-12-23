#meniu de afisare
#while True...
#meniul cu optiunis

#reprezentare TASK prin DICTIONAR
#task = {'descriere': 'HostMovieMarathon', 'zi_deadline': 11, 'luna_deadline': 12, 
#            'status': 'pending'}


option == '1'
descriere=input...
data=input...
status=input...

task = create_task(descriere, data, status)
add_task(task_list, task)

def print_task_list(task_list):
    for task in task_list:
        print(task)

task_list = []
#SPECIFICATII
# ce face o functie, nu CUM
# descriere parametrii + tip preconditii(conditii asupra input-ului: ex. data in format dd-mm)
# output: ce returneaza + postconditii 
def create_task(descriere: str, data, status)-> dict:
    '''
    creeaza un task
    :param descriere: descrierea task-ului
    :type descreire: str
    :param data: data task ului
    :type data: str
    :param status: statsiul task ului
    :type status: str
    :return: task ul creat
    :rtype: dict

    '''
    elements_date = data.split('-')
    day = int(elements_date[0].strip())
    month = int(elements_date[1].strip())



def test_create_task():
    #assert
    #testez crearea de task
    task = create_task('HostMovieMarathon', '11-12', 'pending')
    assert (type(task) == dict)
    assert (type(task) != list)
    assert (task('descriere')=='HostMovieMarathon')
    assert (task('zi_deadline')=='11')
    assert (task('luna_deadline')=='12')
    assert (task('status')=='pending')



#create_task()

def add_task(task_list, task):
    '''
    Adauga taskul dat in lista de taskuri
    :param task_list: lista in care se adauga
    :type task_list: list
    :param task: task ul care se adauga
    :type task: dict
    :return: -; modifica task_list  adaugand la sfarsit task ul dat
    :rtype: -
    '''
    task_list.append(task)

def test_validate_task():
    pass

def test_add():
    #testez adaugare in lista
    test_task_list = []
    assert (len(test_task_list) == 0)
    task = create_task('HostMovieMarathon','11-12','pending')
    add_task(test_task_list, task)
    assert (len(test_task_list) == 1)
    assert (test_task_list[0]['descriere'] == 'HostMovieMarathon')