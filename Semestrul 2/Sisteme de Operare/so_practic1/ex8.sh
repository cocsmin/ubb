#!/bin/bash

#for file in `cat df | tail -n 9`
	for dim in `cat df | tail -n 9 | awk '{print $3}' | sed 's/M$//'`
	do
		#rdim=$((dim))
		#echo $dim
		if [ $dim  1000 ]
		then
			awk '$3 ~ 0M {print $6}' df > rezultat.txt
		#elif 
		fi
	done
cat rezultat.txt
