#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
int main(int argc, char *argv[])
{
	//deschid fifo
	int pd = open("fifo1", O_WRONLY);
	if (pd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	//scriu mesajul
	char msg[] = "Salut!";
	write(pd, msg, sizeof(msg));


	//inchid fifo
	close(pd);


	return 0;
}
