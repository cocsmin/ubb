#!/bin/bash

for USER in `cat who | awk '{print $1}'`
do
#	echo $USER
	procese=0
	for NUME in `cat ps | awk '{print $1}' | sort`
	do
		if [ $USER = $NUME ]
		then
			procese=$((procese+1))
		fi
	done
	echo $USER $procese	
done
