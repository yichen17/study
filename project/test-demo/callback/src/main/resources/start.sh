#!/bin/sh
ip=`ifconfig eth0 | grep "inet addr:" | awk '{print $2}' | cut -c 6-`
nohup java -jar callback-cloud-0.0.1.jar -Xms2048m -Xmx2048m  >/dev/null &