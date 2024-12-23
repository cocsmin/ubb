#!/bin/bash

for FILE in `find fol -type f | grep ".text$"`
do	
	sort "$FILE" -o "$FILE"
done

