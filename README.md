# Student Management (Core Java → Maven → JDBC → Hibernate → Spring Core)

This project consolidates everything we've covered so far:
- Core Java (OOP, exceptions, collections, streams)
- Multithreading (async save)
- Maven
- JDBC (simple connectivity check)
- Hibernate (ORM for CRUD)
- Spring Core (IoC & DI for wiring Service/DAO)

## Prerequisites
- JDK 17+
- Maven 3.8+
- MySQL running locally

Create the DB once:
```sql
CREATE DATABASE studentdb;
```

Update DB credentials in: `src/main/resources/hibernate.cfg.xml`.

## Build & Run
```bash
mvn -q -DskipTests package
mvn -q exec:java
```
or run `MainApp` from your IDE.

## Features
- Add students (sync/async)
- List, sort, filter (Streams API)
- Delete by ID
- Hibernate auto-creates/updates the `Student` table
