```markdown
# 🚀 Employee Management System (Spring Boot REST API)

A **production-ready REST API backend** built with **Spring Boot 3**, **MySQL**, **JPA**, **JWT Authentication**, and **Flyway migrations**.  
This guide walks you through building, running, and deploying the project step-by-step.

---

## 📋 Prerequisites

Before starting, ensure the following tools are installed and running:

- [ ] **IntelliJ IDEA** (Community/Ultimate)
- [ ] **JDK 21**
- [ ] **MySQL Server** (`localhost:3306`)
- [ ] **MySQL Workbench** (optional)
- [ ] **Postman/Thunder Client** for API testing

---

## ⚙️ Project Initialization

### 1️⃣ Create the Project

#### Option A: IntelliJ Spring Initializr
1. Open IntelliJ → **File → New → Project**
2. Select **Spring Initializr**
3. Configure:
```

Name: employee-management-system
Group: com.jayaa
Artifact: ems
Package: com.jayaa.ems
JDK: 21
Type: Maven
Packaging: Jar
Language: Java

````

4. Click **Next**

#### Option B: Web Initializr
1. Go to [https://start.spring.io](https://start.spring.io)
2. Set the same fields as above
3. Add dependencies and download the project ZIP
4. Extract and open in IntelliJ

---

## 🧩 Dependencies

### Essential:
- Spring Web
- Spring Data JPA
- MySQL Driver
- Spring Security
- Validation
- Lombok (optional)

### Additional (`pom.xml`):
```xml
<!-- JWT Authentication -->
<dependency>
 <groupId>io.jsonwebtoken</groupId>
 <artifactId>jjwt-api</artifactId>
 <version>0.11.5</version>
</dependency>
<dependency>
 <groupId>io.jsonwebtoken</groupId>
 <artifactId>jjwt-impl</artifactId>
 <version>0.11.5</version>
 <scope>runtime</scope>
</dependency>
<dependency>
 <groupId>io.jsonwebtoken</groupId>
 <artifactId>jjwt-jackson</artifactId>
 <version>0.11.5</version>
 <scope>runtime</scope>
</dependency>

<!-- Flyway for DB migrations -->
<dependency>
 <groupId>org.flywaydb</groupId>
 <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
 <groupId>org.flywaydb</groupId>
 <artifactId>flyway-mysql</artifactId>
</dependency>

<!-- Swagger / OpenAPI -->
<dependency>
 <groupId>org.springdoc</groupId>
 <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
 <version>2.3.0</version>
</dependency>

<!-- ModelMapper -->
<dependency>
 <groupId>org.modelmapper</groupId>
 <artifactId>modelmapper</artifactId>
 <version>3.2.0</version>
</dependency>
````

---

## 🏗️ Folder Structure

```
com.jayaa.ems
├── config/          # Security + App Config
├── controller/      # REST Endpoints
├── dto/             # Data Transfer Objects
├── exception/       # Custom Exceptions
├── model/           # JPA Entities
├── repository/      # Data Access Layer
├── security/        # JWT & Filters
├── service/         # Business Logic
└── util/            # Helper Utilities
```

---

## 🗄️ Database Setup

### 1️⃣ Create MySQL Database

```sql
CREATE DATABASE ems CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ems_user'@'localhost' IDENTIFIED BY 'ems_pass';
GRANT ALL PRIVILEGES ON ems.* TO 'ems_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2️⃣ Configure `application.yml`

```yaml
spring:
  application:
    name: employee-management-system
  
  datasource:
    url: jdbc:mysql://localhost:3306/ems?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    username: ems_user
    password: ems_pass
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQLDialect
  
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true

server:
  port: 8080
  error:
    include-message: always

app:
  jwt:
    secret: YourSuperSecretKeyMustBeAtLeast32CharactersLongForHS256Algorithm
    expirationMs: 86400000

file:
  upload-dir: uploads
```

---

## 🧱 Entities (Model Layer)

* **Employee**
* **AppUser**

Defined with JPA annotations and managed by Flyway migrations.

Migration file: `src/main/resources/db/migration/V1__init.sql`

---

## 🧬 Repository Layer

* `EmployeeRepository`
* `UserRepository`

Includes `@Query` support and pagination.

---

## 💼 Service Layer

* **EmployeeService**: CRUD, pagination, search, validations
* **AuthService**: Registration, Login, JWT generation

Uses `@Transactional` and `BCryptPasswordEncoder`.

---

## 🔐 Security Layer

* **JwtUtil** → Token creation & validation
* **JwtFilter** → Request-level authentication
* **SecurityConfig** → Endpoint protection + stateless session

### Exposed (Permit All)

```
/auth/**
/v3/api-docs/**
/swagger-ui/**
/swagger-ui.html
```

---

## 🧠 Controller Layer

### `/auth`

* `POST /auth/register` → Register new user
* `POST /auth/login` → Login + JWT token

### `/api/employees`

* `GET /api/employees` → List all employees (Paginated)
* `GET /api/employees/{id}` → Get employee by ID
* `POST /api/employees` → Create employee *(Admin only)*
* `PUT /api/employees/{id}` → Update employee *(Admin only)*
* `DELETE /api/employees/{id}` → Delete employee *(Admin only)*
* `GET /api/employees/search?q=John` → Search employees

---

## 🧾 Exception Handling

Create `ResourceNotFoundException` and global exception handler under `exception/`.

---

## 🧪 Testing APIs

### 1️⃣ Register User

`POST /auth/register`

```json
{
  "username": "user1",
  "password": "password123"
}
```

### 2️⃣ Login

`POST /auth/login`

```json
{
  "username": "user1",
  "password": "password123"
}
```

Response → JWT Token
Use this token for all `/api/employees` routes.

### 3️⃣ Test CRUD APIs

Add header:
`Authorization: Bearer <token>`

---

## 📘 Swagger API Docs

Once running:

```
http://localhost:8080/swagger-ui.html
```

---

## 🚀 Build & Run

### Run via IntelliJ

Click ▶️ next to `EmsApplication.java`

### Run via Maven

```bash
./mvnw clean install
./mvnw spring-boot:run
```

---

## 🧰 Build Output

```
Application started on http://localhost:8080
```

Default Admin Login (from Flyway):

```
Username: admin
Password: admin123
```

---

## ✅ Project Summary

| Layer              | Description                       | Status |
| ------------------ | --------------------------------- | ------ |
| Model + Migration  | Entities & Flyway setup           | ✅      |
| Repository         | Data access + search + pagination | ✅      |
| Service            | CRUD + Auth business logic        | ✅      |
| Security           | JWT + Spring Security integration | ✅      |
| Controller         | REST endpoints                    | ✅      |
| Exception Handling | Custom exceptions & validation    | ✅      |
| Testing            | Postman & Swagger verified        | ✅      |
| Deployment Ready   | Yes                               | ✅      |

---

## 🌍 Next Steps (Optional Enhancements)

* [ ] Add file upload for employee profile images
* [ ] Add audit logging (Spring AOP)
* [ ] Add refresh tokens for JWT
* [ ] Containerize with Docker
* [ ] CI/CD using GitHub Actions
* [ ] Deploy to AWS EC2 / Render

---

### 🏁 You’re Ready!

You now have a **fully functional, secure, and scalable Spring Boot REST API**
for Employee Management — ready for production deployment.
---

```
```
