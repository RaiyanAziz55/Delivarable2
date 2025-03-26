# 🏨 Hotel Booking Backend – Setup Guide

This project is a Java Spring Boot backend for a hotel booking application. It supports hotel chains, rooms, bookings, rentings, employee login, and more.

---

## 📦 Prerequisites

Make sure you have the following installed:

- **Java 17 or higher**
- **Maven** (optional: Maven wrapper is included)
- **PostgreSQL**
- **Git** (to clone the project)

---

## 📁 Project Structure

```
Backend/
├── demo/
│   ├── src/
│   │   ├── main/          # Java source files
│   │   └── test/          # Unit and integration tests
│   ├── .mvn/              # Maven wrapper settings
│   ├── pom.xml            # Maven dependencies file
│   ├── mvnw / mvnw.cmd    # Maven wrapper scripts
│   └── application.properties (inside resources/)
```

---

## ⚙️ Configuration

Set up your PostgreSQL connection in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Hotel_DB_V2 or (DB_NAME)
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Replace `your_password` with your actual PostgreSQL password.

---

## 🪰 Installing Dependencies

Navigate to the project root directory:

### Option 1: Using Local Maven

```bash
cd Backend/demo
mvn clean install
```

### Option 2: Using Maven Wrapper (No installation required)

```bash
cd Backend/demo
./mvnw clean install
```

---

## ▶️ Running the Application

Use Maven to start the Spring Boot server:

```bash
./mvnw spring-boot:run
```

Or run `DemoApplication.java` directly from your IDE (e.g., IntelliJ, VSCode).

---

## 🌐 Access the Backend

Once started, the application will be running at:

```
http://localhost:8080
```

You can now make API calls (e.g., login, search rooms, create bookings, etc.).

---

## 🛠️ Next Steps

- [ ] Test login with `/employees/login`
- [ ] Search rooms with `/rooms/search`
- [ ] View availability with `/rooms/available`
- [ ] View and manage bookings/rentings
- [ ] Use Postman or curl for testing

---

## 📌 Notes

- Make sure PostgreSQL is running and the database `Hotel_DB_V2` exists.
- If you don’t have `pg_dump` or `psql`, you may need to install PostgreSQL CLI tools.

Use this link to backup database: https://www.google.com/search?q=how+to+open+a+sql+file+in+postgresql&oq=how+to+open+a+.sql+&gs_lcrp=EgZjaHJvbWUqCAgFEAAYFhgeMgcIABAAGIAEMgYIARBFGDkyBwgCEAAYgAQyCAgDEAAYFhgeMggIBBAAGBYYHjIICAUQABgWGB4yCAgGEAAYFhgeMggIBxAAGBYYHjIICAgQABgWGB4yCAgJEAAYFhge0gEIODgyNmowajeoAgCwAgA&sourceid=chrome&ie=UTF-8#fpstate=ive&vld=cid:45479709,vid:3AKIA8pu8YY,st:0
---
