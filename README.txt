Инструкция по разворачиванию сервера.

1. Переменную JAVA_HOME указать на jdk8 

2. Скачать WildFly (Jboss) версии 10.1.0.Final из сайта:
http://wildfly.org/downloads/

3. Скопировать собранный holiday.calculator.service/trunk/target/holiday.calculator.service.jar по следующему пути:
/wildfly-10.1.0.Final/standalone/deployments/ 

4. Добавить пользователей с ролью guest 
(см. wildfly-10.1.0.Final/bin/add-user.bat)

5. В wildfly-10.1.0.Final\standalone\configuration\standalone.xml 
добавить    <jms-topic name="testTopic" entries="topic/test java:jboss/exported/jms/topic/test"/>

6. Запустить wildfly-10.1.0.Final/bin/standalone.bat


Инструкция по разворачиванию клиента.

1. Переменную JAVA_HOME указать на jdk8 

2. Распаковать holiday.calculator.product/trunk/target/products/holiday.calculator.product-*.zip в любую папку.

3. Запустить calculator.exe (calculator.sh).
