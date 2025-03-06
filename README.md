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

## Batch Job

The batch job reads data from a text file located at `src/main/resources/dataSource.txt`. The file format is: .txt

**Steps:**

1.  **Read:** `FlatFileItemReader` reads each line of the file.
2.  **Process:** `TransactionItemProcessor` transforms the data into `TransactionOrder` class.
3.  **Write:** `JdbcBatchItemWriter` persists the record to the database.

**Triggering the Batch Job:**

The batch job will be triggered automatically when running the application.

## REST API Endpoints

**Authentication:**

All API endpoints require Basic Authentication.  (Example users/passwords are defined in `SecurityConfig.java` -  e.g., user/password).

*   Handles concurrent updates using optimistic locking (`version` field).  Returns a 409 Conflict status if the version doesn't match.

**Transactions:**
*   `GET /api/transactions`: Retrieve all transactions (with pagination).
    *   Query Parameters:
        *   `page` (int, default: 0): Page number.
        *   `size` (int, default: 10): Page size.
*   `GET /api/transactions/{id}`: Retrieve an transaction based on the ID.
*   `GET /api/transactions?customerId={customerId}`: Retrieve transactions by customer ID (with pagination).
*   `GET /api/transactions?accountNo={accountNumber}`: Retrieve transactions by account number (with pagination).
*   `GET /api/transactions?accountNo={accountNumber}&accountNo={accountNumber2]`: Retrieve transactions by account number can have multiple values (with pagination).
*   `GET /api/transactions?description={description}`: Retrieve transactions by description (with pagination).
*   `GET /api/transactions?customerId={customerId}&accountNo={accountNumber}&description={description}`: Parameters can also be combined (with pagination).

## Design Patterns

*   **Model-View-Controller (MVC):** The application is structured following the MVC pattern.
*   **Service Layer Pattern:**  `TransactionService` encapsulate business logic.
*   **Specification:** `TransactionSpecification` pattern to allow the combination of multiple params into a set of specification

## Build and Run

1.  **Prerequisites:**
    *   Java 17 or later
    *   Maven

2.  **Build:**
    ```bash
    mvn clean install
    ```

3.  **Run:**
    ```bash
    mvn spring-boot:run
    ```

## Diagrams
Class diagram
![image](https://github.com/user-attachments/assets/36b8dcd1-5e3e-4006-9d41-43f7b4d621ae)

Activity diagram
![image](https://github.com/user-attachments/assets/f1640239-cf04-48b5-a34a-10a6b2a706f7)

