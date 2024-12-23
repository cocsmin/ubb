#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

int main(int argc, char *argv[]){
	if (argc != 2){
		perror("Da coaie un fisier");
		exit(EXIT_FAILURE);
	}
	int pp[2];
	int v = pipe(pp);
	if (v == -1){
		perror("eroare la pipe");
		exit(EXIT_FAILURE);
	}
	FILE *fd;
	fd = fopen(argv[1], "r");
	if (fd == 0){
		perror("eroare la fisier sefu");
		exit(EXIT_FAILURE);
	}
	int f = fork();
	if (f == -1){
		perror("eroare la fork sefu");
		exit(EXIT_FAILURE);
	}
	if (f == 0){
		close (pp[1]);
		char operator = 'x';
		int a,b;
		while (operator != '0'){
			read(pp[0], &operator, sizeof(char));
			if (operator == '0')
				break;
			read(pp[0], &a, sizeof(int));
			read(pp[0], &b, sizeof(int));
			if (operator == '*'){
				int rez = a * b;
				printf("%d * %d = %d\n", a, b, rez);
			}
			if (operator == ':'){
				int rez = a / b;
				printf("%d / %d = %d\n", a, b, rez);
			}
		}
		close (pp[0]);
		exit(EXIT_SUCCESS);
	}
	close (pp[0]);
	char op;
	int a,b;
	while (fscanf(fd, "%c %d %d", &op, &a, &b) != -1){
		write(pp[1], &op, sizeof(char));
		write(pp[1], &a, sizeof(int));
		write(pp[1], &b, sizeof(int));
	}
	op = '0';
	write(pp[1], &op, sizeof(char));
	wait(NULL);
	close (pp[1]);
	fclose(fd);


	return 0;
}
