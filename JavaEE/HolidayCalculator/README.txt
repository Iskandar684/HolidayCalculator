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
   3а. Скопировать собранный 
   holiday.calculator.service/target/holiday.calculator.service.jar и 
   holiday.calculator.report.service/holiday.calculator.report.service.jar
   по следующему пути:
   /wildfly-10.1.0.Final/standalone/deployments/holiday.ear
   
   3б. В папку    /wildfly-10.1.0.Final/standalone/deployments/holiday.ear/lib скопировать
  - org.eclipse.birt.runtime/org.eclipse.birt.runtime-patched-4.6.0.jar
   
   3в. Скачать из http://download.eclipse.org/birt/downloads/ org.eclipse.birt.runtime-4.6.0.zip
   Распаковать и скопировать папку содержимое папки birt-runtime-4.6.0-20160607/ReportEngine/lib/ в
   /wildfly-10.1.0.Final/standalone/deployments/holiday.ear/lib
   
   3г. В папку /wildfly-10.1.0.Final/standalone/deployments/ скопировать 
   holiday-calculator-web-service.war
   
   3д. Скачать Tidy.jar и скопировать в /wildfly-10.1.0.Final/standalone/deployments/holiday.ear/lib
       <groupId>org.eclipse.birt.runtime.3_7_1</groupId>  
       <artifactId>Tidy</artifactId>
       <version>1</version>
       <packaging>jar</packaging>
       <name>Tidy.jar</name>
   3е. В папку /wildfly-10.1.0.Final/standalone/deployments/report/ 
   скопировать файлы с расширением .rptdesign из /holiday.calculator.service/src/main/resources/ru/iskandar/holiday/calculator/service/ejb/report
   3ж. В папку wildfly-10.1.0.Final/welcome-content/скопировать файлы (*html, *js, ...) из holiday.calculator.web.client
   3з. В папку    /wildfly-10.1.0.Final/standalone/deployments/holiday.ear/lib 
       из /holiday.calculator.client-libraries/lib скопировать
       elasticsearch-core.jar
       elasticsearch-rest-client.jar
       elasticsearch-rest-high-level-client.jar
       elasticsearch-x-content.jar
       elasticsearch.jar
       hppc.jar
       httpasyncclient.jar 
       httpclient.jar
       httpcore-nio.jar
       httpcore.jar
       jackson-core.jar
       joda-time.jar
       lang-mustache-client.jar
       lucene-core.jar
       lucene-queries.jar
       rank-eval-client.jar   

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

7. Разрешить вызов методов, для которых не определено полномочие
wildfly-10.1.0.Final\standalone\configuration\standalone.xml
<default-missing-method-permissions-deny-access value="false"/>

8. В папке wildfly-10.1.0.Final/standalone/deployments/ создать holiday.ear.dodeploy

Запуск сервера.
1. Запустить postgresql
(service postgresql start)
2. Запустить elasticsearch-7.2.0\bin\elasticsearch 
3. Запустить wildfly-10.1.0.Final\bin\standalone.sh

Инструкция по настройки БД.

1. Создание базы данных.
Чтобы установить PostgreSQL в Linux, выполните следующие команды:
sudo apt-add-repository ppa:pitti/postgresql
sudo apt-get update
sudo apt-get install postgresql

Попробуем поработать с СУБД через оболочку:
sudo -u postgres psql

Создадим базу данных и пользователя БД:
postgres=# CREATE DATABASE holiday_calculator_db;
CREATE DATABASE

postgres=# CREATE USER holiday_calculator_user WITH password 'qwerty';
CREATE ROLE

postgres=# GRANT ALL privileges ON DATABASE holiday_calculator_db TO holiday_calculator_user;
GRANT
Для выхода из оболочки введите команду \q.

2. Добавление драйвера
Качаем драйвер из https://jdbc.postgresql.org/download.html.
для java8: postgresql-42.2.6.jar
В корневой папке WildFly находим папку modules, внутри нее создаем иерархию папок org/postgres/main. 
Кидаем туда драйвер.
Создаем тут же файл module.xml. В нем пишем:
<module xmlns="urn:jboss:module:1.0" name="org.postgres"> 
  <resources> 
    <resource-root path="postgresql-42.2.6.jar"/> 
  </resources> 
   <dependencies> 
     <module name="javax.api"/> 
     <module name="javax.transaction.api"/> 
   </dependencies> 
</module> 

3. Добавление datasource
В папке с WildFly редактируем файл \standalone\configuration\standalone.xml
<datasource jndi-name="java:jboss/datasources/PostgreDataSource" pool-name="PostgreDataSource " enabled="true" jta="true" use-java-context="true" use-ccm="true"> 
                     <connection-url> 
                                jdbc:postgresql://localhost:5432/holiday_calculator_db 
                     </connection-url> 
                     <driver> 
                                postgresql 
                     </driver> 
                     <security> 
                                <user-name> 
                                          holiday_calculator_user 
                                </user-name> 
                                <password> 
                                          qwerty
                                </password> 
                     </security> 
                  </datasource> 
                  
Также добавляем новый драйвер в соответствующий тэг.

 <driver name="postgresql" module="org.postgres"> 
        <xa-datasource-class> 
                org.postgresql.xa.PGXADataSource 
        </xa-datasource-class> 
</driver>
                   
(см http://eax.me/postgresql-install/) 


Добавление пользователей.
1. Выполнить wildfly-10.1.0.Final/bin/add-user.*
2. Полномочия:
   рассматрение заявления: consider
   просмотр пользователей: user_viewer
   полномочие на создание пользователя: user_creator
3. Первого пользователя создать через sql скрипты в БД:     
   SET search_path = public, pg_catalog;
   INSERT INTO ru_iskandar_holiday_calculator_user (uuid, employmentdate, firstname, lastname, login, note, patronymic)
   VALUES ('b8420836-c728-11e9-aa8c-2a2ae2dbcce4', '2019-08-25 14:09:01.478', 'Василий', 'Петров', 'user1', 'Примечание', 'Андреевич');
   

Настройки для отладки.
  1. Чтобы к БД можно было подключиться из другого хоста:
     в файл /etc/postgresql/9.6/main/postgresql.conf добавить
     listen_addresses = '*'
     в файл /etc/postgresql/9.6/main/pg_hba.conf
     host all all 0.0.0.0/0 md5
     перезагрузить: service postgresql restart
     проверка: sudo netstat -pant | grep postgres



Инструкция по разворачиванию клиента.

1. Переменную JAVA_HOME указать на jdk8 

2. Распаковать holiday.calculator.product/trunk/target/products/holiday.calculator.product-*.zip в любую папку.

3. Запустить calculator.exe (calculator.sh).
