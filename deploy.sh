#!/bin/bash

export SERVER_PATH="/home/iskandar/Desktop/hc-server"
export SERVER_ARCHIVE_PATH="./holiday.calculator.product.server/trunk/target"

export CLIENT_PATH="/home/iskandar/Desktop/hc-client"
export CLIENT_ARCHIVE_PATH="./holiday.calculator.product/trunk/target/products"


function deployServer(){
   unzip -o $SERVER_ARCHIVE_PATH/HolidayCalculator-server-server.zip -d $SERVER_ARCHIVE_PATH
   cp -rf $SERVER_ARCHIVE_PATH/HolidayCalculator-server/* $SERVER_PATH
}


function deployClient(){
  rm -rf $CLIENT_PATH
  unzip -o $CLIENT_ARCHIVE_PATH/holiday.calculator.product-linux.gtk.x86_64.zip -d $CLIENT_PATH
}

deployServer
deployClient