Инструкция по разворачиванию сервера.

1. Переменную JAVA_HOME указать на jdk8 
	-для Linux:
	JAVA_HOME=/opt/jdk1.8.0
	export JAVA_HOME
	PATH="$JAVA_HOME/bin:$PATH"


2. Скачать WildFly (Jboss) версии 10.1.0.Final из сайта:
http://wildfly.org/downloads/

3. Скопировать собранный holiday.calculator.service/trunk/target/holiday.calculator.service.jar по следующему пути:
/wildfly-10.1.0.Final/standalone/deployments/ 

4. Добавить пользователей с ролью guest 
(см. wildfly-10.1.0.Final/bin/add-user.bat)

5. В wildfly-10.1.0.Final\standalone\configuration\standalone.xml 
добавить    <jms-topic name="holidaycalculatorTopic" entries="topic/holidaycalculator java:jboss/exported/jms/topic/holidaycalculator"/>

6. Если клиент и сервер на разных хостах, то указать ip адрес сервера:
wildfly-10.1.0.Final\standalone\configuration\standalone.xml
<interfaces>
    <interface name="public">
       <any-ipv4-address/>
    </interface>
</interfaces>

7. Запустить wildfly-10.1.0.Final/bin/standalone.bat


Добавление пользователей.
1. Выполнить wildfly-10.1.0.Final/bin/add-user.*
2. Полномочие рассматривать заявления: consider


Инструкция по разворачиванию клиента.

1. Переменную JAVA_HOME указать на jdk8 

2. Распаковать holiday.calculator.product/trunk/target/products/holiday.calculator.product-*.zip в любую папку.

3. Запустить calculator.exe (calculator.sh).
