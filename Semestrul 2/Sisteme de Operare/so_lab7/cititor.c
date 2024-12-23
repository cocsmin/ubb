#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
int main(int argc, char *argv[])
{
	//deschid fifo
	int pd = open("fifo1", O_RDONLY);
	if (pd == -1)
	{
		perror("open()");
		exit(EXIT_FAILURE);
	}
	//scriu mesajul
	char msg[50];
	read(pd, msg, sizeof(msg));
	printf("Am citit mesajul: %s\n", msg);

	//inchid fifo
	close(pd);


	return 0;
}
