#!/bin/bash


if [ $# -eq 0 ]
then
	echo 'Numar insuficient de argumente! '
	echo 'Utilizare $0 folder(director)'
	exit 1
fi

DIR=$1
FISIERE=$(find $DIR -type f)
for FILE in $FISIERE 
do
	#verific ca e de tip text
	if file $FILE | grep -q 'ASCII text$'
	then
		echo Fisier: $FILE
		NR_LINII=$(grep -c '^.' $FILE)
		if [ $NR_LINII -lt 6 ]
		then
			cat $FILE
		elif [ $NR_LINII -ge 6 ]
		then
			head -3 $FILE
			tail -3 $FILE
		fi
	fi
done

exit 0
