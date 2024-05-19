# Прохождение курса Java Developer. Basic
## Курсовой проект веб-сервер.

### Реализовано:
+ Серверная часть:
  + Полный набор CRUD операций над продуктами:
    + `GET /items` – получение всех продуктов;
    + `GET /items?id=1` – получение продукта, с указанным id; 
    + `GET /items/{id}` – получение продукта, с указанным id;
    + `POST /items` – создание нового продукта;
    + `PUT /items` – модификация продукта;
    + `DELETE /items?id=1` – удаление продукта с указанным id;
    + `DELETE /items/id=1` – удаление продукта с указанным id;
    + `GET /[имя_файла]` - получить в теле ответа указанный файл из папки static;
    + `GET /[имя_файла.html]` - отобразить html страницу из папки static;
  + Обработчик исключений, с преобразованием их в 400/500 ответы;
+ Клиентская часть:
  + index.html;
  + index.js;
### База данных PostgreSQL 14-alpine.
#### DDL:
```
CREATE DATABASE product_repository;
```
```
CREATE TABLE public.product
(
    id    INT PRIMARY KEY,
    title varchar(128) UNIQUE NOT NULL,
    price INT                 NOT NULL,
);
```
### docker-compose.yml
```
version: '3.9'
    
services:
  db: 
    container_name: pg_db
    image: postgres:14-alpine
    environment: 
      POSTGRES_USER: root
      POSTGRES_PASSWORD: root
      POSTGRES_DB: my_db
    volumes:
      - ./postgres_data:/var/lib/postgresql/data/
    ports:
      - "5432:5432"
      
  pgadmin:     
    container_name: pgadmin
    image: dpage/pgadmin4
    environment: 
      PGADMIN_DEFAULT_EMAIL: noemail@noemail.com
      PGADMIN_DEFAULT_PASSWORD: root
    ports:
      - "5050:80"
```
### Запуск сервера из консоли:
```
java -Dport=8180 -Durl=jdbc:postgresql://localhost:5432/product_repository -Duser=root -Dpassword=root -jar web-server-jar-with-dependencies.jar
```