# LibraryAPI

## Feature:
* Spring-boot
* JPA
* PostgreSQL
* JWT token with refresh token
* Swagger-ui

## Instalation

Clone this repository
```
git clone https://github.com/JakubMikolajczyk/LibraryAPI.git
```
Connect your database in [application.properties](https://github.com/JakubMikolajczyk/LibraryAPI/blob/070e7efd243ace998dfa23a864ad2d327fa8e7a8/src/main/resources/application.properties)
and create tables [create.sql](https://github.com/JakubMikolajczyk/LibraryAPI/blob/070e7efd243ace998dfa23a864ad2d327fa8e7a8/create.sql)

Then run spring server
```
mvn spring-boot:run
```
After run server you can see [swagger documentation](http://localhost:8080/swagger-ui/index.html#)

## API endpoints

### Everyone:
* POST api/v1/login
* POST api/v1/register
* GET api/v1/refresh-token
---
* GET api/v1/books
* GET api/v1/books{bookId}
* GET api/v1/books/{bookId}/specimens
---
* GET api/v1/authors
* GET api/v1/authors/{authorId}
---
* GET api/v1/categories
* GET api/v1/categories/{categoryId}
---
* GET api/v1/specimens
* GET api/v1/specimens/{specimenId}

### User:
* GET, PUT api/v1/users/me
* GET api/v1/users/me/borrows
* GET api/v1/users/me/borrow-histories?showHidden=false
* POST api/v1/users/me/borrow-histories{historyId}/hide
* POST api/v1/users/me/borrow-histories{historyId}/unhide
* POST api/v1/users/me/change-password

### STAFF and ADMIN:
* GET api/v1/users
* GET api/v1/users/{userId}
* GET api/v1/users/{userId}/borrows
* GET api/v1/users/{userId}/borrow-histories
---
* POST api/v1/categories
* GET, PUT, DELETE api/v1/categories/{categoryId}
---
* POST api/v1/books
* POST api/v1/books/{bookId}/specimens
* PUT, DELETE api/v1/books/{bookId}
---
* POST api/v1/authors
* PUT, DELETE api/v1/authors/{authorId}
---
* POST api/v1/specimens
* DELETE api/v1/specimens/{specimenId}
---
* GET, POST api/v1/borrows
* POST api/v1/borrows/byUsername
* GET, POST, DELETE api/v1/borrows{borrowId}
---
* GET api/v1/borrow-histories
* GET api/v1/borrow-histories/{historyId}

### ADMIN:
* PUT, DELETE api/v1/users/{userId}
