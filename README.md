# distributed-systems
Working Environment for the Distributed Systems Tutorial

## Requirements

- JDK 25 or newer
- Maven 3.9 or newer

## Build

```bash
mvn compile
```

## Run

```bash
# Exercise 1 - synchronized vs. non-synchronized counters
mvn exec:java -Dexec.mainClass=ex1.Exercise1

# Exercise 2 - HTTP clients (start ex2.HttpServer first)
mvn exec:java -Dexec.mainClass=ex2.Exercise2

# Exercise 2 - HTTP server on port 8080
mvn exec:java -Dexec.mainClass=ex2.HttpServer

# Hello World
mvn exec:java -Dexec.mainClass=HelloWorld
```

The HTTP server serves its files from `src/main/resources/ex2/www`.
