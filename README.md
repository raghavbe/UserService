# 👤 User Service

The **User Service** is the identity and access management backbone of the e-commerce ecosystem. It handles user registration, secure authentication using OAuth2 & JWT, and integrates with event-driven architecture to trigger asynchronous notifications.


## 🚀 Key Features

### Authentication & Security
* **User Registration (SignUp):** secure onboarding for new customers.
* **OAuth2 & Spring Security:** Robust security framework implementation for protecting APIs.
* **JWT Management:**
    * **Token Generation:** Issues JSON Web Tokens (JWT) upon successful login.
    * **Token Validation:** API endpoints to validate tokens for other microservices (like Product or Order Service).
* **Login API:** Secure entry point for user authentication.

### Event-Driven Architecture
* **Kafka Integration:** Implements a Kafka Producer to decouple communication.
* **Email Triggers:** Publishes events to Kafka topics (e.g., `user_registered`) which are consumed by the **Email Service** to send welcome emails or notifications.

### Infrastructure
* **Service Discovery:** Integrated client-side service discovery (likely Eureka/Consul) for dynamic registration within the microservices cluster.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security, OAuth2, JWT (jjwt/nimbus)
* **Messaging:** Apache Kafka
* **Database:** MySQL
* **Build Tool:** Maven

## 🔌 API Endpoints

The `UserController` exposes RESTful endpoints for user management and authentication.

| Method | Endpoint       | Description | Request Body / Params |
| :--- |:---------------| :--- | :--- |
| `POST` | `/signup`      | **Register** a new user. Triggers a Kafka event for the welcome email. | `{ "email": "...", "password": "...", "name": "..." }` |
| `POST` | `/login`       | **Authenticate** a user and return a JWT token. | `{ "email": "...", "password": "..." }` |
| `POST` | `/validate`    | **Validate** a JWT token. Used by other microservices (like Product Service) to verify identity. | `{ "token": "..." }` |
| `POST` | `/logout`      | Invalidate the current session/token (if stateful logout is implemented). | *Requires Bearer Token* |

## ⚙️ Configuration

To run this application locally, you need to configure the Database and Kafka settings. Update your `application.properties` or `application.yml`:

```properties
# Server Port
server.port=8081

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/user_db
spring.datasource.username=root
spring.datasource.password=your_password

# Kafka Configuration (For Email Events)
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.JsonSerializer

# JWT Configuration (Security)
jwt.secret=YOUR_VERY_SECURE_SECRET_KEY_HERE
jwt.expiration=3600000

# Service Discovery
spring.application.name=USER-SERVICE
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/