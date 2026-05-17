# REST API Implementation Guide

## Campus Resource Booking System REST API

This document provides comprehensive information about the REST API endpoints for the Campus Resource Booking System.

## Base URL

```
http://localhost:8080/api
```

## Documentation

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/v3/api-docs

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Build and Run

```bash
# Clone the repository
git clone https://github.com/yourusername/Campus-Resource-Booking-System.git
cd Campus-Resource-Booking-System

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Run tests
mvn test

# Generate test coverage report
mvn test jacoco:report
```

The API will be available at `http://localhost:8080/api`

## API Endpoints Overview

### 1. Booking Management (`/api/bookings`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/bookings` | Create new booking | Yes |
| GET | `/bookings/{bookingId}` | Get booking details | Yes |
| GET | `/bookings/user/{userId}` | Get user's bookings | Yes |
| GET | `/bookings/resource/{resourceId}` | Get resource bookings | Yes |
| PUT | `/bookings/{bookingId}/approve` | Approve booking | Admin |
| PUT | `/bookings/{bookingId}/reject` | Reject booking | Admin |
| DELETE | `/bookings/{bookingId}` | Cancel booking | Yes |
| GET | `/bookings/conflicts` | Check conflicts | Yes |

### 2. Resource Management (`/api/resources`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/resources` | List all resources | No |
| POST | `/resources` | Create resource | Admin |
| GET | `/resources/{resourceId}` | Get resource details | No |
| PUT | `/resources/{resourceId}` | Update resource | Admin |
| DELETE | `/resources/{resourceId}` | Delete resource | Admin |
| GET | `/resources/{resourceId}/availability` | Check availability | No |
| POST | `/resources/{resourceId}/maintenance` | Schedule maintenance | Admin |
| GET | `/resources/{resourceId}/utilization` | Get utilization stats | Admin |

### 3. User Management (`/api/users`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/users/register` | Register user | No |
| POST | `/users/login` | Login | No |
| GET | `/users/{userId}` | Get user details | Yes |
| GET | `/users` | List all users | Admin |
| PUT | `/users/{userId}` | Update profile | Yes |
| POST | `/users/{userId}/change-password` | Change password | Yes |
| DELETE | `/users/{userId}` | Deactivate account | Yes |

### 4. Approval Management (`/api/approvals`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/approvals/pending` | Get pending approvals | Admin |
| GET | `/approvals/{approvalId}` | Get approval details | Admin |
| GET | `/approvals/admin/{adminId}` | Get admin's approvals | Admin |
| POST | `/approvals/{bookingId}/submit` | Submit for approval | Yes |
| POST | `/approvals/{approvalId}/approve` | Approve booking | Admin |
| POST | `/approvals/{approvalId}/reject` | Reject booking | Admin |

### 5. Maintenance Management (`/api/maintenance`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/maintenance` | Schedule maintenance | Admin |
| GET | `/maintenance/{maintenanceId}` | Get maintenance details | Admin |
| GET | `/maintenance/resource/{resourceId}` | Get resource maintenance | Admin |
| GET | `/maintenance/pending` | Get pending maintenance | Admin |
| PUT | `/maintenance/{maintenanceId}/complete` | Complete maintenance | Admin |
| DELETE | `/maintenance/{maintenanceId}` | Cancel maintenance | Admin |

### 6. Notification Management (`/api/notifications`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/notifications/user/{userId}` | Get user notifications | Yes |
| GET | `/notifications/user/{userId}/unread-count` | Get unread count | Yes |
| GET | `/notifications/{notificationId}` | Get notification details | Yes |
| PUT | `/notifications/{notificationId}/mark-read` | Mark as read | Yes |
| PUT | `/notifications/user/{userId}/mark-all-read` | Mark all as read | Yes |
| DELETE | `/notifications/{notificationId}` | Delete notification | Yes |
| DELETE | `/notifications/user/{userId}` | Delete all notifications | Yes |

## Request/Response Examples

### Example 1: Create a Booking

**Request:**
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "resourceId": "resource456",
    "startDate": "2026-05-20T10:00:00",
    "endDate": "2026-05-20T12:00:00",
    "purpose": "Project meeting"
  }'
```

**Response (201 Created):**
```json
{
  "bookingId": "booking789",
  "userId": "user123",
  "resourceId": "resource456",
  "startDate": "2026-05-20T10:00:00",
  "endDate": "2026-05-20T12:00:00",
  "status": "PENDING",
  "purpose": "Project meeting",
  "createdAt": "2026-05-17T14:30:00",
  "updatedAt": "2026-05-17T14:30:00"
}
```

### Example 2: List Resources

**Request:**
```bash
curl -X GET http://localhost:8080/api/resources
```

**Response (200 OK):**
```json
[
  {
    "resourceId": "resource1",
    "name": "Lab A",
    "description": "Computer Lab",
    "type": "LABORATORY",
    "location": "Building A",
    "capacity": 30,
    "availability": "AVAILABLE",
    "createdAt": "2026-05-17T10:00:00",
    "updatedAt": "2026-05-17T10:00:00"
  }
]
```

### Example 3: Register User

**Request:**
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "student@campus.edu",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe",
    "role": "STUDENT"
  }'
```

