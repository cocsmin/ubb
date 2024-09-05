//Se citeste de la tastatura un sir de numere in baza 10. Sa se afiseze numerele prime.
//Programul C va citi sirul si va afisa nr prime
#include <stdio.h>
int verificare_prim(int x);

int main()
{   
    //int a[100] = {0};
    int x = 0;
    int ok = 0;
    printf("Introduceti numerele (0 to stop): ");
    do
        {
        scanf("%d",&x);
        ok = verificare_prim(x);
        if (ok)
            {
            printf("%d", x, "\n");
            //printf("\n");
            }
        }
    while (x);

return 0;
}
