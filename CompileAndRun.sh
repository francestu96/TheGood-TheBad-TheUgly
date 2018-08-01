#!/bin/bash -x

if [ $# != 1 ] 
then
	echo "usage: $0 <clientsNumber>"
	exit	
fi

set -e

javac -d bin src/goodbadugly/common/*.java
javac -d bin -cp src src/goodbadugly/server/Server.java
javac -d bin -cp src src/goodbadugly/clientpc/ClientPc.java

for i in $(seq $1)
do
	gnome-terminal --working-directory=$PWD -e "java -cp bin goodbadugly.clientpc.ClientPc"
done

clear
java -cp bin goodbadugly.server.Server
