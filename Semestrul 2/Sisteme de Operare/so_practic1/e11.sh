#!/bin/bash


gol="">file_report.info
gol="">user_report.info
gol="">error_report.info
for ARG in $@
do
	if [ -f $ARG ]
	then
		realpath $ARG>>file_report.info				
		sed -i '/ana/d' "$ARG"
	elif [ $(grep -c "\<$ARG\>" passwd) -gt 0 ]
	then
		echo $ARG>>user_report.info
		ps -u $ARG>>user_report.info
	else
		echo $ARG>>error_report.info
	fi
	#aici mai trebuie calculat cat % reprezinta fiecare dintre ele
	#mi-e prea somn si nici macar nu mi amintesc cum se calculeaza matematic
	
done
