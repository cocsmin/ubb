#ifdef __APPLE__

#ifndef PTHREAD_BARRIER_H_
#define PTHREAD_BARRIER_H_

#include <pthread.h>
#include <errno.h>
#include <unistd.h>
#include <time.h>
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

int n, sir[100000];
pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;
pthread_barrier_t barr;
void* do_work(void* arg){
	int id = *((int*)arg);
	free(arg);
	sir[id] = rand() % 10 + 10;
	printf("Sunt thread-ul cu id %d, am generat %d \n", id, sir[id]);
	//srandom(time(NULL));
	pthread_barrier_wait(&barr);
	int i,flag;
	while (1){
		flag = -1;
		pthread_mutex_lock(&mtx);
		if (sir[id] <= 0)
			break;
		flag = 0;
		for (i = 0; i < n; i++)
			if (i != id){
				printf("Sunt la thread-ul %d, scad 1 din %d ", id, sir[i]);
				sir[i]--;
				printf("%d \n", sir[i]);
				if (sir[i] > 0)
					flag = 1;
			}
		if (flag == 0)
			break;
		pthread_mutex_unlock(&mtx);
		usleep(100);
	}
	if (flag == -1)
		printf("thread ul %d a terminat \n", id);
	else
		printf("thread ul %d a CASTIGAT \n", id);
	pthread_mutex_unlock(&mtx);

	return NULL;
}
int main(int argc, char *argv[]){
	printf("Cititi n: \n");
	scanf("%d", &n);
	pthread_t th[n];
	pthread_barrier_init(&barr, NULL, n);
	for (int i = 0; i < n; i++){
		int *id = malloc(sizeof(int));
		*id = i;
		pthread_create(&th[i], NULL, do_work, id);
	}
	
	for (int i = 0; i < n; i++)
		pthread_join(th[i], NULL);

	for (int i = 0; i < n; i++)
		printf("%d ", sir[i]);

	pthread_barrier_destroy(&barr);
	return 0;
}
