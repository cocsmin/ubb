//~daniel/so/lab11
//problema consumatori producatori
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <time.h>

#define MAX 10

pthread_cond_t cv = PTHREAD_COND_INITIALIZER;
pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;

int flag, recipient[MAX], n, size;

int e_gol(){
	if (flag == 0)
		return 1;
	else
		return 0;
}

int e_plin(){
	if (flag == 1)
		return 1;
	else
		return 0;
}

void* productie(void* arg){
	pthread_mutex_lock(&mtx);
	while (e_plin()){
		printf("Recipientul este PLIN...astept coaie \n");
		pthread_cond_wait(&cv, &mtx);
	}
		
	printf("Recipientul e GOL...produuuuccc :3 \n");
	for (int i = 0; i < MAX; i++){
		recipient[i] = rand() % 100;
		printf("Am generat %d \n", recipient[i]);
		sleep(1);
	}
	printf("\n");
	flag = 1;
	printf("Am umplut recipientul!!! >_<\n");
	

	pthread_cond_broadcast(&cv);
	pthread_mutex_unlock(&mtx);
	

	return NULL;


}

void* consumatie(void* arg){
	
	while (e_gol()){
		printf("Recipientul e GOL...n-am ce manca uwu\n");
		pthread_cond_wait(&cv, &mtx);

	}
	printf("Recipientul e PLIN...ooooooooo \n");
	for (int i = 0; i < MAX; i++){
		printf("%d ", recipient[i]);
		sleep(1);
	}

	printf(" \n");
	flag = 0;
	printf("A-am..golit recipientul..... \n");
	
	pthread_cond_broadcast(&cv);
	pthread_mutex_unlock(&mtx);

	return NULL;
	
}


int main(int argc, char *argv[]){
	pthread_t cons[10];
	pthread_t prod[10];
	srand(time(NULL));
	printf("Cititi n: \n");
	scanf("%d", &n);
	for (int i = 0; i < 6; i++)
		pthread_create(&prod[i], NULL, productie, NULL);

	for (int i = 0; i < 4; i++)
		pthread_create(&cons[i], NULL, consumatie, NULL);
	
	for (int i = 0; i < 6; i++){
		pthread_join(prod[i], NULL);
	}
	for (int i = 0; i < 4; i++){
		pthread_join(cons[i], NULL);
	}
		








	return 0;
}
