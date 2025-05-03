## ✅ `backend/README.md`

```markdown
# ⚙️ Backend - Simba Pet Services

This is the Spring Boot 3.2 API backend that receives encrypted appointment data.

## ▶️ Run Locally

### With Maven Wrapper:

```bash
./mvnw spring-boot:run

Or via IDE:
Run SimbaApplication.java

App will run at: http://localhost:8080

🔧 Config
In src/main/resources/application.properties:
spring.datasource.url=jdbc:h2:mem:testdb;
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

Or use H2 for demo/testing.
You can view the H2 database by navigating to http://localhost:8080/h2-console. Set the JDBC URL to jdbc:h2:mem:testdb and use sa as the username and password for the password.

🔐 CORS & Auth
Accepts CORS from http://localhost:4200

JWT token is expected in Authorization header

Token is currently hardcoded in Angular for demo

📥 Endpoint
POST /api/form/submit
Receives encrypted payload:
{
  "salt": [16 random bytes],
  "data": "encrypted-hex-string"
}

Java 17 JDK link : https://download.java.net/java/GA/jdk17.0.1/2a2082e5a09d4267845be086888add4f/12/GPL/openjdk-17.0.1_windows-x64_bin.zip