# Introduction
To reduce the amount of code written, I chose to use **Spring Data REST**, which provides automatically:
- All the operations for news and products, without requiring controllers and services
- Allows pagination, with query parameters
- Allows sorting, with query parameters
- It is still possible to do some kind of customization, with additional methods in the repository
As a result, the code provides products search and pagination, plus sorting.

The orders support was too complex for Spring Data REST, so I had to write a controller and a service for it.

For simplicity, I opted for basic authentication, and I used **Spring Security**.
In the order, I used **Bean Validation** to force a minimum quantity of 1.

To reduce the code even more, I used **ddl-auto**, so there is no need to create the tables.

In order tor educe the boilerplate, I used **Lombok**.

# How to test the code
Please start MySQL, using *docker-compose up* or *sudo docker-compose up*. If MySQL runs on another computer or in a Virtual Machine, you need to change the application.yml file.

THen you can start the application, with the class **eu.lucaventuri.api.Application**.

The application run son port 8088, and the easiest way to test it is to use **IntelliJ HTTP Client**, running the queries present in **src/main/http**

As not everybody uses IntelliJ, and the HTTP Client might require IntelliJ Ultimate, I converted the HTTP calls to **curl** class, for your convenience; they are under **src/main/curl**.

A third way is using the **Swagger UI**, as I added **Spring Fox 3.0**; the UI is available here:

http://localhost:8088/swagger-ui/


GET requests can also be called from a web browser.

I created two users:
* **user1**, with password **user1**, with **CUSTOMER** role
* **admin1**, with password **admin1**, with **ADMIN** role


Spring Data REST implements HATEOAS, so the result is a bit verbose, but it is simple to use and to navigate.
The reference of Spring Data REST is here:

https://docs.spring.io/spring-data/rest/docs/current/reference/html/

Section 5 explains pagination (using the *size* parameter) and sorting (using the *sort* parameter)

https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting

