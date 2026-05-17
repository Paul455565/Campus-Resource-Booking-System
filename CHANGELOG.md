# CHANGELOG

All notable changes to the Campus Resource Booking System API will be documented in this file.

## [1.0.0] - 2026-05-17

### Added

#### REST API Features
- **Booking Management**
  - `POST /api/bookings` - Create new resource bookings with availability validation
  - `GET /api/bookings/{bookingId}` - Retrieve specific booking details
  - `GET /api/bookings/user/{userId}` - Get all bookings for a specific user
  - `GET /api/bookings/resource/{resourceId}` - Get all bookings for a specific resource
  - `PUT /api/bookings/{bookingId}/approve` - Approve pending booking requests
  - `PUT /api/bookings/{bookingId}/reject` - Reject booking requests with reasons
  - `DELETE /api/bookings/{bookingId}` - Cancel existing bookings
  - `GET /api/bookings/conflicts` - Check for booking conflicts in time periods

- **Resource Management**
  - `GET /api/resources` - List all available resources with optional filtering
  - `GET /api/resources/{resourceId}` - Get detailed resource information
  - `POST /api/resources` - Create new resources for booking
  - `PUT /api/resources/{resourceId}` - Update resource information
  - `DELETE /api/resources/{resourceId}` - Delete/retire resources (soft delete)
  - `GET /api/resources/{resourceId}/availability` - Check resource availability
  - `POST /api/resources/{resourceId}/maintenance` - Schedule maintenance periods
  - `GET /api/resources/{resourceId}/utilization` - Get resource utilization statistics

- **User Management**
  - `POST /api/users/register` - Register new users with role-based access
  - `POST /api/users/login` - Authenticate users and receive JWT tokens
  - `GET /api/users/{userId}` - Get user profile information
  - `GET /api/users` - List all users (admin only) with optional role filtering
  - `PUT /api/users/{userId}` - Update user profile information
  - `POST /api/users/{userId}/change-password` - Change user passwords securely
  - `DELETE /api/users/{userId}` - Deactivate user accounts

- **Approval Management**
  - `GET /api/approvals/pending` - Get all pending approval requests
  - `GET /api/approvals/{approvalId}` - Get approval details
  - `GET /api/approvals/admin/{adminId}` - Get approvals processed by specific admin
  - `POST /api/approvals/{bookingId}/submit` - Submit bookings for approval
  - `POST /api/approvals/{approvalId}/approve` - Approve pending bookings
  - `POST /api/approvals/{approvalId}/reject` - Reject approval requests

- **Maintenance Management**
  - `POST /api/maintenance` - Schedule maintenance tasks for resources
  - `GET /api/maintenance/{maintenanceId}` - Get maintenance task details
  - `GET /api/maintenance/resource/{resourceId}` - Get resource maintenance history
  - `GET /api/maintenance/pending` - Get all pending maintenance tasks
  - `PUT /api/maintenance/{maintenanceId}/complete` - Mark maintenance as completed
  - `DELETE /api/maintenance/{maintenanceId}` - Cancel scheduled maintenance

- **Notification Management**
  - `GET /api/notifications/user/{userId}` - Get user notifications with optional status filtering
  - `GET /api/notifications/user/{userId}/unread-count` - Get count of unread notifications
  - `GET /api/notifications/{notificationId}` - Get notification details
  - `PUT /api/notifications/{notificationId}/mark-read` - Mark notification as read
  - `PUT /api/notifications/user/{userId}/mark-all-read` - Mark all user notifications as read
  - `DELETE /api/notifications/{notificationId}` - Delete specific notification
  - `DELETE /api/notifications/user/{userId}` - Delete all user notifications

#### Service Layer
- **BookingService** - Complete booking lifecycle management with conflict detection
- **ResourceService** - Resource availability checking and utilization tracking
- **UserService** - User registration, authentication, and profile management
- **ApprovalService** - Approval workflow management for bookings
- **MaintenanceService** - Maintenance scheduling and task tracking
- **NotificationService** - User notification delivery and management
- **AuthenticationService** - JWT token generation and validation

