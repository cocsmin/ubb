#!/bin/bash

for ARGUMENT in $*
	if [ -d ARGUMENT ]
		echo $ARGUMENT
		NF=$(find $ARGUMENT -type f | grep -c '.')
		echo $NF		
	elif [ -f ARGUMENT ]
		echo $ARGUMENT
		echo $(wc -l <$ARGUMENT) #numar de linii
		echo $(wc -m <$ARGUMENT) #numar de caractere
	fi 

done
exit 0
