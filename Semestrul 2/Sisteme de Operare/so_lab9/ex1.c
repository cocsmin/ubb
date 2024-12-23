#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAX_SIZE 100
int main(int argc, char *argv[]){
	char sir[]="aeiouBaeiouC";
	char vocale[]="aeiouAEIOU";
	int i=0;
	while(i<strlen(vocale)){
		int c2p[2];
		if(pipe(c2p)==-1){
			perror("pipe()");
			exit(1);
		}
		int pid=fork();
		//verificam caz de eroare
		if(pid==-1){
			perror("fork()");
			exit(1);
		}
		//lucram in copil
		if(pid==0){
			close(c2p[0]);
			char *rez=malloc((strlen(sir)+1)*sizeof(char));
			int k=0;
			for (int j=0;j<strlen(sir);j++){
				if (sir[j]!=vocale[i])
					rez[k++]=sir[i];
			}
			rez[k]='\0';
			printf("sizeof: %d strlen: %d\n",sizeof(rez),strlen(rez));
			write(c2p[1],rez,MAX_SIZE);
			free(rez);
			close(c2p[1]);
			exit(0);
		}
		//pentru ca am dat exit(0) acum lucram in parinte
		close(c2p[1]);
		char tmp[MAX_SIZE];
		read(c2p[0],tmp,MAX_SIZE);
		strcpy(sir,tmp);
		wait(NULL);
		close(c2p[0]);
		i=i+1;
	}

	printf("Sir final: %s\n", sir);



	return 0;
}
