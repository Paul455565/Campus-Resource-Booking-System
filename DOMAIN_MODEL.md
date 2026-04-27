# Domain Model - Campus Resource Booking System

**Assignment 9 - Domain Modeling and Class Diagram Development**  
**Author:** PAULOSE MAJA  
**Date:** April 27, 2026

## 1. Overview

The Campus Resource Booking System operates within the University/Education domain, where physical resources (study rooms, projectors, lab computers) must be efficiently allocated among students and faculty. The domain model identifies 6 core entities that represent the key business concepts, their relationships, and operational constraints.

---

## 2. Core Domain Entities

### 2.1 Entity Breakdown

| Entity | Purpose | Cardinality |
|--------|---------|-------------|
| **User** | Represents system actors (students, faculty, admins, maintenance staff) | 1..* |
| **Resource** | Physical assets available for booking (rooms, equipment) | 1..* |
| **Booking** | Request to use a resource at a specific time | 1..* |
| **Approval** | Admin decision on a booking request | 0..1 per booking |
| **Notification** | Communication sent to users (email/SMS) | 1..* |
| **Maintenance** | Scheduled downtime for resource repairs/updates | 0..* per resource |

---

## 3. Detailed Entity Definitions

### 3.1 User Entity

**Description:** Represents all system users with role-based access control (RBAC).

| Attribute | Type | Constraints |
|-----------|------|-------------|
| `userId` | String (UUID) | Primary Key, Unique, Not Null |
| `email` | String | Unique, Not Null, Valid email format |
| `password` | String (hashed) | Not Null, Min 8 chars, bcrypt encrypted |
| `firstName` | String | Not Null, Max 50 chars |
| `lastName` | String | Not Null, Max 50 chars |
| `role` | Enum: STUDENT, FACULTY, ADMIN, MAINTENANCE, AUDITOR | Not Null |
| `department` | String | Max 100 chars |
| `phone` | String | Valid format, optional |
| `status` | Enum: ACTIVE, INACTIVE, SUSPENDED | Default: ACTIVE |
| `createdAt` | DateTime | Timestamp of account creation |
| `updatedAt` | DateTime | Timestamp of last update |

**Methods/Behaviors:**
- `register()` - Create new account with email verification
- `login()` - Authenticate with credentials, generate JWT token
- `updateProfile()` - Edit personal information
- `changePassword()` - Update password with validation
- `logout()` - Invalidate session
- `suspendAccount()` - Disable account (admin action)
- `getBookingHistory()` - Retrieve all bookings for user

**Relationships:**
- **1..*** - Bookings created by user
- **1..**** - Notifications received by user
- **RBAC:** Admin users manage resources and approvals

**Business Rules:**
- BR1: Users must verify email before booking
- BR2: Account lockout after 5 failed login attempts
- BR3: Password expires every 90 days (admin users)
- BR4: Users can cancel own pending bookings anytime

---

### 3.2 Resource Entity

**Description:** Physical assets (study rooms, projectors, lab computers) available for booking.

| Attribute | Type | Constraints |
|-----------|------|-------------|
| `resourceId` | String (UUID) | Primary Key, Unique, Not Null |
| `name` | String | Not Null, Max 100 chars |
| `description` | String | Optional, Max 500 chars |
| `type` | Enum: ROOM, PROJECTOR, COMPUTER_LAB, EQUIPMENT | Not Null |
| `location` | String | Not Null, Max 200 chars (building, floor, room number) |
| `capacity` | Integer | Not Null, Min 1 |
| `availability` | Enum: AVAILABLE, BOOKED, MAINTENANCE, RETIRED | Default: AVAILABLE |
| `maintenanceSchedule` | DateTime[] | Array of scheduled maintenance periods |
| `createdAt` | DateTime | Timestamp of resource addition |
| `updatedAt` | DateTime | Timestamp of last modification |

**Methods/Behaviors:**
- `addResource()` - Create new resource inventory entry
- `editResource()` - Modify resource details
- `deleteResource()` - Remove resource (soft delete if bookings exist)
- `checkAvailability(date, time, duration)` - Verify time slot is free
- `markMaintenance(startDate, endDate)` - Schedule downtime
- `getBookingHistory()` - List all past/future bookings
- `getUtilizationRate()` - Calculate usage percentage

**Relationships:**
- **1..**** - Multiple bookings per resource
- **0..**** - Multiple maintenance windows per resource
- **Managed by:** Admin users

**Business Rules:**
- BR5: A resource cannot have overlapping maintenance periods
- BR6: Bookings cannot be created during maintenance windows
- BR7: Study rooms capacity: 1-100; Lab rooms: 10-50; Equipment: 1-5
- BR8: Resource availability status updates automatically based on bookings

