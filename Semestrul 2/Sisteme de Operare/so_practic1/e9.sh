#!/bin/bash
#aici am schimbat structura sa ma obisnuiesc si cu asta, probabil mergea 
#si cu  for file in $@
while [ $# -gt 0 ]
do
	file=$1
	nr=$(grep -c "[0,2,4,6,8]$" $file)
	echo "Fisierul $file are $nr numere pare"
	shift
done
