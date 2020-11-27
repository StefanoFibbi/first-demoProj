# Project description
Sample project to demonstrate some basic Spring Reactor features.  
The project contains two SpringBoot applications:
* **patient-registry**: is a full reactive applications that exposes some REST APIs to manipulate entities like _Patient, Clinical Documents and Contacts._
The data are stored into a MongoDB, which is initialized at the application startup.  
Every endpoint can be called with a delay query parameter: `?delay={delay-in-seconds}`
* **consumer-app**: is the client application. It exposes an API to get the full Patient list, retrieving the information from the _patient-registry_. 
This SpringBoot Application implements the API with both Spring MVC and Spring Reactive.  
There are some test classes with code samples:
  + **WebFluxExampleTests**: shows some features of `Mono` and `Flux` publishers (e.g: publisher initialization, log, block, backpressure)
  + **SpringMvcVsWebFluxPerformanceTests**: contains some tests useful to compare the performance of Spring MVC and Spring Reactor. To successfully run the tests, 
  `patient-registry` **must be up & running**.
  + **ReactivePatientConsumerServiceUTests**: contains some unit tests written using both `Assertions` (from assertj) and `StepVerifier` (from project reactor)

# Project setup
1. Run `docker-compose up` in the root folder of the `patient-registry` application. It runs a MongoDB docker image.
2. Run the `patient-registry` application. After the application startup, the MongoDB should contains some test data.
3. Run the `consumer-app`:
    + Set `classic` as active profile to run the application using the classic **Spring MVC** stack
    + Set `reactive` as active profile to run the application using the **Spring Reactive** stack

The `postman-collection` folder contains a `.json` file to be imported into Postman as a collection, with a set of REST APIs to start using both `patient-registry` and `consumer-app`
