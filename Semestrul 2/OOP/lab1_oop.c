#include <stdio.h>

int formula(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)

{
    /*
    * pre: x1, x2, x3, x4 (numere intregi) coordonatele de pe axa Ox y1, y2, y3 ,y4 (numere intregi) coordonatele de pe axa Oy
    * post: m nr intreg
    * ce face: returneaza formula pantei dreptelor
    */
    int m = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) /
        ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1));
    return m;

}

int pozX(int m, int x1, int x2)
{
    /*
    * pre: m (panta drepetei) x1, x2 (coordonatele de pe axa Ox)
    * post: x (coordonata x a punctului de intersectie)
    * ce face: calculeaza cu ajutorul pantei coordonata x a punctului de intersectie
    */
    int x = x1 + m * (x2 - x1);
    return x;
}

int pozY(int m, int y1, int y2)
{
    /*
    * pre: m (panta drepetei) y1, y2 (coordonatele de pe axa Oy)
    * post: y (coordonata y a punctului de intersectie)
    * ce face: calculeaza cu ajutorul pantei coordonata y a punctului de intersectie
    */
    int y = y1 + m * (y2 - y1);
    return y;
}

int main() {
    int M, x, y, optiune;
    printf("Meniu: \n");
    printf_s("1. Coordonate punct intersectie \n");
    printf_s("2. Exit\n");

    while (true) {
        printf_s("Introduceti optiunea: ");
        scanf_s("%d", &optiune);

        if (optiune == 1) {
            int x1, y1, x2, y2, x3, y3, x4, y4;
            printf_s("Introduceti coordonatele primului segment (x1 y1 x2 y2): ");
            scanf_s("%d %d %d %d", &x1, &y1, &x2, &y2);
            printf_s("Introduceti coordonatele celui de al doilea segment (x1 y1 x2 y2): ");
            scanf_s("%d %d %d %d", &x3, &y3, &x4, &y4);

            M = formula(x1, y1, x2, y2, x3, y3, x4, y4);
            x = pozX(M, x1, x2);
            y = pozY(M, y1, y2);

            printf_s("Coordonatele punctului de intersectie sunt: %d, %d\n", x, y);
        }
        else if (optiune == 2) {
            // printf_s(" Iesire\n");
            break;
        }
        else {
            printf_s("poate di introdus doar 1 sau 2.\n");
        }
    }

    return 0;
}