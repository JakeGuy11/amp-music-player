#!/bin/bash

#Let the program know we're starting this script
echo "BASH: beginning character replacement script"

#Go into our media folder
cd ./media

#Start with replacing all the spaces
for FILE in *.mp3
do
    if [[ $FILE =~ " " ]]; then mv "./$FILE" "$(echo "$FILE" | tr " " "_")"; fi
done

#Replace all the opening brackets
for FILE in *.mp3
do
    if [[ $FILE =~ "(" ]]; then mv "./$FILE" "$(echo "$FILE" | tr "(" "_")"; fi
done

#Replace all the closing brackets
for FILE in *.mp3
do
    if [[ $FILE =~ ")" ]]; then mv "./$FILE" "$(echo "$FILE" | tr ")" "_")"; fi
done

#Replace all the apostrophes
for FILE in *.mp3
do
    if [[ $FILE =~ "'" ]]; then mv "./$FILE" "$(echo "$FILE" | tr "'" "_")"; fi
done

#Wait a while for the console output to catch up
sleep .25

#Let the program know we're done
echo "BASH: finished character replacement script"