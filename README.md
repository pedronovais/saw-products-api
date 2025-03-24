# Saw Products API

This repository contains a RESTful API for managing products and categories, with Basic Auth authentication and role-based access control (admin and user). The application uses **Spring Boot**, **MongoDB**, **Spring Security**, and is prepared to run both locally and via Docker.

---

## Table of Contents

1. [Overview](#overview)
2. [Main Technologies](#main-technologies)
3. [Prerequisites](#prerequisites)
4. [Running Locally](#running-locally)
5. [Running via Docker Compose](#running-via-docker-compose)
6. [Database Configurations](#database-configurations)
7. [Tests](#tests)
8. [Project Structure](#project-structure)
9. [Main Endpoints](#main-endpoints)
10. [Contributing](#contributing)
11. [License](#license)

---

## 1. Overview

The **Saw Products API** manages:

- **Products** (full CRUD).
- **Categories** (read-only and listing).
- **Users** (no endpoints for user CRUD; they are preloaded into the database).

The API includes robust validations (e.g., required fields, size limits, email format, etc.) and **access control** (users with role `admin` can create/update/delete, while users with role `user` can only view data).

---

## 2. Main Technologies

- **Java 17 or higher**
- **Spring Boot 3.4.x**
- **Spring Data MongoDB**
- **Spring Security**
- **MongoDB**
- **Docker** and **Docker Compose** (optional, for containerization)

---

## 3. Prerequisites

- **Java 17** installed (if running locally).
- **Maven 3.9+**.
- **MongoDB** locally installed OR Docker.
- **Git**.

---

## 4. Running Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-user/your-repo.git
   cd your-repo
   ```

2. **Set up local MongoDB**
   - Make sure MongoDB is running on port `27017` (default).
   - Create (or let Spring handle) the database `saw_products_db`.

3. **Adjust the configuration file**  
   In `src/main/resources/application.properties`, keep:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/saw_products_db
   server.port=8080
   ```

4. **Run the application**
   - Via Maven:
     ```bash
     mvn clean spring-boot:run
     ```
   - Or package and run:
     ```bash
     mvn clean package
     java -jar target/saw-products-api-0.0.1-SNAPSHOT.jar
     ```

5. **Access API**: `http://localhost:8080`.

---

## 5. Running via Docker Compose

1. **Check `docker-compose.yml`**:
   ```yaml
   version: "3.8"
   services:
     mongodb:
       image: mongo:latest
       container_name: mongodb
       ports:
         - "27017:27017"
       volumes:
         - mongo-data:/data/db

     saw-products-api:
       build:
         context: .
         dockerfile: Dockerfile
       container_name: saw-products-api
       ports:
         - "8080:8080"
       environment:
         SPRING_DATA_MONGODB_URI: "mongodb://mongodb:27017/saw_products_db"
       depends_on:
         - mongodb

   volumes:
     mongo-data:
   ```

2. **Run**:
   ```bash
   docker-compose up --build
   ```

3. Access at `http://localhost:8080`.

---

## 6. Database Configurations

- **Database**: `saw_products_db`
- **Collections**:
   - `products`
   - `categories`
   - `users` (preloaded)

**Initial Seed**: Admin and user accounts preloaded.

---

## 7. Tests

Run unit tests:
```bash
mvn clean test
```

**Note**: If you're using Docker to run Mongo, tests may fail if they point to `mongodb://localhost`. Adjust accordingly to `mongodb://localhost` or `mongodb://mongodb` based on your scenario. Typically, for local unit tests, we use `mongodb://localhost`.

---

## 8. Project Structure

```
saw-products-api
 ┣ src
 ┃ ┣ main
 ┃ ┃ ┣ java
 ┃ ┃ ┃ ┗ com.testeiteam.saw_products_api
 ┃ ┃ ┃   ┣ controller
 ┃ ┃ ┃   ┣ dto
 ┃ ┃ ┃   ┣ exception
 ┃ ┃ ┃   ┣ model
 ┃ ┃ ┃   ┣ repository
 ┃ ┃ ┃   ┣ security
 ┃ ┃ ┃   ┣ service
 ┃ ┃ ┃   ┣ config
 ┃ ┃ ┃   ┗ SawProductsApiApplication.java
 ┃ ┃ ┗ resources
 ┃ ┣ test
 ┃ ┃ ┣ java
 ┃ ┃ ┃ ┗ com.testeiteam.saw_products_api
 ┃ ┃ ┃   ┣ controller
 ┃ ┃ ┃   ┣ service.impl
 ┃ ┃ ┗ resources
 ┣ Dockerfile
 ┣ docker-compose.yml
 ┣ pom.xml
 ┗ README.md
```

---

## 9. Main Endpoints

**Authentication**: Basic Auth
- `admin@domain.com` / `adminpassword`
- `user@domain.com` / `userpassword`

**Products**:
- `POST /api/products` (Admin)
- `GET /api/products/{id}`
- `PUT /api/products/{id}` (Admin)
- `DELETE /api/products/{id}` (Admin)
- `GET /api/products`

**Categories**:
- `GET /api/categories`

---

## 10. Contributing

- Fork the repo
- Create your branch
- Commit changes
- Open a Pull Request

---

## 11. License

[MIT License](https://opensource.org/licenses/MIT)

