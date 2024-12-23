#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char *argv[]){
	int A2B[2], B2A[2];
	pipe(A2B);
	pipe(B2A);
	int n = 1000;
	int f = fork();
	if (f == -1){
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	if (f == 0){
		close (A2B[1]);
		close (B2A[0]);
		while (n >= 5){
			read(A2B[0], &n, sizeof(int));
			printf("In procesul B, am primit din A: %d\n", n);
			n = n/2;
			printf("In procesul B n a devenit: %d\n", n);
			write(B2A[1], &n, sizeof(int));		
		}
		close(B2A[1]);
		close(A2B[0]);
		exit(0);
	}
	close (B2A[1]);
	close (A2B[0]);
	if (n == 1000){
		int ok = 0;
		while(!ok){
			n = random() % 100 + 50;
			if (n < 200)
				ok = 1;
		}
	}
	while (n >= 5){
		if (n%2)
			n++;
		printf("In procesul A: %d\n", n);
		write(A2B[1], &n, sizeof(int));
		read(B2A[0], &n, sizeof(int));
		printf("In procesul A, am primit din B: %d\n", n);
	}
	close (A2B[0]);
	close (B2A[1]);

	return 0;
}
