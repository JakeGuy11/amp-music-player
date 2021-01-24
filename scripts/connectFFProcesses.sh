#!/bin/bash

#While the process with the first pid entered exists
while kill -0 $1 2> /dev/null; do
    #Print that the process is alive
    echo "$1 is Running"

    #Wait a little so we don't take up all the cpu
    sleep .1
done

#It doesn't exist anymore. Kill the second process
kill $2