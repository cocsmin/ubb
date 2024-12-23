#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#ifdef __APPLE__  
#include <unistd.h>
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

int matrice[10000][100];
int n,m;
int suma_totala;
pthread_mutex_t mtx = PTHREAD_MUTEX_INITIALIZER;
void* do_work(void* arg){
	int indice = *((int*)arg);
	free(arg);
	int suma = 0;
	for (int i = 0; i < m; i++)
		suma+=matrice[indice][i];
	pthread_mutex_lock(&mtx);
	suma_totala = suma_totala + suma;
	pthread_mutex_unlock(&mtx);
	usleep(100);
}
	

int main(int argc, char* argv[]){
	FILE *fd;
	fd = fopen("fisier.txt", "r");
	fscanf(fd,"%d", &n);
	fscanf(fd,"%d", &m);
	//fin >> n >> m;
	for (int i = 0; i < n; i++)
		for (int j = 0; j < m; j++)
			fscanf(fd,"%d",&matrice[i][j]);

	pthread_t th[n];
	for (int i = 0; i < n; i++){
		int *id = malloc(sizeof(int));
		*id = i;
		pthread_create(&th[i], NULL, do_work, id);
	}


	for (int i = 0; i < n; i++)
		pthread_join(th[i], NULL);

	
	printf("%d", suma_totala);	
	






	return 0;
}
