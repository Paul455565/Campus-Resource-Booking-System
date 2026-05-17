# Build & Deployment Guide

## Building the Application

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6.0 or higher
- Git

### Build Steps

1. **Clone the Repository**
```bash
git clone https://github.com/yourusername/Campus-Resource-Booking-System.git
cd Campus-Resource-Booking-System
```

2. **Build the Project**
```bash
# Build with Maven
mvn clean install

# Build with skip tests (for faster builds)
mvn clean install -DskipTests
```

3. **Run Tests**
```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BookingServiceTest

# Generate test coverage report
mvn clean test jacoco:report
# Coverage report available at: target/site/jacoco/index.html
```

4. **Package the Application**
```bash
# Create JAR file
mvn clean package

# JAR file location: target/resource-booking-system-1.0.0.jar
```

## Running the Application

### Development (Spring Boot Run)
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080/api`

### Production (JAR Execution)
```bash
java -jar target/resource-booking-system-1.0.0.jar
```

### With Custom Port
```bash
java -jar target/resource-booking-system-1.0.0.jar --server.port=9090
```

### With Custom Configuration
```bash
java -jar target/resource-booking-system-1.0.0.jar \
  --spring.datasource.url=jdbc:mysql://localhost:3306/resourcedb \
  --spring.datasource.username=root \
  --spring.datasource.password=password
```

## Docker Deployment

### Build Docker Image

Create `Dockerfile`:
```dockerfile
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/resource-booking-system-1.0.0.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build the image:
```bash
docker build -t campus-resource-booking:1.0.0 .
```

Run the container:
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/resourcedb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=password \
  campus-resource-booking:1.0.0
```

### Docker Compose

Create `docker-compose.yml`:
```yaml
version: '3.8'

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/resourcedb
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: password
      SPRING_JPA_HIBERNATE_DDL_AUTO: update
    depends_on:
      - db

  db:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: password
      MYSQL_DATABASE: resourcedb
    ports:
      - "3306:3306"
    volumes:
      - db_data:/var/lib/mysql

volumes:
  db_data:
```

Run with Docker Compose:
```bash
docker-compose up
```

## Kubernetes Deployment

Create `k8s-deployment.yaml`:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: campus-resource-booking
spec:
  replicas: 3
  selector:
    matchLabels:
      app: campus-resource-booking
  template:
    metadata:
      labels:
        app: campus-resource-booking
    spec:
      containers:
      - name: app
        image: campus-resource-booking:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: app-config
              key: datasource-url
        - name: SPRING_DATASOURCE_USERNAME
          valueFrom:
            secretKeyRef:
              name: app-secrets
              key: db-username
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: app-secrets
              key: db-password
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"

---
apiVersion: v1
kind: Service
metadata:
  name: campus-resource-booking-service
spec:
  selector:
    app: campus-resource-booking
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  type: LoadBalancer
```

Deploy to Kubernetes:
```bash
kubectl apply -f k8s-deployment.yaml
```

## Configuration

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `SERVER_PORT` | 8080 | Application port |
| `SPRING_DATASOURCE_URL` | jdbc:h2:mem:resourcedb | Database URL |
| `SPRING_DATASOURCE_USERNAME` | sa | Database username |
| `SPRING_DATASOURCE_PASSWORD` | (empty) | Database password |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | update | Hibernate DDL auto strategy |
| `LOGGING_LEVEL_ROOT` | INFO | Root logging level |

### Application Properties

Edit `src/main/resources/application.yml`:
```yaml
spring:
  application:
    name: Campus Resource Booking System
  datasource:
    url: jdbc:mysql://localhost:3306/resourcedb
    username: root
    password: password
  jpa:
    hibernate:
      ddl-auto: update

server:
  port: 8080
```

## Monitoring

### Health Check
```bash
curl http://localhost:8080/api/actuator/health
```

### Metrics
```bash
curl http://localhost:8080/api/actuator/metrics
```

### Application Info
```bash
curl http://localhost:8080/api/actuator/info
```

Enable actuator endpoints in `application.yml`:
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,info
```

## Performance Tuning

### JVM Parameters
```bash
java -Xmx2g -Xms1g \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -jar target/resource-booking-system-1.0.0.jar
```

### Connection Pool Configuration
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
```

## Logging Configuration

### Enable Debug Logging
```bash
java -jar target/resource-booking-system-1.0.0.jar \
  --logging.level.com.campus.resourcebooking=DEBUG
```

### Log to File
```yaml
logging:
  file:
    name: logs/application.log
  level:
    root: INFO
    com.campus.resourcebooking: DEBUG
```

## Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn test -Dtest=*IntegrationTest
```

### Load Testing with Apache JMeter
```bash
jmeter -n -t load-test-plan.jmx -l results.jtl -j jmeter.log
```

## Troubleshooting

### Port Already in Use
```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### OutOfMemoryError
Increase JVM heap size:
```bash
java -Xmx4g -Xms2g -jar target/resource-booking-system-1.0.0.jar
```

### Database Connection Issues
- Verify database is running
- Check connection string in `application.yml`
- Verify database credentials
- Check firewall rules

### CORS Issues
Update `src/main/java/com/campus/resourcebooking/config/CorsConfig.java`:
```java
registry.addMapping("/api/**")
    .allowedOrigins("http://localhost:3000", "http://yourdomain.com")
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
```

## CI/CD Pipeline

### GitHub Actions Example

Create `.github/workflows/build.yml`:
```yaml
name: Build and Test

on: [push, pull_request]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Build with Maven
        run: mvn clean install
      - name: Run tests
        run: mvn test
```

## Backup and Restoration

### Database Backup
```bash
# MySQL
mysqldump -u root -p resourcedb > backup.sql

# PostgreSQL
pg_dump -U postgres resourcedb > backup.sql
```

### Database Restoration
```bash
# MySQL
mysql -u root -p resourcedb < backup.sql

# PostgreSQL
psql -U postgres resourcedb < backup.sql
```

---

**Version**: 1.0.0
**Last Updated**: May 17, 2026
