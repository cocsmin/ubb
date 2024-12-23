cmake_minimum_required(VERSION 3.27)
project(sda)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(sda
        Colectie.cpp
        Colectie.h
        IteratorColectie.cpp
        IteratorColectie.h)
