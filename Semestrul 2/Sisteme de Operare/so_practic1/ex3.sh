#!/bin/bash

for file in `find dir -type f | grep ".log$"`
do
	sort "$file" -o "$file"
done

