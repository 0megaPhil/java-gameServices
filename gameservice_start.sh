#!/bin/bash
# Expects environment to be added
# TODO - Setup proper logging via logback
pkill -ef 'java -jar' & pkill -ef 'java -jar' & pkill -ef 'java -jar'
sleep 15 # FIXME with proper checking that old process is dead and port is clear
source sshenv
export SERVICE_COMMAND="java -jar ~/$(ls *gameservices*.jar) --server.port=8080 \
--spring.datasource.url=$SPRING_DATASOURCE_URL --spring.datasource.username=$SPRING_DATASOURCE_USERNAME \
--spring.datasource.password=$SPRING_DATASOURCE_SECRET"
nohup bash -c "$SERVICE_COMMAND" > gameservices.log 2>&1 & exit &