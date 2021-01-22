#!/bin/bash

#IFS='#' read -r -a array <<< "$1"
#echo "${array[0]}"

rm -f ./gen/*
ffmpeg -f concat -safe 0 -i ./scripts/mylist.txt -an -qscale 0 -c copy ./gen/output.ts
ffplay -fs -autoexit ./gen/output.ts

echo "Done"