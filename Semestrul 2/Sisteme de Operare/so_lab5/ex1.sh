#!/bin/bash


#verific numarul de argumente
if [ $# -eq 0 ]; then
	echo "Numar de argumente insuficient !"
	echo "Utilizare: $0 N"
	exit 1
fi

N=$1
for X in $(seq 1 $N)
do
	#cream fisierul
	touch 'file_'$X'.txt'
	#scriem in fisier
	sed -n ''$X',+5p' /etc/passwd > 'file_'$X'.txt'
done

exit 0
