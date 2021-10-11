# BookStore

## Table of Consents
1. [Description](#description)
2. [Architecture](#architecture)
3. [Technologies](#technologies)
4. [Most important endpoints](#most-important-endpoints)
5. [Cypress Automated Tests](#cypress-automated-tests)
6. [Zipkin](#zipkin)
7. [Addresses](#addresses)
8. [Docker Swarm](#docker-swarm)
9. [Documentation](#documentation)

## Description
The project puropse is to create the backend system which will support the book store activities.

## Architecture
System architecture is based on microservices. Communication between them is done using Feign Client.

The following microservices are highlighted:
- **Bookstore-Gateway** - gateway service that provides dynamic routing between microservices and filtering based on permissions.
- **Bookstore-Products** - products service, adding authors, books and data presentation.
- **Bookstore-Orders** - orders service, making order based on created basket.
- **Bookstore-Baskets** - basket service, assigning basket to user, value calculation.
- **Bookstore-Accounts** - registration, authentification and authorization service.
- **Bookstore-Storage** - save and file presentation service.
- **Eureka-Service** - holds the information about all available microservies.

<img width="550" alt="Screenshot 2021-08-15 at 13 28 31" src="https://user-images.githubusercontent.com/34041060/130590312-53528a03-4feb-4281-88b0-dadb29433364.png">

## Technologies
- **Java 11 + Spring Boot** - Backend
- **FeignClient** - Communication between microservices
- **ZUUL Proxy** - API gateway
- **Eureka Server** - Microservices register
- **Swagger** - Automated documentation for describing RESTful APIs (expressed using JSON)
- **Elastic, Logstash, Kibana, Zipkin** - Application monitoring
- **Docker / Docker Swarm** - App containerization

## Most important endpoints
1. **POST /accounts-service/registration** - user registration (FormData interface).
- username, password, email, name, surname

2. **POST /accounts-service/auth** - user authentication (FormData interface).
- username, password
- Bearer token as a response. Token is attached to every request and it has one of four roles encoded: admin, employee, user, notRegisteredUser.
 
3. **POST /products-service/admin/author** - adding author.

4. **POST /products-service/admin/book** - adding book.

5. **GET /baskets-service/cart** - creating basket, cookie with basket id is saved for using in the next requests.

6. **POST /baskets-service/cart/{PRODUCT_ID}** - adding product to basket.

7. **DELETE /baskets-service/cart/{PRODUCT_ID}** - deleting product from basket.

9. **POST /orders-service/order** - checking products availability and creating the order based on saved cookie.
- When we send request with Bearer token attached - in the response we will also see the ID of logged user.

## Cypress Automated Tests
Automated tests are based on JavaScript framework, Cypress. It suites really well for both frontend and backend automation.

Setting up (within cypress-bookstore folder):
- `npm install` - installing all modules which are defined in package.json
- `npm run open` - open Cypress Test Runner

**Accounts-service/Accounts.spec.js** - tests of accounts service: 
- user registration, signing in, admin account check.

**Products-service/Products.spec.js** - tests of products service:
- adding author, adding book, resources presentation.

**Baskets-Orders-service/Baskets-Orders.spec.js** - complex tests of baskets and orders services: 
- getting specific book, creating basket, adding and removing products from basket, making orders based on specific basket.

## Zipkin
After running all servies and making requests between them, we can see logs from specific services (clicking on `run query`). Whole configuration is added to docker-compose.

Setting up without docker compose: `run -d -p 9411:9411 openzipkin/zipkin`. 

## Addresses
- **Main Gateway**: http://localhost:9091
- **Swagger:** http://localhost:9091/swagger-ui.html
- **Zipkin:** http://localhost:9411
- **Kibana:** http://localhost:5601 (credentials: elastic / password)

## Docker Swarm
1) `docker swarm init` - starting master node swarm
2) `docker-compose build` - creating images which will be deployed on stack
3) `docker-compose pull` - pulling images which don't have Dockerfile
4) `docker stack deploy --compose-file docker-compose.yml bookstore` - stack deployment to swarm
5) `docker stack services bookstore` - checking status of all servies

Deleting stack and quiting from swarm node:
1) `docker stack rm bookstore` - deleting stack
2) `docker swarm leave --force` - quiting from swarm node

## Documentation
- [App documentation (in Polish)](https://github.com/igordzie97/bookStore/blob/main/Ksi%C4%99garnia%20internetowa%20-%20dokumentacja.pdf)

## Authors
- Igor Dzierwa
- Adrian Nędza
- Konrad Makuch
