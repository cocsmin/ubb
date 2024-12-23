#!/bin/bash

if [ $# -le 0 ]
then
	echo "nr insuficient de argumente"
	exit 2
fi
#!!!!!!!!!
#nu intra in if ul ala in care verific daca suma e mai mica decat N
#FOR SOME REASON da deja stau de 15 min si ma tot chinui ma fut pe el
#pana acolo merge
while true
do
	read n
	if [ $n -eq 0 ]
	then
		n=$aux
		break
	fi
	aux=$n
done

for file in $@
do
	if [ -s $file ]
	then
		S=$(awk 'BEGIN{ total=0 } { total=total+$2 } END{ print total }' "$file")
		echo $S
		if [ $S -eq $n ]
		then
			echo "Am intrat in if bagamias pula in el if"
			M=$(($n-$S+1))
			echo $M
			$M >> "$file"
		fi
	fi
shift
done
