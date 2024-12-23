#ifdef __APPLE__

#ifndef PTHREAD_BARRIER_H_
#define PTHREAD_BARRIER_H_

#include <pthread.h>
#include <errno.h>
#include <string.h>
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


#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#define SIZE 128
pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cg = PTHREAD_COND_INITIALIZER;
pthread_cond_t cp = PTHREAD_COND_INITIALIZER;
char buffer[SIZE];
int count;
int pos;

void* generate(void* arg){
	while(1){
		char ch = 'a' + rand() % ('z' - 'a' + 1);
		pthread_mutex_lock(&mtx);
		while (pos == SIZE){
			pthread_cond_signal(&cp);
			while (pos == SIZE){
				pthread_cond_wait(&cg, &mtx);
			}
		}
		if (count == 0)
			break;
		buffer[pos] = ch;
		pos++;
		pthread_mutex_unlock(&mtx);
	}
	pthread_mutex_unlock(&mtx);
	return NULL;
}

void* print(void* arg){
	while (1){
		pthread_mutex_lock(&mtx);
		if (pos != SIZE){
			pthread_cond_signal(&cg);
			while ( pos != SIZE ){
				pthread_cond_wait(&cp, &mtx);
			}
		}
		printf("%s\n", buffer);
		memset(buffer, 0, SIZE);
		pos = 0;
		count--;
		if (count == 0)
			break;
		pthread_cond_broadcast(&cg);
		pthread_mutex_unlock(&mtx);
	}
	pthread_mutex_unlock(&mtx);
	return NULL;
}

int main(int argc, char* argv[]){
	if (argc != 3){
		printf("trebe M si N");
		exit(1);
	}
	int N = atoi(argv[1]);
	int M = atoi(argv[2]);
	srand(time(NULL));
	pos = 0;
	count = M;
	pthread_t thr[N + 1];

	for (int i = 0; i < N; i++){
		pthread_create(&thr[i], NULL, generate, NULL);
	}
	pthread_create(&thr[N], NULL, print, NULL);
	for (int i = 0; i <= N; i++)
		pthread_join(thr[i], NULL);

		



	return 0;
}
