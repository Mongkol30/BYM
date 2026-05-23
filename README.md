# BeeryYummyMap Backend

Spring Boot REST API 

## Tech Stack

- Java 25
- Spring Boot
- PostgreSQL
- Docker

## Prerequisites

- Java 25
- Maven
- PostgreSQL

## Getting Started

### 1. Clone repository

```bash
git clone https://github.com/Mongkol30/BYM.git
cd BYM
```

### 2. ตั้งค่า Database

สร้าง database ใน PostgreSQL local:

```sql
CREATE DATABASE bymV1;
CREATE USER bymuser WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE bymV1 TO bymuser;
```

### 3. ตั้งค่า Config

Copy ไฟล์ example แล้วแก้ค่าให้ตรงกับเครื่องตัวเอง:

```bash
cp src/main/resources/application-dev.yml.example src/main/resources/application-dev.yml
```

แก้ไข `application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bymV1
    username: your_username
    password: your_password
```

### 4. Run

```bash
mvn spring-boot:run
```

API จะรันที่ `http://localhost:8080`

## Project Structure

```
├── .github/workflows
│     └── backend.yml # script CI/CD
├── src/
│    └── main/
│        ├── java/com/beeryyummymap/
│        │   ├── config/          # Configuration 
│        │   ├── controller/      # REST Controllers
│        │   ├── service/         # Business Logic
│        │   ├── repository/      # Database Access
│        │   └── entity/          # JPA Entities
│        └── resources/
│            ├── application.yaml           # Main config
│            ├── application-dev.yml        # Local config 
│            ├── application-dev.yml.example # Template สำหรับ dev
│            └── application-prod.yml       # Production config
├── Dockerfile # For build
└── pom.xml
```

## Environment Variables (Production)

ตั้งค่าใน Render Dashboard:

| Variable | Description |
|---|---|
| `SPRING_DATASOURCE_URL` | Supabase JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Supabase username |
| `SPRING_DATASOURCE_PASSWORD` | Supabase password |
| `SPRING_PROFILES_ACTIVE` | ต้องเป็น `prod` |

## CI/CD

- **CI**: GitHub Actions — compile check ทุก push
- **CD**: GitHub Actions → trigger Render deploy hook ทุก push to main
- **Platform**: Render (Docker)

