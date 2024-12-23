#!/bin/bash

contor=0
for file in `find dir -type f | grep '.c$'`
do
	#echo $contor
	if [ $contor -eq 2 ]
        then
                break
        fi
	nr=$(sort "$file" | wc -l)
	if [ $nr -gt 500 ]
	then
		echo $file
		contor=$((contor+1))
	fi
	
done
