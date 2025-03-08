cmake_minimum_required(VERSION 3.27)
project(oop_lab7)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(CMAKE_PREFIX_PATH "/Users/cosmin/Qt/6.7.0/macos/lib/cmake")

find_package(Qt6 COMPONENTS
        Core
        Gui
        Widgets
        REQUIRED)
add_executable(oop_lab7 main.cpp
        film.cpp
        film.h
        filmrepo.cpp
        filmrepo.h
        vectordinamic.h
        filmservice.cpp
        filmservice.h
        validator.cpp
        validator.h
        erori.h
        ui.cpp
        ui.h
        teste.cpp
        teste.h
        cosfilme.cpp
        cosfilme.h
        cosfilme.cpp
        dto.h
        undo.h
        filmGUI.cpp
        filmGUI.h
        CosCRUDGUI.h
        CosReadOnlyGUI.h
        observator.h
        modeltabel.h
)

target_link_libraries(oop_lab7
        Qt::Core
        Qt::Gui
        Qt::Widgets
)
