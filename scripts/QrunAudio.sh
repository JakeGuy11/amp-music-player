#!/usr/bin/bash

#go to the scripts folder so we can preform actions from there
cd ./scripts

#start the concat command argument
CONCATCMD="concat:"

#Start a for loop from all the arguments, the arguments being the names of the video files (as a ts) in the media folder
for arg; do
    #append the file to play and a |, the recognized separated for concat
    CONCATCMD="$CONCATCMD$arg|"
done

#remove the last | from the command, since there are no files after
CONCATCMD=${CONCATCMD%|}

#go to the media folder (since the concat command can't take paths, we need to be in the same directory as the video files)
cd ../media/

#delete the output if it already exists
rm -f ../gen/audioOut.mp3

#concat all the specified inputs without audio
ffmpeg -i "$CONCATCMD" -acodec copy ../gen/audioOut.mp3