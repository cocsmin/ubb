#!/bin/bash
#asta nu stiu sigur daca merge in teorie nu are de ce sa nu mearga
#mi-e prea lene sa verific totu in detaliu zic ca i pierdere de timp
#miau
nr=$1
cuv=$2
shift 2
for file in $@
do
	contor=0
	aparitii=$(grep -c "\<$cuv\>")
	if [ $aparitii -gt $nr ]
	then
		for linie in $file
		do
			if [ $aparitii -eq $nr ]
			then
				echo $linie
			fi
			if [ $linie == $cuv ]
			then
				aparitii=$((aparitii-1))
			fi
		done
	fi
			
done
