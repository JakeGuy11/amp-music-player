#!/usr/bin/bash

#While the process with the first pid entered exists
while kill -0 $1 2> /dev/null; do
    #Wait a little so we don't take up all the cpu
    sleep .1
done


#It doesn't exist anymore. Kill the second process if it exists
if kill -0 $2 2> /dev/null; then kill $2; fi