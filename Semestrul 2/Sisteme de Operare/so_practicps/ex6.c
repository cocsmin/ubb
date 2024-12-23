#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[]){
	if (argc != 2){
		perror("Dati un singur argument!");
		exit(EXIT_FAILURE);
	}
	int c2p[2], p2c[2];
	pipe(c2p);
	pipe(p2c);
	int f = fork();
	if (f == -1){
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	if (f == 0){
		close(p2c[1]);
		close(c2p[0]);
		int n, nr;
		float rezultat = 0;
		read(p2c[0], &n, sizeof(int));
		for (int i = 0; i < n; i++){
			read(p2c[0], &nr, sizeof(int));
			rezultat = rezultat + nr;
		}
		rezultat = rezultat / n;
		write (c2p[1], &rezultat, sizeof(float));
		close (p2c[0]);
		close (c2p[1]);
		exit(0);
	}
	close (p2c[0]);
	close (c2p[1]);
	int n = atoi(argv[1]);
	float rezultat = 0;
	write (p2c[1], &n, sizeof(int));
	for (int i = 0; i < n; i++){
		int nr = random() % 100;
		printf("Am generat numarul : %d \n", nr);
		write (p2c[1], &nr, sizeof(int));
	}
	wait(0);
	read(c2p[0], &rezultat, sizeof(float));
	printf("REZULTATUL ESTE: %f", rezultat);
	close (p2c[1]);	
	close (c2p[0]);



	return 0;
}
