#!/bin/sh

#загрузка настроек
source ./deploy.conf;
source ./functions.conf;

#подготовка
prepare;
#установка пакетов
installPackages;
#загрузка файлов
download;
#сборка
build;
#удаление предыдущей версии
cleanJBOSS_HOME;
#копирование файлов
copyFiles;
#запуск
start;

