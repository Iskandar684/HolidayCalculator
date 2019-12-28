--удаление БД, если существует
DROP DATABASE IF EXISTS holiday_calculator_db;
--создание БД
CREATE DATABASE holiday_calculator_db;
\connect holiday_calculator_db;
-- создание пользователя БД
--CREATE USER holiday_calculator_user WITH password 'qwerty';

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT                      
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'holiday_calculator_user') THEN

      CREATE ROLE my_user LOGIN PASSWORD 'qwerty';
   END IF;
END
$do$;

GRANT ALL privileges ON DATABASE holiday_calculator_db TO holiday_calculator_user;
--создание таблицы пользователей приложения
CREATE TABLE public.ru_iskandar_holiday_calculator_user (
  uuid UUID NOT NULL,
  employmentdate TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  firstname VARCHAR(255) NOT NULL,
  lastname VARCHAR(255) NOT NULL,
  login VARCHAR(255) NOT NULL,
  note VARCHAR(5000),
  patronymic VARCHAR(255) NOT NULL,
  CONSTRAINT ru_iskandar_holiday_calculator_user_pkey PRIMARY KEY(uuid),
  CONSTRAINT uk_df4onudw808ut4isibfulx4jy UNIQUE(login)
) 
WITH (oids = false);

ALTER TABLE public.ru_iskandar_holiday_calculator_user
  OWNER TO holiday_calculator_user;
