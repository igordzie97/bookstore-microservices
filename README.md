# Book Store

## Spis Treści

## Cel oraz Architektura
Celem projektu było stworzenie systemu backendowego realizującego zadania obsługi księgarni internetowej. 
Architektura systemu oparta jest o paradygmat mikroserwisowy. Mikroserwisy komunikują się pomiędzy sobą wykorzystując FeignClient. Wyodrębnione zostały następujące mikroserwisy:
- **Bookstore-Gateway** - odpowiada za przekazywanie zapytań do odpowiednich mmikroserwisów (Zuul Proxy) oraz filtrowanie ich na podstawie uprawnień.
- **Bookstore-Products** - realizuje logikę związaną z zasobami dostępnymi w systemie - dodawanie autorów, książek, prezentacja istniejących zasobów.
- **Bookstore-Orders** - obsługa oraz rejestr zamówień.
- **Bookstore-Baskets** - realizacja logiki związanej z koszykiem - zapamiętywanie zawartości, obliczanie jego wartości, przypisywanie koszyka do użytkownika.
- **Bookstore-Accounts** - obsługa rejestracji użytkowników, mechanizm logowania do systemu (JWT).
- **Bookstore-Storage** - obsługa zapisu i prezentacji plików.
- **Eureka-Service** - rejestr dostępnych mikroserwisów.

<img width="550" alt="Screenshot 2021-08-15 at 13 28 31" src="https://user-images.githubusercontent.com/34041060/130590312-53528a03-4feb-4281-88b0-dadb29433364.png">

## Technologie
- **Java 11 + Spring Boot** - backend
- **FeignClient** - komunikacja między serwisami
- **ZUUL Proxy** - API Gateway
- **Eureka Server** - rejestr mikroserwisów
- **Swagger** - automatyczna dokumentacja metod RestAPI
- **Elastic, Logstash, Kibana, Zipkin** - monitoring aplikacji
- **Docker / Docker Swarm** - konteneryzacja

## Najważniejsze Endpointy
1. **POST /accounts-service/registration** - rejestracja użytkownika, w postaci FormData.
- username, password, email, name, surname

2. **POST /accounts-service/auth** - autoryzacja użytkownika, w postaci FormData
- username, password
- Jako odpowiedź dostajemy Bearer Token, wykorzystywany do reszty requestów, w którym "zaszyta" jest jedna z 4 ról: admin, employee, user, notRegisteredUser.
 
3. **POST /products-service/admin/author** - dodanie autora

4. **POST /products-service/admin/book** - dodanie książki

5. **GET /baskets-service/cart** - utworzenie koszyka, podczas którego dopisuje się cookie z Id koszyka, które jest aumtoatycznie dodawane do kolejnych requestów.

6. **POST /baskets-service/cart/{PRODUCT_ID}** - dodanie produktu do koszyka.

7. **DELETE /baskets-service/cart/{PRODUCT_ID}** - usunięcie produktu z koszyka.

8. **POST /orders-service/order** - sprawdzany jest stan magazynowy, następnie składane jest zamówienie z zapisanym cookie. 
-Jeśli wykonamy tą metodę z dodanym Bearerem - to doda się paramter Mode:"User Zalogowany" do Order i z accounts-service pobierze się ID zalogowanego użytkownika i też doda się do zamówienia.

## Testy automatyczne - Cypress
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

## Adresy
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

## Dokumentacja
- [Dokumentacja](https://github.com/igordzie97/bookStore/blob/main/Ksi%C4%99garnia%20internetowa%20-%20dokumentacja.pdf)

## Autorzy
- Igor Dzierwa
- Adrian Nędza
- Konrad Makuch
