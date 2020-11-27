# Project description
Sample project to demonstrate some Spring Reactor basic features.  
The project contains two SpringBoot applications:
  * **patient-registry**: it is a full reactive application that exposes some REST APIs to manipulate entities like _Patient, Clinical Documents and Contacts._
The data are stored into a MongoDB, which is initialized at the application startup.  
Every endpoint can be called with a delay query parameter: `?delay={delay-in-seconds}`
* **consumer-app**: it is the client application. It exposes an API to get the full Patients list, retrieving the information from the _patient-registry_. 
This SpringBoot Application implements such API with both Spring MVC and Spring Reactive.  
There are some test classes with code samples:
  + **WebFluxExampleTests**: it shows some features of `Mono` and `Flux` publishers (e.g., publisher initialization, log, block, and backpressure)
  + **SpringMvcVsWebFluxPerformanceTests**: it contains some tests useful to compare the performance of Spring MVC and Spring Reactor. To successfully run the tests, 
  `patient-registry` **must be up & running**.
  + **ReactivePatientConsumerServiceUTests**: it contains some unit tests written using both `Assertions` (from assertj) and `StepVerifier` (from project reactor).

# Project setup
1. Run `docker-compose up` in the root folder of the `patient-registry` application. It runs a MongoDB docker image.
2. Start the `patient-registry` SpringBoot Application. After the application startup, the MongoDB should contain some test data.
3. Start the `consumer-app` SpringBoot Application:
    + Use `classic` as active profile to run the application using the **Spring MVC** stack
    + Use `reactive` to run with the **Spring Reactive** stack

The `postman-collection` folder contains a `.json` file to be imported into Postman as a collection, with a set of REST API endpoints to start using both `patient-registry` and `consumer-app` SpringBoot Applications.
