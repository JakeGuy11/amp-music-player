#!/bin/bash

#Let the program know we're starting this script
echo "BASH: starting process connection script"

#While the process with the first pid entered exists
while kill -0 $1 2> /dev/null; do
    #Wait a little so we don't take up all the cpu
    sleep .1
done


#It doesn't exist anymore. Kill the second process if it exists
if kill -0 $2 2> /dev/null; then kill $2; fi

#Wait a little for the console output to catch up
sleep .25

echo "BASH: finished process connection script"