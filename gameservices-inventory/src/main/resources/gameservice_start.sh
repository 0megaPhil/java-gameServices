#!/bin/bash

pkill -ef 'java -jar'
nohup java -jar ~/$(ls *gameservices*.jar) > gameservices.log 2>&1 & exit &