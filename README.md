# Country Routing Service

## Design decisions

- Spring MVC instead of WebFlux because:
    - workload is CPU-bound BFS
    - dataset is loaded once
    - no reactive stream processing required

- BFS algorithm:
    - finds shortest land route
    - complexity O(V+E)

- CountryGraph:
    - immutable singleton
    - thread-safe

## Requirements

- Java 21
- Maven 3.9+


## Build

```shell
mvn clean package
```

## Run

```shell
java -jar target/country-routing-service-1.0-SNAPSHOT.jar
```


## Endpoint

`GET /routing/{origin}/{destination}` 


Example:

`GET /routing/CZE/ITA`

Response:

```text
{
"route": ["CZE", "AUT", "ITA"]
}
```

If route does not exist:

`HTTP 400`

## Docker

### Build

```shell
docker build -t routing-service .
```
### Run

```shell
docker run -p 8080:8080 routing-service
```

### Endpoint

```shell
curl http://localhost:8080/routing/CZE/ITA
```

### Swagger

#### Swagger UI page: http://localhost:8080/swagger-ui/index.html

#### OpenAPI description: http://localhost:8080/v3/api-docs
