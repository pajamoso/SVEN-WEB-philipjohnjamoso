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

Java 17 JDK is included in the source code