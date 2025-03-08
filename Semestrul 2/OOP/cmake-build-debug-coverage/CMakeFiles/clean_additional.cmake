# Additional clean files
cmake_minimum_required(VERSION 3.16)

if("${CONFIG}" STREQUAL "" OR "${CONFIG}" STREQUAL "Debug")
  file(REMOVE_RECURSE
  "CMakeFiles/oop_lab7_autogen.dir/AutogenUsed.txt"
  "CMakeFiles/oop_lab7_autogen.dir/ParseCache.txt"
  "oop_lab7_autogen"
  )
endif()
