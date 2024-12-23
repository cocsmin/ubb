cmake_minimum_required(VERSION 3.27)
project(sda_lab4)

set(CMAKE_CXX_STANDARD 14)

include_directories("Proiect C++")
include_directories(Teste)

add_executable(sda_lab4
        "Proiect C++/App.cpp"
        "Proiect C++/IteratorLP.cpp"
        "Proiect C++/IteratorLP.h"
        "Proiect C++/Lista.cpp"
        "Proiect C++/Lista.h"
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h)
