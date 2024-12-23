cmake_minimum_required(VERSION 3.27)
project(lab7sda)

set(CMAKE_CXX_STANDARD 14)

include_directories("Proiect C++")
include_directories(Teste)

add_executable(lab7sda
        "Proiect C++/App.cpp"
        "Proiect C++/IteratorMDO.cpp"
        "Proiect C++/IteratorMDO.h"
        "Proiect C++/MDO.cpp"
        "Proiect C++/MDO.h"
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h)
