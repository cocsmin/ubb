#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char *argv[]){
	int a2b[2], b2a[2];
	pipe(a2b);
	int ok = 0;
	int B = fork();
	if (B == 0){
		close (a2b[1]);
		srandom(getpid());
		int N = random() % 901 + 100;
		printf("Procesul B a generat %d \n", N);
		int X;
		while (!ok){
		read(a2b[0], &X, sizeof(int));
		int dif;
		if (N > X)
			dif = N - X;
		else
			dif = X - N;
		printf("B a primit %d; diferenta: %d \n", X, dif);
		if (dif < 50)
			ok = 1;
		}
		close (a2b[0]);
		exit(0);
	}
	close (a2b[0]);
	srandom(getpid());
	int k = 0;
	while (!ok){
		//while (!k){
		int X = random() % 1001 + 50;
		k++;
		write(a2b[1], &X, sizeof(int));
		wait(NULL);
	}
	printf("Procesul A a generat %d numere", k);
	close (a2b[1]);

	return 0;
}