**Response (201 Created):**
```json
{
  "userId": "user123",
  "email": "student@campus.edu",
  "firstName": "John",
  "lastName": "Doe",
  "role": "STUDENT",
  "status": "ACTIVE",
  "createdAt": "2026-05-17T14:35:00",
  "updatedAt": "2026-05-17T14:35:00"
}
```

## HTTP Status Codes

| Code | Meaning | Use Cases |
|------|---------|-----------|
| 200 | OK | Successful GET, PUT, PATCH requests |
| 201 | Created | Successful POST requests |
| 204 | No Content | Successful DELETE requests |
| 400 | Bad Request | Invalid input, validation errors |
| 401 | Unauthorized | Authentication required/failed |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource not found |
| 409 | Conflict | Booking conflict, duplicate resource |
| 500 | Internal Server Error | Server error |

## Enum Values

### BookingStatus
- PENDING
- APPROVED
- REJECTED
- CANCELLED
- NO_SHOW
- COMPLETED

### ResourceType
- LABORATORY
- MEETING_ROOM
- EQUIPMENT
- LIBRARY
- DORMITORY
- AUDITORIUM

### ResourceAvailability
- AVAILABLE
- MAINTENANCE
- RETIRED

### UserRole
- STUDENT
- STAFF
- FACULTY
- ADMIN

### UserStatus
- ACTIVE
- INACTIVE
- SUSPENDED

### NotificationStatus
- SENT
- READ
- ARCHIVED
- FAILED

### NotificationType
- CONFIRMATION
- APPROVAL
- REJECTION
- REMINDER
- MAINTENANCE

### NotificationChannel
- EMAIL
- SMS
- PUSH
- IN_APP

## Error Response Format

```json
{
  "timestamp": "2026-05-17T14:35:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid booking request",
  "path": "/api/bookings"
}
```

## Testing

The project includes comprehensive test coverage:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=BookingServiceTest

# Run with coverage report
mvn clean test jacoco:report
# Report available at: target/site/jacoco/index.html
```

### Test Files
- `src/test/java/com/campus/resourcebooking/service/` - Unit tests for services
- `src/test/java/com/campus/resourcebooking/controller/` - Integration tests for API endpoints

## Project Structure

```
Campus-Resource-Booking-System/
├── src/
│   ├── main/
│   │   ├── java/com/campus/resourcebooking/
│   │   │   ├── CampusResourceBookingApplication.java
│   │   │   ├── controller/              # REST controllers
│   │   │   ├── service/                 # Business logic
│   │   │   ├── model/                   # Domain models
│   │   │   ├── repositories/            # Data access
│   │   │   ├── enums/                   # Enumerations
│   │   │   ├── factories/               # Factory pattern
│   │   │   └── config/                  # Spring configuration
│   │   └── resources/
│   │       └── application.yml          # Application properties
│   └── test/
│       ├── java/com/campus/resourcebooking/
│       │   ├── service/                 # Service unit tests
│       │   └── controller/              # Controller integration tests
│       └── resources/
│           └── application-test.yml     # Test properties
├── pom.xml                              # Maven build configuration
├── CHANGELOG.md                         # Version history
└── API_DOCUMENTATION.md                 # This file
```

## Database

The application uses H2 in-memory database for development/testing:
- **Console URL**: http://localhost:8080/api/h2-console
- **JDBC URL**: `jdbc:h2:mem:resourcedb`
- **Username**: `sa`
- **Password**: (empty)

## Performance Considerations

- Booking conflict detection is O(n) - consider indexing for production
- Consider caching resource availability checks
- Implement pagination for list endpoints
- Add rate limiting to prevent abuse
- Use connection pooling for database operations

## Security Notes

⚠️ **Current Implementation**:
- Basic JWT token generation (simplified)
- Password hashing recommended
- CORS configuration included
- Role-based access control ready

**For Production**:
- Implement proper JWT token validation
- Hash passwords using BCrypt
- Add HTTPS/TLS enforcement
- Implement OAuth2/OpenID Connect
- Add API rate limiting
- Use environment variables for secrets

## Future Enhancements

1. Database persistence (PostgreSQL/MySQL)
2. Advanced search and filtering
3. Export to PDF/Excel
4. Email notifications integration
5. Calendar view for bookings
6. Mobile app API support
7. Analytics and reporting dashboard
8. Multi-tenant support
9. Audit logging
10. WebSocket support for real-time updates

## Support

For issues or questions:
- Check the Swagger documentation at `/api/swagger-ui.html`
- Review test files for usage examples
- Check CHANGELOG.md for recent updates
- Contact: support@campus.edu

---

**Last Updated**: May 17, 2026
**Version**: 1.0.0
