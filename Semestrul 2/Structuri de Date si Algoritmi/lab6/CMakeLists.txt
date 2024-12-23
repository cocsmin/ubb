cmake_minimum_required(VERSION 3.27)
project(Lab6sda)

set(CMAKE_CXX_STANDARD 14)

include_directories(Colectie)
include_directories(Teste)

add_executable(Lab6sda
        Colectie/App.cpp
        Colectie/Colectie.cpp
        Colectie/Colectie.h
        Colectie/IteratorColectie.cpp
        Colectie/IteratorColectie.h
        Teste/TestExtins.cpp
        Teste/TestExtins.h
        Teste/TestScurt.cpp
        Teste/TestScurt.h)
