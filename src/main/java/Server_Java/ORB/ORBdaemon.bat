@echo off
start orbd -J-verbose -J-Djava.net.preferIPv4Stack=true -J-Djava.net.preferIPv6Addresses=false -ORBInitialPort 2055 -ORBInitialHost 192.168.56.1