---

### 3.3 Booking Entity

**Description:** A request to reserve a resource for a specific date, time, and duration.

| Attribute | Type | Constraints |
|-----------|------|-------------|
| `bookingId` | String (UUID) | Primary Key, Unique, Not Null |
| `userId` | String (FK) | Not Null, References User |
| `resourceId` | String (FK) | Not Null, References Resource |
| `startDate` | DateTime | Not Null, Must be future date |
| `endDate` | DateTime | Not Null, Must be >= startDate |
| `status` | Enum: PENDING, APPROVED, REJECTED, ACTIVE, COMPLETED, CANCELLED, NO_SHOW | Default: PENDING |
| `purpose` | String | Not Null, Max 300 chars (description of intended use) |
| `notes` | String | Optional, Max 500 chars |
| `createdAt` | DateTime | Timestamp of booking submission |
| `updatedAt` | DateTime | Timestamp of last status change |

**Methods/Behaviors:**
- `submitBooking()` - Create booking request with conflict check
- `cancelBooking()` - User cancels pending/approved booking (before start time)
- `getStatus()` - Return current booking status
- `updateStatus(newStatus)` - Change status (admin/system action)
- `sendConfirmation()` - Trigger confirmation email
- `calculateDuration()` - Return duration in hours
- `checkConflict()` - Verify no overlap with existing bookings
- `markAsNoShow()` - Auto-flag if user doesn't check in

**Relationships:**
- **N..1:** Many bookings for one User
- **N..1:** Many bookings for one Resource
- **1..1:** Has one Approval (optional, when status changes)
- **1..1:** Generates one Notification

**Business Rules:**
- BR9: Bookings must be 15+ minutes and <= 8 hours duration
- BR10: Students can book max 5 concurrent bookings
- BR11: Bookings cannot start in the past
- BR12: A student can cancel own pending/approved booking anytime before start
- BR13: No-show automatically flagged if user not checked in 15 mins after start
- BR14: Resource cannot be double-booked (unique (resourceId, startDate, endDate))

---

### 3.4 Approval Entity

**Description:** Admin decision on a booking request (approve, reject, or conditional approval).

| Attribute | Type | Constraints |
|-----------|------|-------------|
| `approvalId` | String (UUID) | Primary Key, Unique, Not Null |
| `bookingId` | String (FK) | Not Null, References Booking, Unique |
| `adminId` | String (FK) | Not Null, References User (admin role) |
| `decision` | Enum: APPROVED, REJECTED, CONDITIONAL | Not Null |
| `reason` | String | Optional, Max 500 chars (required if REJECTED) |
| `conditions` | String | Optional, Max 500 chars (for CONDITIONAL approval) |
| `approvedAt` | DateTime | Timestamp of approval decision |

**Methods/Behaviors:**
- `approveBooking(bookingId, conditions?)` - Admin approves with optional conditions
- `rejectBooking(bookingId, reason)` - Admin rejects with mandatory reason
- `getDecision()` - Retrieve approval status and details
- `updateDecision(newDecision, reason?)` - Change approval decision
- `notifyUser()` - Send approval/rejection notification

**Relationships:**
- **N..1:** Many approvals by one Admin user
- **1..1:** One approval per booking
- **Triggers:** Notification to user

**Business Rules:**
- BR15: All pending bookings must be reviewed within 24 hours
- BR16: Rejection requires documented reason
- BR17: Conditional approvals allowed (e.g., "approved if attendee count < 20")
- BR18: Admin can reverse approval if booking not yet active

---

### 3.5 Notification Entity

**Description:** System-generated messages sent to users for status updates, reminders, and confirmations.

| Attribute | Type | Constraints |
|-----------|------|-------------|
| `notificationId` | String (UUID) | Primary Key, Unique, Not Null |
| `userId` | String (FK) | Not Null, References User |
| `type` | Enum: CONFIRMATION, APPROVAL, REJECTION, REMINDER, CANCELLATION, NO_SHOW_WARNING | Not Null |
| `subject` | String | Not Null, Max 200 chars |
| `message` | String | Not Null, Max 2000 chars |
| `channel` | Enum: EMAIL, SMS, IN_APP | Not Null |
| `status` | Enum: DRAFT, SENT, DELIVERED, FAILED, BOUNCED | Default: DRAFT |
| `sentAt` | DateTime | Timestamp when sent |
| `deliveredAt` | DateTime | Timestamp when received |
| `relatedBookingId` | String (FK) | Optional, References Booking |
| `createdAt` | DateTime | Timestamp of notification creation |

