#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char *argv[]){
	int a2b[2], b2c[2], c2a[2];
	printf("CITITI N: ");
	int N;
	scanf("%d", &N);
	pipe(a2b);
	pipe(b2c);
	pipe(c2a);
	int B = fork();
	if (B == 0){
		close (b2c[0]);
		close (a2b[1]);
		close (c2a[0]);
		close (c2a[1]);
		int numbers[100];
		int N;
		read(a2b[0], &N, sizeof(int));
		read(a2b[0], numbers, sizeof(int) * (N+1));
		for (int i = 1; i <= N; i++)
			printf("%d ", numbers[i]);
		printf("\n");
		int k;
		int ok = 0;
		while (!ok){
			k = random() % 10 - 5;
			if (k >= 2 && k <= 5)
				ok = 1;
		}
		printf("Numarul random este: %d \n", k);
		for (int i = 1; i <= N; i++)
			numbers[i] = numbers[i] + k;
		write(b2c[1], &N, sizeof(int));
		write(b2c[1], numbers, sizeof(int) * (N + 1));
		close (a2b[0]);
		close (b2c[1]);
		exit(0);
	}
	int C = fork();
	if (C == 0){
		close (a2b[0]);
		close (a2b[1]);
		close (b2c[1]);
		close (c2a[0]);
		int numbers[100];
		int N;
		read(b2c[0], &N, sizeof(int));
		read(b2c[0], numbers, sizeof(int) * (N + 1));
		for (int i = 1; i <= N; i++)
                        printf("%d ", numbers[i]);
                printf("\n");
		int suma = 0;
		for (int i = 1; i <= N; i++)
			suma = suma + numbers[i];
		write(c2a[1], &suma, sizeof(int));
		close (b2c[0]);
		close (c2a[1]);
		exit(0);
	}
	close (b2c[0]);
	close (b2c[1]);
	close (c2a[1]);
	close (a2b[0]);	
	int numere[100];
	for (int i = 1; i <= N; i++){
		//int aux;
		scanf("%d", &numere[i]);
		//numere[i] = aux;
	}
	write(a2b[1], &N, sizeof(int));
	write(a2b[1], &numere, sizeof(int) * (N+1));
	//wait(NULL);
	//wait(NULL);
	int suma;
	read(c2a[0], &suma, sizeof(int));
	printf("suma este: %d \n", suma);
	close (a2b[1]);
	close (c2a[0]);




	return 0;
}
