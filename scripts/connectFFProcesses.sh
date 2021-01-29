#!/bin/bash

echo "BASH: starting process connection script"

#While the process with the first pid entered exists
while kill -0 $1 2> /dev/null; do
    #Wait a little so we don't take up all the cpu
    sleep .1
done

#TODO: Check of proc 2 exists, only kill it if it does

#It doesn't exist anymore. Kill the second process
kill $2

echo "BASH: finished process connection script"