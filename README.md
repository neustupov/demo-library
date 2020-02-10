## demo-library

#### app port - 8090
#### swagger url - http://localhost:8090/swagger-ui.html#/

#### Java8, SpringBoot 2.2.4, H2 DB, JUnit5, Tomcat, Maven

Создать REST-сервис книжного магазина по поиску, добавлению и удалению книг из реестра 

Хранилище магазина представляет собой набор стеллажей, с тремя уровнями, на каждом уровне выставлены книги в случайном порядке 

Требуется написать следующий API:

- Получение книги по ее id 
##### GET /api/v1/books/{id}

- Получение списка книг по id cтеллажа 
##### GET /api/v1/racks/{id}

- Либо по номеру уровня
##### GET /api/v1/books/level/{level}

- Либо по обоим параметрам одновременно
##### GET /api/v1/books/rack/{rack}/level/{level}

- Добавление книги на определенный стеллаж и уровень 
##### POST /api/v1/books/rack/{rack}/level/{level}

- Удаление книги по id 
##### DELETE /api/v1/books/{id}

- Обновление информации по id книги  
##### PUT /api/v1/books/{id}

- Поиск книги в хранилище по ее названию 
##### GET /api/v1/books/name/{name}
