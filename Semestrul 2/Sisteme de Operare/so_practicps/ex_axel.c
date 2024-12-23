#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <string.h>
int main(int argc, char *argv[]){
	char sir[200];
	int p2a[2], a2p[2], a2b[2];
	scanf("%s", sir);
	while (strcmp(sir, "X")){
		pipe (p2a);
		pipe (a2p);
		pipe (a2b);
		int A = fork();
		if (A == -1){
			perror("fork()");
			exit(EXIT_FAILURE);
		}
		if (A == 0){
			close (a2b[0]);
			close (p2a[1]);
			close (a2p[0]);
			int fr[26]={0};
			char string[200];
			read(p2a[0], string, sizeof(char) * strlen(sir));
			for (int i = 0; i < strlen(string); i++)
				fr[string[i] - 'a']++;

			write(a2p[1], fr, sizeof(int) * 26);
			write(a2b[1], fr, sizeof(int) * 26);
			close (p2a[0]);
			close (a2p[1]);
			close (a2b[1]);
			exit(0);
		}
		int B = fork();
		if (B == -1){
			perror("fork()");
			exit(EXIT_FAILURE);
		}
		if (B == 0){
			close (a2p[0]);
			close (a2p[1]);
			close (p2a[0]);
			close (p2a[1]);
			close (a2b[1]);
			int fr[26];
			read (a2b[0], fr, sizeof(int) * 26);
			int suma = 0;
			for (int i = 0; i < 26; i++)
				suma = suma + fr[i];
			printf("Rezultatul sumei: %d\n", suma);
			close (a2b[0]);
			exit(0);
		}
		close (a2b[0]);
		close (a2b[1]);
		close (p2a[0]);
		close (a2p[1]);
		write (p2a[1], sir, sizeof(char) * strlen(sir));
		int frecventa[26];
		read (a2p[0], frecventa, sizeof(int) * 26);
		for (int i = 0; i < 26; i++)
			printf("%d : %d \n", i, frecventa[i]);
		
		scanf("%s", sir);
		close (p2a[1]);
		close (a2p[0]);
	}



	return 0;
}
