#!/bin/bash

echo "BASH; beginning character replacement script"

#Go into our media folder
cd ./media

#Repeat for each mp3
for FILE in *.mp3
do
#Rename the file to its current name, but with _s instead of " "s
    if [[ $FILE =~ " " ]]; then 
        mv "./$FILE" "$(echo "$FILE" | tr " " "_")"
        #TODO: FOUND THE ISSUE! Replace all non-alphanumeric characters
    fi
done

echo "BASH: finished character replacement script"