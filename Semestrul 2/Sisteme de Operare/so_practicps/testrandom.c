#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
int main(int argc, char *argv[]){
	int n = random();
	printf("random : %d \n", n);
	int m = n % 100;
	printf("random : %d \n" , m);
	n = n % 200;
	printf("random : %d" , n);	

	return 0;
}
