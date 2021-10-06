# Book Store

## Table of Consents
1. [Description](#cel-oraz-architektura)
2. [Architecture](#architecture)
3. [Technologies](#technologie)
4. [Most important endpoints](#most-important-endpoints)
5. [Cypress Automated Tests](#cypress-automated-tests)
6. [Zipkin](#zipkin)
7. [Addresses](#addresses)
8. [Docker Swarm](#docker-swarm)
9. [Documentation](#documentation)

## Description
The project puropse was to create a backend system which will support book store activities.

## Architecture
System architecture is based on microservices. Communication between them is done using Feign Client.

The following microservices are highlighted:
- **Bookstore-Gateway** - gateway service that provides dynamic routing between microservices, filtering based on permissions 
- **Bookstore-Products** - products service - adding authors, books and data presentation
- **Bookstore-Orders** - orders service - making order based on basket
- **Bookstore-Baskets** - basket service - assigning basket to user, value calculation
- **Bookstore-Accounts** - registration, authentification and authorization service
- **Bookstore-Storage** - save and file presentation service
- **Eureka-Service** - holds the information about all available microservies

<img width="550" alt="Screenshot 2021-08-15 at 13 28 31" src="https://user-images.githubusercontent.com/34041060/130590312-53528a03-4feb-4281-88b0-dadb29433364.png">

## Technologies
- **Java 11 + Spring Boot** - backend
- **FeignClient** - communication between microservices
- **ZUUL Proxy** - API gateway
- **Eureka Server** - microservices register
- **Swagger** - automated documentation for describing RESTful APIs expressed using JSON
- **Elastic, Logstash, Kibana, Zipkin** - application monitoring
- **Docker / Docker Swarm** - app containerization

## Most important endpoint
1. **POST /accounts-service/registration** - user registration (FormData interface)
- username, password, email, name, surname

2. **POST /accounts-service/auth** - user authentication (FormData interface)
- username, password
- Bearer token as a response. Token is attached to every request. It has one of four roles encoded: admin, employee, user, notRegisteredUser.
 
3. **POST /products-service/admin/author** - adding author

4. **POST /products-service/admin/book** - adding book

5. **GET /baskets-service/cart** - creating basket - utworzenie koszyka, podczas którego dopisuje się cookie z Id koszyka, które jest aumtoatycznie dodawane do kolejnych requestów.

6. **POST /baskets-service/cart/{PRODUCT_ID}** - adding product to basket.

7. **DELETE /baskets-service/cart/{PRODUCT_ID}** - deleting product from basket

9. **POST /orders-service/order** - checking products availability and creating an order based on saved cookie
-Jeśli wykonamy tą metodę z dodanym Bearerem - to doda się paramter Mode:"User Zalogowany" do Order i z accounts-service pobierze się ID zalogowanego użytkownika i też doda się do zamówienia.

## Cypress Automated Tests
Testy automatyczne oparte są o javascriptowy framework Cypress, nadający się zarówno do testów frontendu, jak również backendu.

Do uruchomienia - w obrębie folderu cypress-bookstore:

`npm install` - ściągnięcie wszystkich potrzebnych paczek (node_modules), które są zdefiniowane w package.json.

`npm run open` - uruchomienie cypress test runner.

**Accounts-service/Accounts.spec.js** - testy serwisu accounts, które obejmują: rejestrację użytkownika, jego zalogowanie oraz sprawdzenie konta administratora.

**Products-service/Products.spec.js** - testy serwisu products, które obejmują: dodanie autora, dodanie książki oraz prezentację wszystkich zasobów.

**Baskets-Orders-service/Baskets-Orders.spec.js** - kompleksowe testy serwisów baskets oraz orders, które obejmują: pobranie książki, utworzenie koszyka oraz dodanie jej do niego, usunięcie książki z koszyka i ponowne jej dodanie, by w ostateczności złożyć i pobrać zamówienie. 

## Zipkin
Jeśli chcemy uruchomić bez docker compose: `run -d -p 9411:9411 openzipkin/zipkin`

Zipkin odpala się na localhost:9411.

Odpalamy wszystkie serwisy i w zipkinie widzimy logi związane z odpytywaniem poszczególnych serwerów. Trzeba wywołać jakaś akcję, np rejestrację, potem w zipkin kliknąć "run query" i powinno się coś pojawić

Dodane też do docker-compose - jak odpalimy całość to zipkin też się odpali w dockerze.

## Addresses
- Main gateway: http://localhost:9091
- Swagger: http://localhost:9091/swagger-ui.html
- Zipkin: http://localhost:9411
- Kibana: http://localhost:5601 (elastic / password)

## Docker Swarm
1) `docker swarm init` - uruchamia master node swarma
2) `docker-compose build` - tworzy obrazy, które zostaną zdeployowane do stacku
3) `docker-compose pull` - pobiera obrazy, które nie posiadają Dockerfile'i
4) `docker stack deploy --compose-file docker-compose.yml bookstore` - deployment stacku do swarma
5) `docker stack services bookstore` - tutaj można sprawdzić stan wszystkich serwisów

Usunięcie stacku i wyjście z node'a:
1) `docker stack rm bookstore` - usunięcie stacku
2) `docker swarm leave --force` - wyjście z trybu swarm

## Documentation
- [Dokumentacja](https://github.com/igordzie97/bookStore/blob/main/Ksi%C4%99garnia%20internetowa%20-%20dokumentacja.pdf)

## Authors
- Igor Dzierwa
- Adrian Nędza
- Konrad Makuch
