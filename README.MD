# Voting App

This is a simple voting app that allows users to vote for their agreement or not in topics about anything.
It is built using the following technologies:

- Java 11
- Spring Boot 2.7.4
- Spring Web
- Spring Data MongoDB
- Spring Validation
- JUnit 5
- Mockito
- Kafka
- Docker
- Docker Compose
- Swagger v3
- Lombok
- MongoDB
- Maven
- Git

# Considerations

- Regarding the scheduled task, I decided to use a simple scheduler that runs every 1 minute to search for ended sessions and then prepare the event to be sent
  to the queue.
  I could have used a more complex scheduler, but I thought it would be overkill for this project. It has been implemented using an endpoint that can be called
  by an in-house scheduler / cloud scheduler.

- Regarding tests, I have implemented unit tests (there are some marked by a TODO comment as it would be almost the same) and didn't have time to implement
  integration tests.

- Regarding the database, I decided to use MongoDB because it is a NoSQL database, and I thought it would be a good fit for this project.
  I could have used a relational database, but I thought that noSQL would be more performant for the voting write operations.

- Regarding the queue, I decided to use Kafka because it is what the client uses, so I thought it would be a better choice.

- Regarding the documentation, I decided to use Swagger v3 because it is a very good tool for documenting APIs.

- Regarding the dockerization, I decided to use docker-compose because it is a very good tool for managing containers in a simple way.
 
- Regarding the API versioning, I decided to use the URI versioning because it is a very simple way to version the API and
  also because it is a very common way to version APIs. Some people prefer to use the header versioning, but I think it is not a good choice because it is
  brings more complexity to the API and also to the caching layer.
- Regarding performance tests, I decided to use JMeter because it is a very good tool, and it is very easy to use (test plan and generated data are in the
  /jMeter folder. I have implemented a simple test that simulates 1000 users voting for a topic.
  The test is not very complex, but it is enough to show that the application is being impacted by the endpoint being used to validate if the user is able to
  vote.

## Local setup instructions

1. You need to have docker and docker-compose installed in your machine. If you don't have it, you can download it
   from [here](https://www.docker.com/products/docker-desktop).
2. Clone the repository
3. Run the following command in the root folder of the project
   ```docker-compose up```
4. The app will be running in http://localhost:8080
5. To stop the app, run the following command in the root folder of the project
   ```docker-compose down```
6. There is a collection of postman requests in the root folder of the project called "voting-app.postman_collection.json".
   You can import it in your postman and use it to test the app.
7. To get the swagger documentation, go to http://localhost:8080/swagger-ui.html
8. To get the data that is being stored in mongoDB, go to http://localhost:8081/ once you create a topic/session/vote the data will be stored in the voting
   database.
9. To get the messages that are being sent to kafka, go to http://localhost:9021/ there is a topic called "voting-topic" where you can see the messages that are
   being sent with the scheduled job that is running every 60 seconds closing the session and sending the results to kafka.