#!/bin/bash
#asta nu inteleg de ce for some reason la toate imi ia numele fisierului
#pe care il dau ca argument hence all the echo peste tot, daca descoperi tu
nr=$1
shift
for file in $@
do
	for linie in "$file"
	do
		echo $linie
		for cuvant in $linie
		do
			echo $cuvant
			aparitii=$(grep -c "\<$cuvant\>" "$file")
			echo $aparitii
			if [ $aparitii -gt $nr ]
			then
				echo $cuvant
			fi
		done
	done
done
