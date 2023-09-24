#!/bin/bash

mvn clean install -DskipTests=true 

sh ./deploy.sh