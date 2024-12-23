#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
int main(int argc, char *argv[])
{
	//definesc lista de argumente, comanda propriu zisa
	char *args[] = {"ls", "-l", NULL};
	int pid = fork();
	if (pid == -1)
	{
		perror("fork()");
		exit(EXIT_FAILURE);
	}
	
	if (pid == 0) // in copil
	{	
		//creez fisireul, simpla redirectare!
		int fd = open("file_list", O_CREAT | O_TRUNC | O_RDWR, 0644);
		
		if (fd == -1)
		{
			perror("open()");
			exit(EXIT_FAILURE);
		}
		//fac redirectarea fluxului de iesire standard
		dup2(fd, STDOUT_FILENO);
		close(fd);
		if (execvp(args[0], args) < 0)
		{
			perror("execvp()");
			exit(EXIT_FAILURE);
		}
	
		exit(0);
	}
	else // in parinte
	{
		int status;
		wait(&status); // astept terminarea copilului
		//waitpid(pid, &status, 0) // echivalent cu cea de mai sus 
		printf("Parinte: Copilul cu pid %d a returnat %d \n", pid, status);
	}


return 0;
}
