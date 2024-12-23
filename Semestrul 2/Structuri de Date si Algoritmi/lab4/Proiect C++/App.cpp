#include <iostream>


#include "TestScurt.h"
#include "TestExtins.h"

int main(){
    printf("Test scurt...");
    testAll();
    printf("passed \n");
    printf("Test lung...");
    testAllExtins();
    printf("passed \n");
    std::cout<<"Finished LP Tests!"<<std::endl;
}
