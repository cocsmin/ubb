#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h> 
#include <fcntl.h>
#include <string.h>

#define MAX_SIZE 100
int main(int argc, char*argv[]){
	int n=10;
	for (int i=2;i<n/2;i++){
		int pid=fork();
		if (pid==-1){
			perror("fork()");
			exit(1);
		}
		if (pid==0){
			FILE* fd=fopen("numere.txt", "r");
        		if (fd == NULL){
                		perror("fopen()");
                		exit(1);
        		}
			FILE* fd2=fopen("tmp","w");
			if (fd2 == NULL){
				perror("fopen()");
				exit(1);
			}
			int nr=0;
			char sir[MAX_SIZE];
			while (fscanf(fd,"%d",&nr)>0){
				//for (int j=i;j<n;j++){
					if (nr%i != 0){
					//transforma int in sir	cu sprintf
						fprintf(fd2, "%d ", nr);					
					}
				//}
			}
	
			fclose(fd);
			fclose(fd2);
			remove("numere.txt");
			rename("tmp","numere.txt");
			exit(0);
		}
		wait(NULL);
	}

	return 0;
}
