#!/bin/bash

director=$1

if [ $# -eq 0 ]
then
	echo "Nr insuficient de argumente."
	exit 2
fi

if [ ! -d $director ]
then
	echo "Numele dat ca argument nu e al unui director."
	exit 2
fi

contor=0
for file in `find "$director" -type f | grep -E ".c$"`
do
	contor=$((contor+1))
done

echo $contor
