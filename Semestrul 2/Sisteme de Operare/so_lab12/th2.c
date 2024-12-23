#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <limits.h>
#include <time.h>
#include <fcntl.h>
//Se da un taboul cu 100000 nr folosind un nr potrivit de threaduri sa
// se determine cel mai mic nr intreg din acest tablou
#define MAX_THR 50
#define MAX_NUM 100000

int numere[MAX_NUM];
int suma_minime = 0;
pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;
pthread_barrier_t barr;

//thread start routine
void* do_work(void* arg){
	int id = *(int*)arg;
	free(arg);

	//calculez limitele de lucru
	int min = INT_MAX; // minim local
	int st = id * (MAX_NUM/MAX_THR);
	int dr = st + MAX_NUM/MAX_THR;
	for (int i = st; i < dr; i++){
		if (min > numere[i])
			min = numere[i];	
		
	}

	pthread_mutex_lock(&mtx);

	suma_minime += min;
        pthread_mutex_unlock(&mtx);
	//pun bariera
	pthread_barrier_wait(&barr);
	float ma = suma_minime/MAX_THR;
	printf("Media aritmetica: %f\n", ma);

	return NULL;
}
int main(int argc, char*argv[]){
	/*
	//stabilesc seed-ul
	srand(time(NULL));
	//generz 100000 numere aleatoare
	for (int i = 0; i < MAX_NUM; i++)
		numere[i] = rand();
	*/
	int fd = open("file.bin", O_RDONLY);
	if (fd <= 0){
		perror("open()");
		exit(EXIT_FAILURE);
	}

        for (int i = 0; i < MAX_NUM; i++)
		read(fd, &numere[i], 2); // adica 2 octeti, putea sa fie sizeof(int)); 



	close(fd);

	//creez bariera
	pthread_barrier_init(&barr, NULL, MAX_THR);
	
	//creez thread uri
	pthread_t tid[MAX_THR];
	for (int i = 0; i < MAX_THR; i++){
		int *id = malloc(sizeof(int));
		*id = i;
		pthread_create(&tid[i], NULL, do_work, id);	
	}
	//astpet terminarea threadurilor
	for (int i = 0; i < MAX_THR; i++){
		pthread_join(tid[i], NULL);
		
	}

	pthread_barrier_destroy(&barr);

return 0;
}
