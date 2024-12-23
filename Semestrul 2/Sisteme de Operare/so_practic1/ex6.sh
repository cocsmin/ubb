#!/bin/bash

for file in `find dir -type f`
do
	if [ -w $file ]
	then
		#aici trebuie afisate permisiunile si numele inainte 
		chmod o-w "$file"
	        #aici trebuie afisate permisiunile si numele dupa
		#n-am nicio idee cum se face afisarea, daca iti vine tie
		#in rest programu merge!
	fi
done

