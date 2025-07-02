# 👤 User Management System (Spring Boot + JWT + Role-Based Authorization)

This is a Spring Boot microservice project developed for secure user management. It includes features like user registration, login, JWT-based token authentication, and role-based access control using Spring Security. All endpoints are protected and tested via Postman, and exception handling is centralized using Spring’s `@ControllerAdvice`.

---

## 🚀 Features

- ✅ **User Registration** (`/register`)
- ✅ **User Login** with JWT Token (`/login`)
- 🔐 **Role-Based Authorization**
  - Supports `ROLE_USER` and `ROLE_ADMIN`
  - Default role assigned as `ROLE_USER` on registration
- 🔒 **Secured Endpoints** using JWT tokens and role-based access
- ⚠️ **Global Exception Handling** using `@ControllerAdvice`
- 🛡️ Integrated with **Spring Security**
- 🧪 Fully tested with **Postman**

---

## 🛠️ Tech Stack

This project is built using the following technologies:

- **Java 17** – for backend development
- **Spring Boot 3.5.3** – to simplify and structure the backend application
- **Spring Security** – to secure endpoints with JWT and roles
- **JWT (io.jsonwebtoken)** – for token-based authentication
- **Maven** – for dependency management and build automation
- **MySQL** – for persistent database storage (H2 can be used for in-memory testing)
- **Postman** – for manual testing of API endpoints

---

## 📁 Project Structure

The codebase is organized using standard layered architecture inside the `com.vik` package:

src/main/java
└── com.vik
├── UserManagementSystemApplication.java // Main class
├── advice // Global Exception Handling (@ControllerAdvice)
│ └── UserManagmentControllerAdvice.java
├── controller // REST Controller with all API endpoints
│ └── UserManagementController.java
├── entity // Entity class representing the User table
│ └── MyUser.java
├── service // Business logic + JWT service
│ ├── IUserManagementService.java
│ ├── UserManagementServiceImpl.java
│ └── JWTService.java
├── filter // Custom JWT filter for authentication
│ └── JWTFilter.java
├── model // DTO / Model class
│ ├── LoginUser.java
│ └── UserPrinciple.java
└── repository // Data persistence layer (JPA Repository)
└── IUserManagementRepo.java

src/main/resources
└── application.properties // Database configs, JWT secret, and server port

> This clean separation of concerns improves code readability, testability, and maintainability.

---

## 🔑 API Endpoints

### 🔓 Public
| Method | Endpoint     | Description                               |
|--------|--------------|-------------------------------------------|
| POST   | `/register`  | Register new user (default `ROLE_USER`)   |
| POST   | `/login`     | Authenticate and return JWT token         |

### 🔐 Secured (JWT Required)

| Method | Endpoint         | Access Level       |
|--------|------------------|--------------------|
| GET    | `/allUsers`      | `ROLE_ADMIN` only  |
| GET    | `/getByid/{id}`  | `ROLE_ADMIN` only  |
| GET    | `/users/{id}`    | `ROLE_USER` or `ROLE_ADMIN` |
| DELETE | `/users/{id}`    | `ROLE_ADMIN` only  |


---

## ⚙️ How to Run

### 🧑‍💻 Prerequisites
- Java 17+
- Maven installed
- MySQL Server running 
- Postman for testing endpoints


👤 Author
Vikram Gujar
Full Stack Java Developer





