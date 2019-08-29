Инструкция по разворачиванию сервера.

1. Переменную JAVA_HOME указать на jdk8 
	-для Linux:
	JAVA_HOME=/opt/jdk1.8.0
	export JAVA_HOME
	PATH="$JAVA_HOME/bin:$PATH"

2. Скачать Jboss и ES
    2а. Скачать WildFly (Jboss) версии 10.1.0.Final из сайта:
        http://wildfly.org/downloads/
    2б. Скачать elasticsearch версии 7.2.0
        https://www.elastic.co/downloads/elasticsearch

3. Скопировать модули 
   
   по следующему пути:
   /wildfly-10.1.0.Final/standalone/deployments/holiday.ear