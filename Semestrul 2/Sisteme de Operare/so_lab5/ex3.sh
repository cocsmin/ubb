#!/bin/bash

if [ $# -eq 0 ]
then
        echo 'Numar insuficient de argumente! '
        echo 'Utilizare $0 folder(director)'
        exit 1
fi

if [ ! -d $1 ]
then
	echo "Directorul $1 nu exista! "
	exit 2
fi
NTL=0
NF=0
for FILE in $(find $1 -type f | sort)
do
        #verific ca e de tip text
        if file $FILE | grep -q 'ASCII text$'
        then
		NL=$(cat $FILE | wc -l)		
		NTL=$((NTL+NL))
		NF=$((NF+1))
	fi
done

echo "Numarul mediu de linii: " $((NTL/NF))

exit 0
