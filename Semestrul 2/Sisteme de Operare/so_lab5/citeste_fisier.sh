#!/bin/bash

# ./citeste_fisier file1 file2 file3

#verific nr de argumente
if [ $# -eq 0 ]
then
	echo "Numar insuficient de argumente! "
	echo "Utilizare: $0 fisier1 fisier2 ..."
	exit 1
fi

for FILE in $*
do
	echo 'Fisier: ' $FILE
	#varianta 1
	#sed -n 'p' $FILE
	
	#varianta 2
	#grep '^.' $FILE
	
	#varianta 3
	#awk '{print}' $FILE
	
	#varianta 4
	#cat $FILE
	
	#varianta 5
	LINII=$(cat $FILE)
	for LINIE in $LINII
	do
		echo $LINIE
	done

done

exit 0