**Methods/Behaviors:**
- `createNotification(userId, type, message, channel)` - Generate notification
- `sendNotification()` - Send via email/SMS
- `retryFailedNotification()` - Retry failed delivery up to 3 times
- `markAsDelivered()` - Update status when confirmed received
- `getNotificationHistory(userId)` - Retrieve user's past notifications
- `cancelNotification()` - Cancel unsent notification

**Relationships:**
- **N..1:** Many notifications to one User
- **0..1:** Optionally linked to Booking
- **Triggered by:** Booking status changes, system events

**Business Rules:**
- BR19: Confirmation sent immediately upon booking submission
- BR20: Approval/Rejection sent within 2 minutes of admin decision
- BR21: 24-hour reminder sent for upcoming bookings (configurable)
- BR22: Failed delivery retried up to 3 times within 1 hour
- BR23: 95% email delivery rate SLA (monitored)

---

### 3.6 Maintenance Entity

**Description:** Scheduled downtime windows for resource maintenance, repairs, or updates.

| Attribute | Type | Constraints |
|-----------|------|-------------|
| `maintenanceId` | String (UUID) | Primary Key, Unique, Not Null |
| `resourceId` | String (FK) | Not Null, References Resource |
| `scheduledBy` | String (FK) | Not Null, References User (admin/maintenance role) |
| `startDate` | DateTime | Not Null |
| `endDate` | DateTime | Not Null, Must be >= startDate |
| `reason` | String | Not Null, Max 300 chars (e.g., "Projector lamp replacement") |
| `severity` | Enum: LOW, MEDIUM, HIGH | Default: MEDIUM |
| `status` | Enum: SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED | Default: SCHEDULED |
| `notes` | String | Optional, Max 500 chars |
| `createdAt` | DateTime | Timestamp of scheduling |
| `completedAt` | DateTime | Timestamp when maintenance finished |

**Methods/Behaviors:**
- `scheduleMaintenance(resourceId, dates, reason)` - Create maintenance window
- `startMaintenance()` - Begin maintenance, block all bookings
- `completeMaintenance()` - Finish maintenance, restore availability
- `cancelMaintenance()` - Cancel scheduled maintenance
- `getAffectedBookings()` - List bookings overlapping maintenance window
- `notifyAffectedUsers()` - Alert users of overlapping bookings
- `rescheduleBookings()` - Auto-suggest alternative times for affected bookings

**Relationships:**
- **N..1:** Many maintenance windows per Resource
- **N..1:** Scheduled by Admin/Maintenance User
- **References:** Multiple Bookings (affected)

**Business Rules:**
- BR24: Maintenance windows cannot overlap with existing bookings (must cancel first)
- BR25: HIGH severity maintenance can force-cancel bookings with 48-hour notice
- BR26: Users of affected bookings notified 24 hours before cancellation
- BR27: Resource status automatically changes to MAINTENANCE during window
- BR28: Maintenance log retained for audit trails (2-year retention)

---

## 4. Entity Relationships

### 4.1 Relationship Diagram (Narrative)

```
┌─────────────┐         ┌──────────────┐
│    User     │◄────────►│  Booking     │
│  (1..*) │    │(N..1) │
└─────────────┘    │    └──────────────┘
      │             │           │
      │             │        (1..1)
      │             │           │
      │          (N..1)      ┌──────────────┐
      │             │        │  Approval    │
      │             └───────►│              │
      │                      └──────────────┘
      │
      │◄────────────► (1..*) ┌──────────────┐
                            │ Notification │
      ├─────────────────────►└──────────────┘
      │
      ├─────────────────────► (1..*) ┌────────────────┐
                                    │  Resource      │
      │                            └────────────────┘
      │                                   │
      │                                (0..*)
      │                                   │
      └───────────────────────────────────┼──► ┌──────────────┐
                                           │   │  Maintenance │
                                           └──►│              │
                                               └──────────────┘
```

### 4.2 Key Relationships

| Relationship | Cardinality | Type | Notes |
|--------------|-------------|------|-------|
| User → Booking | 1..* | Composition | User creates many bookings |
| Resource → Booking | 1..* | Aggregation | Resource has many bookings |
| Booking → Approval | 1..0..1 | Association | Booking may have one approval |
| User → Approval | 1..* | Association | Admin user creates approvals |
| User → Notification | 1..* | Aggregation | User receives notifications |
| Booking → Notification | 0..1 | Association | Notification linked to booking |
| Resource → Maintenance | 1..* | Aggregation | Resource has maintenance windows |
| User → Maintenance | N..1 | Association | Maintenance scheduled by user |

---

## 5. Business Rules Summary

