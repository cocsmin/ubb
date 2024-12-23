#!/bin/bash

if [ $# -lt 2 ]
then
	echo "nr mic de arg"
	exit 2
fi

cuv=$1
shift

for file in $@
do
	contor=0
	contor=$(grep -c "\b$cuv\b" $file)
	#echo $contor
	echo "Cuvantul $cuv apare in $file de $contor ori."
done
