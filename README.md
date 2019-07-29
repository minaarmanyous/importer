# Product Importer Microservice


## Description

- The importer microservice uses Spring Batch to process CSV files that contain a list of products then send them as JSON messages using Kafka.
- Spring batch is a comprehensive, lightweight and scalable framework to perform the bulk processing which is typically the objective of the importer microservice.


## Getting Started

#### Running the application
- Service requires Kafka to be up and running so docker compose that enclose Kafka configurations should be started in order to start Kafka server.
- Docker service should be running in machine in order to start the docker compose.
- Run the docker compose using this command "docker-compose up"
- Docker will start downloading the Kafka image then start it on port 9092

To run the microservice, those commands should be executed:
- mvn spring-boot:run
- The application run on port 8080
- Finally, you need to call the endpoint explained below to start importing the CSV file.

#### Running the tests
- mvn test

#### Application Configurations
- H2 database is added as dependency because it is being used by Spring Batch to store relevant metadata.
- Log4j by default support console and file logging at the level of info and debug respectively however this can be tuned by editing the logging configuration file "Log4j2.xml".
- Application has DockerFile on the root folder to support creating a docker image for the service if desired. 
- Kafka producer can be configured from the config file "KafkaConfig".

## Endpoints
#### GET /products/import
This endpoint triggers the Spring Batch job to executes which in turn starts reading and processing the CSV file.


## How it works

- Once the endpoint is called, the controller will start executing the Spring Batch import job.
- Subsequently, the job reads the dataExample.csv file and map each line to a product.
- Eventually, after file reading process is completed, Kafka starts sending those products through a JSON messages.  


## Technologies
- Spring Boot
- Spring Batch
- Apache Kafka
- Jackson
- Log4j2
- Mockito
- JUnit
- Docker
- Lombok


## Test Cases
#### Kafka writer tests
- Testing that Kafka send calls are executed as per the number of received products.
#### Product processor tests
- Testing that product processor properly assigns the request date to the passed product.