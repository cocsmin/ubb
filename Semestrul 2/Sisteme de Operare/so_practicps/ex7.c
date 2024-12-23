#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char *argv[]){
	int a2b[2], b2a[2];
	pipe(a2b);
	pipe(b2a);
	int f = fork();
	if (f == -1){
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	if (f == 0){
		close (a2b[0]);
		close (b2a[1]);
		int n;
		read (b2a[0], &n, sizeof(int));
		printf("Fiul 1 a citit: %d \n", n);
		while (n != 10){
			n = random() % 10 + 1;
			write (a2b[1], &n, sizeof(int));
			if (n == 10)
				break;
			read (b2a[0], &n, sizeof(int));
			printf("Fiul 1 a citit: %d \n", n);
		}
		close (a2b[1]);
		close (b2a[0]);
		exit(0);
	}
	f = fork();
	if (f == -1){
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	if (f == 0){
		close (a2b[1]);
		close (b2a[0]);
		int n;
		while (n != 10){
			n = random() % 10 + 1;
			write (b2a[1], &n, sizeof(int));
			if (n == 10)	
				break;
			read (a2b[0], &n, sizeof(int));
			printf("Fiul 2 a citit: %d \n", n);
		}
		close (a2b[0]);
		close (b2a[1]);
		exit (0);
	}
	for (int i = 0; i < 2; i++)
		wait(NULL);

	return 0;
}
