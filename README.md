# 👤 User Management System (Spring Boot + JWT + Role-Based Authorization)

This is a Spring Boot microservice for managing users, with built-in JWT authentication, role-based access control, and secure API endpoints.

---

## 🚀 Features

- ✅ **User Registration** (`/register`)
- ✅ **User Login** with JWT Token (`/login`)
- 🔐 **Role-Based Authorization**
  - `ROLE_USER` and `ROLE_ADMIN`
  - `ROLE_USER` assigned by default
- 🔒 **Secured Endpoints** using JWT and roles
- ⚠️ **Global Exception Handling** using `@ControllerAdvice`
- 🛡️ Spring Security Integration
- 🧪 REST tested using Postman

---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot 3.5.3
- Spring Security
- JWT (io.jsonwebtoken.Jwts)
- Maven
- MySQL (or use H2 for in-memory DB)
- Postman (for API testing)

---

## 📁 Project Structure



---

## 🔑 API Endpoints

### 🔓 Public
| Method | Endpoint     | Description          |
|--------|--------------|----------------------|
| POST   | `/register`  | Register new user (default `ROLE_USER`) |
| POST   | `/login`     | Authenticate and return JWT token |

### 🔐 Secured (JWT Required)

| Method | Endpoint         | Access Level   |
|--------|------------------|----------------|
| GET    | `/allUsers`      | ADMIN only     |
| GET    | `/getByid/{id}`  | ADMIN only     |
| GET    | `/users/{id}`    | USER or ADMIN  |
| DELETE | `/users/{id}`    | ADMIN only     |

> 🔐 Use JWT token in header for secured APIs:



---

## ⚙️ How to Run

### 🧑‍💻 Prerequisites
- Java 17+
- Maven
- MySQL (or use H2 if configured)
- Postman

### 🧪 Start the application

```bash
mvn clean install
mvn spring-boot:run
