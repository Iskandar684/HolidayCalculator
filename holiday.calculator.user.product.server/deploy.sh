#!/bin/bash

export SERVER_PATH="/home/iskandar/Desktop/hc-server"
export SERVER_ARCHIVE_PATH="./holiday.calculator.user.product.server/target"

function deployServer(){
   rm -rf $SERVER_PATH/standalone/deployments
   mkdir $SERVER_PATH/standalone/deployments
   unzip -o $SERVER_ARCHIVE_PATH/HolidayCalculator-user-server.zip -d $SERVER_ARCHIVE_PATH
   cp -rf $SERVER_ARCHIVE_PATH/HolidayCalculator/* $SERVER_PATH
}



deployServer
