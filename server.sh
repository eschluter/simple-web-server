#!/bin/bash

(cd target && exec java -cp ../lib/commons-cli-1.2.jar:. Server "$@")




