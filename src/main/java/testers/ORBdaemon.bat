@echo off
start orbd -J-verbose -J-Djava.net.preferIPv4Stack=true -J-Djava.net.preferIPv6Addresses=false -ORBInitialPort 900 -ORBInitialHost 172.25.13.81