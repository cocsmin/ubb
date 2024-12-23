#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(int argc, char *argv[])
{
	//creez pipe
	int pd[2];
	int res = pipe(pd);
	if (res == -1)
	{
		perror("pipe()");
		exit(EXIT_FAILURE);
	}
	

	int pid = fork();
	if (pid == -1)
	{
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	
	if (pid == 0) // in copil
	{	
		//copilul citeste, deci inchidem capatul de WRITE
		close(pd[1]);
		while(1)
		{
		//citesc din pipe
		char c;
		read(pd[0], &c, sizeof(char));
		printf("[COPIL] Am citit %c\n", c);
		fflush(stdout);
		if (c == '0')
			break;
		
		}
		//inchid si capatul de citire
		close(pd[0]);	
		exit(0);
	}
	else // in parinte
	{
		//parintele scrie, dechi nichidem capatul de CITIRE
		close(pd[0]);
		while(1)
		{
		//citesc un caracter de la tastatura
		printf("Caracter: \n");
		char c;
		scanf("%c", &c);
		getchar();
		write(pd[1], &c, sizeof(char));
		//verific input-ul
		if (c == '0')
			break;

		//scriu in pipe	
		sleep(1);
		}
		//inchid si capatul de SCRIERE
		close(pd[1]);

		int status;
		wait(&status); // astept terminarea copilului
		//waitpid(pid, &status, 0) // echivalent cu cea de mai sus 
		printf("Parinte: Copilul cu pid %d a returnat %d \n", pid, status);
	}


return 0;
}
