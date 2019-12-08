--удаление БД, если существует
DROP DATABASE IF EXISTS holiday_calculator_db;
--создание БД
CREATE DATABASE holiday_calculator_db;
-- создание пользователя
CREATE USER holiday_calculator_user WITH password 'qwerty';
GRANT ALL privileges ON DATABASE holiday_calculator_db TO holiday_calculator_user;