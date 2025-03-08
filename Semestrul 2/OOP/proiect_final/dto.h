//
// Created by Cosmin Secrier on 17.04.2024.
//
#include <string>
class DTO{
public:
    DTO() {}
    DTO(int count) : count(count){}
    int getCount()  {
        return count;
    }
    void increment(){
        count++;
    }
private:
    int count;
};
