#!/bin/bash

export SERVER_PATH="/home/iskandar/Desktop/hc-server"

export ARCHIVE_PATH="./holiday.calculator.product.server/trunk/target"


unzip -o $ARCHIVE_PATH/HolidayCalculator-server-server.zip -d $ARCHIVE_PATH


cp -rf $ARCHIVE_PATH/HolidayCalculator-server/* $SERVER_PATH