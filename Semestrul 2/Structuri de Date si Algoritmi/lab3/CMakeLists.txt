cmake_minimum_required(VERSION 3.27)
project(sda)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)
include_directories("Proiect C++")
include_directories(Teste)

add_executable(sda
        "Proiect C++/App.cpp"
        "Proiect C++/CP.cpp"
        "Proiect C++/CP.h"
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h)
