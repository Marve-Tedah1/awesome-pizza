# awesome-pizza
Questa repository è stata definita per un servizio che gestisce ordini per una pizzeria usando SpringBoot e la versione 17 di Java 

## Run locally
```bash
mvn spring-boot:run
# app -> http://localhost:8080
# db  -> http://localhost:8080/h2-console (URL = jdbc:h2:mem:pizzadb  User Name= sa)
```

## Unit Test
```bash
mvn test
```

## REST Test
Per poter effettuare i tests delle varie chiamate rest, possiamo usare sia Postman sia lo swagger quindi sarebbe a preferenza
```bash
# link per lo swagger -> http://localhost:8080/swagger-ui/index.html#/
```