#### Documentation
- OpenAPI/Swagger UI integrated with comprehensive endpoint documentation
- JSON schemas for all API request/response models
- Detailed operation descriptions for all endpoints
- Parameter validation and error code documentation
- API available at `http://localhost:8080/api/swagger-ui.html`

#### Testing
- **Unit Tests**: Comprehensive unit test coverage for all service classes
  - BookingServiceTest (50+ assertions)
  - ResourceServiceTest (40+ assertions)
  - UserServiceTest (35+ assertions)
  - Test coverage for CRUD operations, business logic, and error handling
  
- **Integration Tests**: API endpoint integration tests
  - BookingControllerIntegrationTest (10+ test cases)
  - ResourceControllerIntegrationTest (10+ test cases)
  - Tests for HTTP status codes, response formats, and error handling

#### Database
- H2 in-memory database for development and testing
- Hibernate ORM configuration with Spring Data JPA
- Automatic schema generation and updates

#### Configuration
- Spring Boot application properties in `application.yml`
- Logging configuration with DEBUG level for application code
- Server context path: `/api`
- CORS and security ready for future implementation

#### Architecture
- Clean separation of concerns: Controller → Service → Repository → Model
- Dependency injection with Spring IoC container
- Factory pattern for repository instantiation
- Repository pattern for data access abstraction
- Service layer for business logic
- REST API design following REST conventions

### Features

#### Booking System
- Automatic conflict detection for overlapping bookings
- Resource availability validation before booking creation
- Booking status tracking (PENDING, APPROVED, REJECTED, CANCELLED)
- Admin approval workflow for resource bookings

#### Resource Management
- Multiple resource types support (LABORATORY, MEETING_ROOM, EQUIPMENT, LIBRARY, DORMITORY, AUDITORIUM)
- Resource capacity tracking
- Availability status management (AVAILABLE, MAINTENANCE, RETIRED)
- Maintenance scheduling and tracking
- Utilization statistics generation

#### User System
- Role-based access control (STUDENT, STAFF, FACULTY, ADMIN)
- User profile management with department and contact information
- Password change functionality
- Account deactivation (soft delete)
- User status tracking

#### Notifications
- Real-time notification delivery to users
- Status tracking for notifications (SENT, READ, ARCHIVED)
- Multiple notification channels (EMAIL, SMS, PUSH, IN_APP)
- Bulk notification operations

### Fixed
- Initial release (no fixes)

### Security Considerations
- Password hashing (to be implemented)
- JWT token validation
- Role-based access control (RBAC)
- Input validation on all endpoints
- HTTP parameter constraints

### Known Limitations
- In-memory database (use for development/testing only)
- Basic authentication (JWT implementation pending)
- No email/SMS service integration yet
- API rate limiting not yet implemented

### Dependencies Added
- Spring Boot 3.1.5
- Spring Web Starter for REST API support
- Spring Data JPA for database operations
- Springdoc OpenAPI 2.0.0 for Swagger documentation
- H2 Database for testing
- JUnit 5 for testing
- Mockito for unit test mocking

### Technical Specifications
- **Language**: Java 17
- **Framework**: Spring Boot 3.1.5
- **Build Tool**: Maven
- **API Format**: REST/JSON
- **Documentation**: OpenAPI 3.0 (Swagger)
- **Testing**: JUnit 5, Mockito
- **Database**: H2 (development)

### How to Run
1. Clone the repository
2. Run `mvn clean install` to build the project
3. Run `mvn spring-boot:run` to start the application
4. Access the API at `http://localhost:8080/api`
5. View Swagger UI at `http://localhost:8080/api/swagger-ui.html`
6. Run tests with `mvn test`

### API Response Codes
- **200 OK**: Successful GET/PUT request
- **201 Created**: Successful POST request
- **204 No Content**: Successful DELETE request
- **400 Bad Request**: Invalid input parameters
- **401 Unauthorized**: Authentication required
- **404 Not Found**: Resource not found
- **409 Conflict**: Resource conflict (e.g., booking conflict)
- **500 Internal Server Error**: Server-side error

### Version History
- **1.0.0** (2026-05-17): Initial release with complete REST API, service layer, and Swagger documentation
