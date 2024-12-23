#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main(int argc, char *argv[]){
	int n, piduri[100],k = 1;
	printf("n= ");
	scanf("%d", &n);
	int p[2];
	int res = pipe(p);
	if (res == -1){
		perror("pipe()");
		exit(EXIT_FAILURE);
	}
	while (n){
		int pid = fork();
		if (pid == -1){
			perror("fork()");
			exit(EXIT_FAILURE);
		}
		if (pid == 0){
			close (p[0]);
			int pid_fiu = getpid();
			int pid_parinte = getppid();
			printf("Pid fiu: %d, Pid parinte: %d\n", pid_fiu, pid_parinte);
			write(p[1], &pid_fiu, sizeof(int));
			k++;
			close (p[1]);
			exit(0);
		}
		else {
		close (p[1]);
		int aux;
		read(p[0], &aux, sizeof(int));
		piduri[k] = aux;
		k++;
		n--;
		close (p[0]);
		}
	}
	printf("GATA CU COPIII!!!! \n");
	printf("PID EU: %d \n", getpid());
	for (int i = 1; i < k; i++)
		printf("PID FIU %d: %d \n", i, piduri[i + 1]);



	return 0;
}
			
