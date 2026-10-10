# Order State Management API

A RESTful backend application built using **Java, Spring Boot, Spring Data JPA, and Hibernate** to manage order creation and status transitions with transaction management and concurrency control.

## Features

- Create and manage orders.
- Maintain order lifecycle using an enum-based state machine.
- Validate status transitions before updating an order.
- Prevent invalid state changes.
- Use database transactions for consistent updates.
- Use pessimistic locking to handle concurrent status updates.
- Handle errors using custom exceptions and centralized exception handling.
- Persist order data using Spring Data JPA.

## Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Persistence:** Spring Data JPA, Hibernate
- **Database:** H2 / configured relational database
- **Build Tool:** Maven
- **API Testing:** Postman

## Order Lifecycle

The API supports the following transitions:

```text
CREATED ──────► CONFIRMED ──────► SHIPPED ──────► DELIVERED
   │                 │
   ▼                 ▼
CANCELLED         CANCELLED
```

### Allowed Transitions

| Current Status | Allowed Next Status |
|---|---|
| CREATED | CONFIRMED, CANCELLED |
| CONFIRMED | SHIPPED, CANCELLED |
| SHIPPED | DELIVERED |
| DELIVERED | None |
| CANCELLED | None |

Any transition not listed above is rejected.

## Order Model

Each order contains:

| Field | Description |
|---|---|
| `id` | Database-generated order ID |
| `userId` | Identifier of the user |
| `productId` | Identifier of the product |
| `quantity` | Number of units ordered |
| `status` | Current order lifecycle status |

The initial status of a newly created order is `CREATED`.

## API Endpoints

### 1. Create an Order

**Endpoint:** `POST /api/orders`

Request body:

```json
{
  "userId": "U1001",
  "productId": "P101",
  "quantity": 2
}
```

The API should create an order with the initial status `CREATED` and return the generated order ID along with the order details.

### 2. Update Order Status

**Endpoint:** `PUT /api/orders/{orderId}/status`

Example request:

```http
PUT /api/orders/1/status
Content-Type: application/json
```

Request body:

```json
{
  "status": "CONFIRMED"
}
```

The API validates the requested transition, updates the existing order, and returns the updated order when successful.

**Example invalid transition:**

```json
{
  "status": "DELIVERED"
}
```

If the current status is `CREATED`, this transition is rejected.

## Concurrency Control

The application uses pessimistic write locking when retrieving an order for a status update.

This helps serialize concurrent transactions attempting to update the same order. Each transaction reads the current status, validates the transition, and updates the order within a transaction.

The lock is held until the transaction completes.

## Exception Handling

The application uses centralized exception handling to return meaningful HTTP responses.

| Scenario | Expected HTTP Status |
|---|---|
| Order updated successfully | 200 OK |
| Order created successfully | 201 Created |
| Order does not exist | 404 Not Found |
| Invalid status transition | 400 Bad Request |
| Invalid request data | 400 Bad Request |

## Getting Started

### Prerequisites

- Java JDK compatible with your Spring Boot version
- Maven
- Git

### Clone the Repository

```bash
git clone https://github.com/ashu12355/Order-State-Management-LLD.git
cd Order-State-Management-LLD
```

### Run the Application

Using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

Or, if Maven is installed:

```bash
mvn spring-boot:run
```

The application runs on the configured server port. If configured to use port `1200`, the base URL is:

```text
http://localhost:1200
```

### Run Tests

```bash
./mvnw test
```

Or:

```bash
mvn test
```

## Project Structure

```text
src/
 ├── main/
 │   ├── java/
 │   │   └── com/example/orderStateManagement/
 │   │       ├── controller/
 │   │       ├── service/
 │   │       ├── repository/
 │   │       ├── entity/
 │   │       ├── enums/
 │   │       ├── dto/
 │   │       └── exception/
 │   └── resources/
 │       └── application.properties
 └── test/
     └── java/
```

The exact package names and folders may vary depending on the final implementation.

## Key Learning Outcomes

- Designing RESTful APIs with Spring Boot.
- Implementing state machines using Java enums and collections.
- Separating controller, service, repository, and entity responsibilities.
- Managing transactions with `@Transactional`.
- Using pessimistic locking for concurrent database updates.
- Applying exception handling and HTTP status codes.
- Implementing business rules independently of controller logic.

## Future Improvements

- Add optimistic locking using JPA `@Version` as an alternative concurrency strategy.
- Add comprehensive unit and integration tests.
- Add request validation using Bean Validation.
- Add API documentation using Swagger / OpenAPI.
- Add authentication and authorization using Spring Security.

## Author

**Ashutosh Sharma**

GitHub: [ashu12355](https://github.com/ashu12355)
