[![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.oracle.com/java/)
![Lines of Code](https://img.shields.io/badge/lines_of_code-817-green)
![License](https://img.shields.io/badge/license-MIT-blue)

**Telegram Bot for Vacancies Bot** s a component of the Vacancies Bot application built on a microservices architecture. It registers new users to PostgreSQL database.

## Requirements
- Java 8 or higher
- PostgreSQL server running locally or accessible via network
- Maven for building the project

## Installation
1. Clone the repository:
```shell
git clone https://github.com/kyljmeeksi/vacancies-bot-telegram-bot.git
```
2. Navigate to the project directory:
```shell
cd vacancies-bot-telegram-bot 
```
3. Build the project using Maven:
```shell
mvn clean install
```

## Configuration
Modify the following parameters in Main.java to match your PostgreSQL setup:
```java
String url = "jdbc:postgresql://localhost:5432/vacancies-bot";
String user = "postgres";
String password = "postgres";
```
Ensure the PostgreSQL server is configured correct.

## Usage
1. Run the application:
```shell
java -jar vacancies-bot-telegram-bot.jar
```