### Critical Rules (BR1-BR10)
1. **BR1:** Users must verify email before booking
2. **BR2:** Account lockout after 5 failed login attempts  
3. **BR3:** Admin password expires every 90 days
4. **BR4:** Users can cancel own pending bookings anytime
5. **BR5:** Resources cannot have overlapping maintenance periods
6. **BR6:** Bookings cannot be created during maintenance windows
7. **BR7:** Resource capacity constraints: Rooms (1-100), Labs (10-50), Equipment (1-5)
8. **BR8:** Resource availability auto-updates based on bookings
9. **BR9:** Booking duration: 15+ minutes and <= 8 hours
10. **BR10:** Students can maintain max 5 concurrent bookings

### Operational Rules (BR11-BR20)
11. **BR11:** Bookings cannot start in the past
12. **BR12:** Students cancel own pending/approved bookings before start
13. **BR13:** No-show auto-flagged if not checked in 15 mins after start
14. **BR14:** Unique constraint: (resourceId, startDate, endDate) — no double-booking
15. **BR15:** All pending bookings reviewed within 24 hours
16. **BR16:** Rejection requires documented reason
17. **BR17:** Conditional approvals allowed with criteria
18. **BR18:** Admin can reverse approval if booking not active
19. **BR19:** Confirmation sent immediately upon submission
20. **BR20:** Approval/Rejection sent within 2 minutes of admin decision

### Service Level & Compliance Rules (BR21-BR28)
21. **BR21:** 24-hour booking reminder sent (configurable)
22. **BR22:** Failed notifications retried up to 3 times within 1 hour
23. **BR23:** 95% email delivery rate SLA (monitored)
24. **BR24:** Maintenance windows cannot overlap existing bookings
25. **BR25:** HIGH severity maintenance can force-cancel with 48-hour notice
26. **BR26:** Affected users notified 24 hours before cancellation
27. **BR27:** Resource status = MAINTENANCE during maintenance window
28. **BR28:** Maintenance log retained 2 years for audit

---

## 6. Traceability to Previous Assignments

### Requirements (Assignment 4)
| BR | Requirement | Alignment |
|----|-------------|-----------|
| BR1, BR2, BR3 | FR1: User Authentication | Email verification, account lockout, password expiry |
| BR9, BR10, BR11 | FR4: Booking Submission | Duration constraints, booking limit, date validation |
| BR14 | FR4: Booking Submission | Conflict detection prevents double-booking |
| BR19, BR20, BR21, BR23 | FR7: Email Notifications | Confirmation, approval, reminders, SLA |
| BR5, BR6, BR7, BR8 | FR8: Resource Management | Capacity, availability tracking, maintenance blocking |
| BR24, BR25, BR26, BR27 | FR11: Maintenance Scheduling | Downtime management, notification, resource status |

### Use Cases (Assignment 5)
| Use Case | Domain Entity | Relationship |
|----------|---------------|--------------|
| Login | User | User.login() validates credentials |
| Browse Resources | Resource | Resource.checkAvailability() filters by date/time |
| Submit Booking | Booking | Booking.submitBooking() creates with conflict check |
| Approve/Reject Booking | Approval | Approval.approveBooking() or rejectBooking() |
| Manage Resources | Resource | Admin performs add/edit/delete via Resource.addResource() |
| View Profile | User | User.getBookingHistory() retrieves past bookings |
| Generate Reports | Booking, Resource | Query aggregate data across entities |
| Schedule Maintenance | Maintenance | Maintenance.scheduleMaintenance() blocks resource |

### State Diagrams (Assignment 8)
| Entity | States | Domain Model Mapping |
|--------|--------|----------------------|
| Resource | Available, Booked, InUse, Maintenance | Resource.availability enum |
| Booking | Pending, Approved, Active, Completed, Cancelled, NoShow | Booking.status enum |
| UserAccount | Inactive, Active, Suspended | User.status enum |
| Notification | Draft, Sent, Delivered, Failed | Notification.status enum |
| Approval | PendingReview, Approved, Rejected | Approval.decision enum |
| Maintenance | Scheduled, In Progress, Completed, Cancelled | Maintenance.status enum |

---

## 7. Conclusion

The domain model identifies 6 core entities (User, Resource, Booking, Approval, Notification, Maintenance) that collectively represent the Campus Resource Booking System's business logic. Each entity encapsulates specific attributes, methods, and business rules, ensuring clear separation of concerns and maintainability. The relationships between entities follow standard object-oriented principles, with proper cardinality and dependency management. This model forms the foundation for the subsequent class diagram (UML design) and ensures alignment with functional requirements, use cases, and behavioral modeling completed in prior assignments.
