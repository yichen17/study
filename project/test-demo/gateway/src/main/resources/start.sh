#!/bin/sh
ip=`ifconfig eth0 | grep "inet addr:" | awk '{print $2}' | cut -c 6-`
nohup java -Dcsp.sentinel.app.type=1 -Dcsp.sentinel.dashboard.server=localhost:8866 -Dproject.name=gateway-sentinel -jar gateway-0.0.1.jar -Xms2048m -Xmx2048m  &
