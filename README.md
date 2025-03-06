# technical_assessment

# Transaction Management Service
A Spring Boot application that contains a batch job feature to read a file in a directory and RESTful API features for managing transactions.

## Features:

* **Batch processing:** Imports account's transaction data from a file in a directory using Spring Batch.
* **RESTful API:**
    * Retrieves record of transaction using a range of different parameters and supports pagination and filtering.
    * Updates transaction details and handle any concurrent updates.
    * Secured with basic authentication.

## Technologies:

* Java
* Spring Boot
* Spring Batch

