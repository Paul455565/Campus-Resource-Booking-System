# Use Case Diagram

**Campus Resource Booking System**  
**Assignment 5 - Use Case Modeling**  
**Author:** PAULOSE MAJA  
**Date:** March 30, 2026  

## Use Case Diagram

```mermaid
usecaseDiagram
    actor Student
    actor Faculty
    actor ResourceAdministrator
    actor UniversityAdministrator
    actor MaintenanceStaff
    actor ITSupportStaff
    actor SecurityAuditor

    usecase UC1 as "Login"
    usecase UC2 as "Browse Resources"
    usecase UC3 as "View Calendar"
    usecase UC4 as "Submit Booking"
    usecase UC5 as "View Dashboard"
    usecase UC6 as "Approve/Reject Booking"
    usecase UC7 as "Manage Resources"
    usecase UC8 as "View Profile"
    usecase UC9 as "Generate Reports"
    usecase UC10 as "Schedule Maintenance"
    usecase UC11 as "Search Bookings"
    usecase UC12 as "View Audit Logs"

    Student --> UC1
    Student --> UC2
    Student --> UC3
    Student --> UC4
    Student --> UC8

    Faculty --> UC1
    Faculty --> UC2
    Faculty --> UC3
    Faculty --> UC4
    Faculty --> UC8

    ResourceAdministrator --> UC1
    ResourceAdministrator --> UC5
    ResourceAdministrator --> UC6
    ResourceAdministrator --> UC7
    ResourceAdministrator --> UC10

    UniversityAdministrator --> UC1
    UniversityAdministrator --> UC5
    UniversityAdministrator --> UC9
    UniversityAdministrator --> UC11

    MaintenanceStaff --> UC1
    MaintenanceStaff --> UC10

    ITSupportStaff --> UC1
    ITSupportStaff --> UC11
    ITSupportStaff --> UC12

    SecurityAuditor --> UC1
    SecurityAuditor --> UC12

    UC4 ..> UC2 : <<include>>
    UC4 ..> UC3 : <<include>>
    UC6 ..> UC5 : <<include>>
    UC9 ..> UC11 : <<include>>
    UC12 ..> UC11 : <<include>>
```

## Explanation

### Key Actors and Their Roles
- **Student**: Primary user who books resources for study or personal use. Interacts with login, browsing, calendar view, booking submission, and profile management.
- **Faculty**: Similar to students but may have priority or additional permissions for class-related bookings. Uses the same core booking features.
- **Resource Administrator**: Manages daily operations, approves/rejects bookings, manages resources, and schedules maintenance.
- **University Administrator**: Oversees high-level operations, generates reports, and searches bookings for analytics.
- **Maintenance Staff**: Schedules resource maintenance to mark downtime.
- **IT Support Staff**: Handles technical aspects like searching bookings and viewing audit logs for support.
- **Security Auditor**: Ensures compliance by viewing audit logs.

### Relationships Between Actors and Use Cases
- **Generalization**: Not explicitly shown, but Faculty and Student share similar use cases, implying a potential "User" generalization, but kept separate for clarity.
- **Inclusion**: 
  - Submit Booking includes Browse Resources and View Calendar (to check availability before booking).
  - Approve/Reject Booking includes View Dashboard (to review pending bookings).
  - Generate Reports includes Search Bookings (to filter data for reports).
  - View Audit Logs includes Search Bookings (to query logs).
- **Extension**: Not used, but could be for optional flows like notifications.

### How the Diagram Addresses Stakeholder Concerns from Assignment 4
- **Students/Faculty**: Supports easy access to availability (Browse Resources, View Calendar), quick booking (Submit Booking), and profile management (View Profile), addressing pain points like double-bookings and visibility.
- **Resource Administrators**: Streamlines approval workflow (Approve/Reject Booking), dashboards (View Dashboard), and resource management (Manage Resources), reducing administrative overload.
- **University Administrators**: Enables usage analytics (Generate Reports) and fair allocation oversight (Search Bookings).
- **Maintenance Staff**: Simple interface to mark downtime (Schedule Maintenance), avoiding invalid bookings.
- **IT Support/Security Auditors**: Provides monitoring and compliance tools (Search Bookings, View Audit Logs), ensuring reliability and security.
- Overall, the diagram ensures balanced priorities, with traceability to FR1-FR12 in SYSTEM_REQUIREMENTS.md.</content>
<parameter name="filePath">c:\Users\tania\OneDrive\Desktop\Campus Resource Booking System\USE_CASE_DIAGRAM.md