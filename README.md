# Employee Management System

A REST API backend for managing employee records, built with Spring Boot 3, MySQL, and JWT authentication.

## What You Need

- IntelliJ IDEA
- JDK 21
- MySQL Server running on localhost:3306
- Postman or similar tool for testing APIs

## Getting Started

### Create the Project

**Using IntelliJ:**
1. File → New → Project
2. Select Spring Initializr
3. Fill in:
   - Name: `employee-management-system`
   - Group: `com.jayaa`
   - Artifact: `ems`
   - Package: `com.jayaa.ems`
   - JDK: 21
   - Type: Maven
   - Packaging: Jar

**Using start.spring.io:**
1. Visit https://start.spring.io
2. Use the same settings above
3. Download and extract the ZIP
4. Open in IntelliJ

### Add Dependencies

Add these to your `pom.xml`:

**JWT Authentication:**
```xml
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
```

**Database Migrations:**
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-mysql</artifactId>
</dependency>
```

**API Documentation:**
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

**Object Mapping:**
```xml
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.2.0</version>
</dependency>
```

## Database Setup

Open MySQL Workbench or your MySQL client and run:

```sql
CREATE DATABASE ems CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ems_user'@'localhost' IDENTIFIED BY 'ems_pass';
GRANT ALL PRIVILEGES ON ems.* TO 'ems_user'@'localhost';
FLUSH PRIVILEGES;
```

Create `src/main/resources/application.yml`:

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

## Project Structure

```
com.jayaa.ems/
├── config/         Security and application configuration
├── controller/     REST API endpoints
├── dto/            Data transfer objects
├── exception/      Custom exception classes
├── model/          JPA entities
├── repository/     Database access layer
├── security/       JWT utilities and filters
├── service/        Business logic
└── util/           Helper classes
```

## Database Migrations

Create `src/main/resources/db/migration/V1__init.sql` with your table definitions for Employee and AppUser entities. Flyway will automatically run this on startup.

## API Endpoints

### Authentication

**Register a new user:**
```
POST /auth/register
Content-Type: application/json

{
  "username": "user1",
  "password": "password123"
}
```

**Login:**
```
POST /auth/login
Content-Type: application/json

{
  "username": "user1",
  "password": "password123"
}
```

Returns a JWT token. Use this token in the Authorization header for protected endpoints.

### Employee Management

All employee endpoints require authentication. Add the header:
```
Authorization: Bearer <your-jwt-token>
```

**Get all employees (paginated):**
```
GET /api/employees?page=0&size=10
```

**Get employee by ID:**
```
GET /api/employees/{id}
```

**Create employee (admin only):**
```
POST /api/employees
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "department": "Engineering"
}
```

**Update employee (admin only):**
```
PUT /api/employees/{id}
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.updated@example.com",
  "department": "Engineering"
}
```

**Delete employee (admin only):**
```
DELETE /api/employees/{id}
```

**Search employees:**
```
GET /api/employees/search?q=John
```

## Running the Application

**From IntelliJ:**
Click the run button next to `EmsApplication.java`

**From terminal:**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

The application starts on http://localhost:8080

**Default admin credentials:**
- Username: `admin`
- Password: `admin123`

## API Documentation

Once running, visit http://localhost:8080/swagger-ui.html for interactive API documentation.

## Security

The application uses JWT-based authentication with Spring Security. Public endpoints include authentication routes and API documentation. All other endpoints require a valid JWT token.

Token expiration is set to 24 hours by default.

## Future Enhancements

Consider adding:
- File upload for employee profile images
- Audit logging using Spring AOP
- Refresh token mechanism
- Docker containerization
- CI/CD pipeline
- Cloud deployment configuration

## Troubleshooting

**Database connection issues:**
Verify MySQL is running and credentials in `application.yml` match your database setup.

**JWT token errors:**
Ensure the secret key in `application.yml` is at least 32 characters for HS256 algorithm.

**Flyway migration failures:**
Check that your migration files follow the naming convention `V{version}__{description}.sql`
