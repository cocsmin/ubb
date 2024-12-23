#define _POSIX_C_SOURCE 200112L /* Or higher */
#ifdef __APPLE__

#ifndef PTHREAD_BARRIER_H_
#define PTHREAD_BARRIER_H_

#include <pthread.h>
#include <errno.h>

typedef int pthread_barrierattr_t;
typedef struct
{
    pthread_mutex_t mutex;
    pthread_cond_t cond;
    int count;
    int tripCount;
} pthread_barrier_t;


int pthread_barrier_init(pthread_barrier_t *barrier, const pthread_barrierattr_t *attr, unsigned int count)
{
    if(count == 0)
    {
        errno = EINVAL;
        return -1;
    }
    if(pthread_mutex_init(&barrier->mutex, 0) < 0)
    {
        return -1;
    }
    if(pthread_cond_init(&barrier->cond, 0) < 0)
    {
        pthread_mutex_destroy(&barrier->mutex);
        return -1;
    }
    barrier->tripCount = count;
    barrier->count = 0;

    return 0;
}

int pthread_barrier_destroy(pthread_barrier_t *barrier)
{
    pthread_cond_destroy(&barrier->cond);
    pthread_mutex_destroy(&barrier->mutex);
    return 0;
}

int pthread_barrier_wait(pthread_barrier_t *barrier)
{
    pthread_mutex_lock(&barrier->mutex);
    ++(barrier->count);
    if(barrier->count >= barrier->tripCount)
    {
        barrier->count = 0;
        pthread_cond_broadcast(&barrier->cond);
        pthread_mutex_unlock(&barrier->mutex);
        return 1;
    }
    else
    {
        pthread_cond_wait(&barrier->cond, &(barrier->mutex));
        pthread_mutex_unlock(&barrier->mutex);
        return 0;
    }
}

#endif // PTHREAD_BARRIER_H_
#endif // __APPLE__

#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>

#define MAX_THR 50
#define _XOPEN_SOURCE 600

typedef struct{
	pthread_barrier_t *barr;
	pthread_mutex_t *mut;
	int *flag;
}data;
void* do_work(void* arg){
	data td = *((data*)arg);
	pthread_barrier_wait(td.barr);
	while(1){
		int n = rand() % 111112;
		pthread_mutex_lock(td.mut);
		if (*(td.flag) == 0){
			printf("%d\n", n); 
			if (n % 1001 == 0){
				*(td.flag) = 1;
				break;
			}		
		}
		else 
			break;
		pthread_mutex_unlock(td.mut);
	}
        pthread_mutex_unlock(td.mut);
	return NULL;
}

int main(int argc, char *argv[]){
	if (argc != 2){
		printf("trebuie n");
		exit(1);
	}
	int n = atoi(argv[1]);
	srand(time(NULL));
	int *fl = malloc(sizeof(int));
	*fl = 0;
	pthread_barrier_t *barr = malloc(sizeof(pthread_barrier_t));
	pthread_barrier_init(barr, NULL, n);
	pthread_mutex_t *mut = malloc(sizeof(pthread_mutex_t));
	pthread_mutex_init(mut, NULL);
	pthread_t t[n];
	data *args = malloc(n * sizeof(data));
	for (int i = 0; i < MAX_THR; i++){
		args[i].flag = fl;
		args[i].mut = mut; 
		args[i].barr = barr;
		pthread_create(&t[i], NULL, do_work, &args[i]);

	}
        for (int i = 0; i < MAX_THR; i++){
		pthread_join(t[i], NULL);
	}


	pthread_mutex_destroy(mut);
	pthread_barrier_destroy(barr);
	free(args);
	free(fl);
	free(mut);
	free(barr);

	return 0;
}
