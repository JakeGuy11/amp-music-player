#!/bin/bash

#Go into our media folder
cd ./media

#Repeat for each mp3
for FILE in *.mp3
do
#Rename the file to its current name, but with _s instead of " "s
mv "./$FILE" "`echo "$FILE" | tr " " _`"
done

echo "Files renamed and moved"