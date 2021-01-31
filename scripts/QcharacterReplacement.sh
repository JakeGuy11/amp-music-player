#!/usr/bin/bash

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