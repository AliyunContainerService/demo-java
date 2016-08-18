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

docker build -t registry.cn-hangzhou.aliyuncs.com/jiangjizhong/demo-java:latest .

