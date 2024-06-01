## Project description
This project consists of 2 spring boot application, this one and balance-alert project. 

This main application once started creates a Account and Transaction table. During initialization it also inserts 2 accounts in the database so the APIs will be ready to be executed.
Two endpoints are exposed see [RestController](src/main/java/com/elpidoroun/MarlowBank/controller/BankAccountController.java) where you can withdraw and deposit money in a specific account.

The secondary application is responsible for listening to events produced by this main application when a withdrawal transaction is executed and the balance amount falls below 100, and it logs the error.

## Project Specification
- This project is developed using java 17 and maven wrapper
- Liquibase is used to generate tables for the application
- Docker-compose and "spring-boot-docker-compose" artifact is used to start appropriate containers required for the application during project startup
  - See docker-compose file [here](docker-compose.yml) which contains the 4 services needed
    - Postgres as a database
    - pgAdmin instance to connect to the database
    - kafka and zookeeper to send balance_alert_notifications to be consumed by the secondary application
- If you are using intelliJ you can run the application by running the "MarloBankApplication" stored in the repository
- After application startup you can go to http://localhost:5050/ click on servers and use password "password" to login pgAdmin to perform queries on the database

## Project setup
### Pre-requisites
Before running the application make sure you have the following tools installed
- [Docker](https://www.docker.com/)
    - Docker is required for docker-compose file to start containers necessary for the application to run

### Recommended Tools
#### Postman
- For testing and interacting with the application's APIs, [Postman](https://www.postman.com/downloads/) is recommended.
- In the repository, you can find a folder [postman_api_collection](./postman_api_collection) where you can import all the requests to run the APIs of this project

#### Kadeck
- Kadeck can be used to manage and monitor Apache kafka cluster
- To connect to the kafka cluster of the application (after starting the app, so docker-compose can create the container) create a new connection with url: 127.0.0.1:9092



## Stress testing the application
The application is built to handle multiple requests of deposit/withdrawals simultaneously as an account can be a joined account and more than one user can withdraw/deposit funds.
To run tests on this, you can use [JMeter](https://jmeter.apache.org/download_jmeter.cgi). Once downloaded use the test plan stored [here](./jmeter_test). 
The test runs a Thread Group of 15 Threads(users) and will perform multiple deposit/withdrawals on the same account. Once logged in pgAdmin you can run the query "select * from transaction order by timestamp" to check that the deposits and balance of the account were correct