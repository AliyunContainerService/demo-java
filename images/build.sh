#!/bin/bash

set -x
set -e

if [ -f demo-java.war ]; then
    rm -f demo-java.war
fi

cd $(dirname $0)
(
cd ..
gradle war
cp build/libs/demo-java.war images/
)

GIT_SHA=`git rev-parse --short HEAD || echo "GitNotFound"`

export IMAGE="demo-java:$GIT_SHA"
docker build -t $IMAGE .
docker-compose -f ../docker-compose.yml up

