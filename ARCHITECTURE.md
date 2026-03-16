# C4 Architectural Diagrams

**Project Title:** Campus Resource Booking System  
**Author:** PAULOSE MAJA  
**Date:** March 10, 2026  

## 1. Introduction
*(Same as SPECIFICATION.md)* Domain: University/Education. Problem: Inefficient resource booking. Scope: Feasible individual project.

## 2. C4 Model Diagrams (Mermaid)

### Level 1: System Context
```mermaid
C4Context
    title Campus Resource Booking System - Context Diagram
    
    Person(student, "Student", "Books resources")
    Person(admin, "Admin", "Approves bookings")
    
    System(bookingSystem, "Campus Resource Booking System", "Web app for resource booking")
    System_Ext(emailService, "Email Service", "Sends notifications")
    
    Rel(student, bookingSystem, "Login, Browse, Book")
    Rel(admin, bookingSystem, "Approve/Reject")
    Rel(bookingSystem, emailService, "Send notifications")
```

### Level 2: Containers
```mermaid
C4Container
    title Container Diagram
    
    Person(student, "Student")
    Person(admin, "Admin")
    
    System_Boundary(bookingSystem, "Campus Resource Booking System") {
        Container(webApp, "Web App", "React SPA", "Student/Admin UI")
        Container(backend, "Backend API", "Node.js/Express", "Business logic")
        ContainerDb(db, "Database", "PostgreSQL", "Bookings, Users, Resources")
        Container(emailService, "Notification Service", "Nodemailer", "Email sending")
    }
    
    Rel(student, webApp, "HTTPS")
    Rel(admin, webApp, "HTTPS")
    Rel(webApp, backend, "REST API")
    Rel(backend, db, "SQL")
    Rel(backend, emailService, "SMTP")
```

### Level 3: Components
```mermaid
C4Component
    title Component Diagram (Backend Focus)
    
    Container(backend, "Backend API") {
        Component(loginSvc, "Auth Service", "JWT", "Login/Logout")
        Component(resourceSvc, "Resource Service", "REST", "Browse/Availability")
        Component(bookingSvc, "Booking Service", "REST", "Create/View")
        Component(approvalSvc, "Approval Service", "REST", "Approve/Reject")
        Component(notificationComp, "Notification Component", "Internal", "Trigger emails")
    }
    
    Rel(loginSvc, db, "Verify users")
    Rel(resourceSvc, db, "Query resources")
    Rel(bookingSvc, db, "Create bookings")
    Rel(approvalSvc, db, "Update status")
    Rel(notificationComp, emailService, "Send")
```

### Level 4: End-to-End Booking Flow (Sequence)
```mermaid
sequenceDiagram
    participant S as Student
    participant WA as Web App
    participant B as Backend
    participant DB as Database
    participant E as Email
    
    S->>WA: Login
    WA->>B: Auth request
    B->>DB: Validate
    DB-->>B: OK
    B-->>WA: JWT
    
    S->>WA: Browse & Book room
    WA->>B: POST /bookings
    B->>DB: Insert pending
    B-->>WA: Success
    
    Note over Admin,B: Admin reviews
    Admin->>WA: Approve
    WA->>B: PATCH /approve
    B->>DB: Update approved
    B->>E: Notify student
    E-->>S: Email sent
```

## 3. Deployment
Docker containers for Backend/DB, hosted on Vercel/Heroku (future).

**All end-to-end components covered: UI → API → DB → Notifications.**

