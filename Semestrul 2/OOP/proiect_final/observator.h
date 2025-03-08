//
// Created by Cosmin Secrier on 26.05.2024.
//

#pragma once
#include <vector>
#include <algorithm>

class Observator{
public:
    virtual void update() = 0;
};

class Observabil{
private:
    std::vector<Observator*> observatori;
public:
    void adauga_obs(Observator* obs){
        observatori.push_back(obs);
    }
    void sterge_obs(Observator* obs){
        observatori.erase(std::remove(observatori.begin(), observatori.end(), obs), observatori.end());
    }
    void notifica(){
        for (auto obs : observatori)
            obs->update();
    }
};