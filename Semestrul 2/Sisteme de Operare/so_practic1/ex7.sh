#!/bin/bash

str=gol
for USER in `cat input.txt`
do
	mail=$USER'@scs.ubbcluj.ro'
	if [ $str == gol ]
	then
		str=$mail
	else
		str=$str,$mail
	fi
done
echo $str
