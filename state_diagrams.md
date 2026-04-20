# Object State Modeling - State Transition Diagrams

Diagrams for 8 critical objects in Campus Resource Booking System, mapped to functional requirements (inferred FR from Assignment 4: FR-001 User Reg, FR-002 Search/Book, etc.).

## 1. Resource (e.g., Room/Equipment)
```mermaid
stateDiagram-v2
    [*] --> Available
    Available --> Booked : bookRequest
    Booked --> InUse : checkIn [time valid]
    Booked --> Available : cancel [before start]
    InUse --> Available : checkOut
    InUse --> Maintenance : reportIssue
    Maintenance --> Available : repairComplete
```
**Explanation**: Tracks resource lifecycle. Maps to FR-002 (booking), FR-007 (cancellation). Guard ensures no overlap bookings.

## 2. Booking
```mermaid
stateDiagram-v2
    [*] --> Pending
    Pending --> Approved : adminApprove
    Pending --> Rejected : conflict/invalid
    Approved --> Active : checkIn
    Approved --> Cancelled : userCancel [before start]
    Active --> Completed : checkOut
    Active --> NoShow : autoAfterStart
```
**Explanation**: Booking flow with admin approval. Aligns with Assignment 6 user story "Submit booking request".

## 3. UserAccount
```mermaid
stateDiagram-v2
    [*] --> Inactive
    Inactive --> Active : register/verifyEmail
    Active --> Suspended : violations
    Active --> Inactive : deactivate
    Suspended --> Active : adminReinstate
```
**Explanation**: User lifecycle, FR-001 registration.

## 4. Payment
```mermaid
stateDiagram-v2
    [*] --> Pending
    Pending --> Paid : processSuccess
    Pending --> Failed : invalidCard
    Paid --> Refunded : cancelRequest
```
**Explanation**: For paid resources, FR-004 payment.

## 5. Approval
```mermaid
stateDiagram-v2
    [*] --> PendingReview
    PendingReview --> Approved : adminOK
    PendingReview --> Rejected : adminDeny
```
**Explanation**: Admin approval process.

## 6. Notification
```mermaid
stateDiagram-v2
    [*] --> Draft
    Draft --> Sent : triggerEvent
    Sent --> Delivered : received
    Sent --> Failed : bounce
```
**Explanation**: Email/SMS for confirmations.

## 7. Session (User Login)
```mermaid
stateDiagram-v2
    [*] --> LoggedOut
    LoggedOut --> Authenticated : loginSuccess
    Authenticated --> LoggedOut : logout/timeout
```
**Explanation**: Security, FR-003 auth.

## 8. Report
```mermaid
stateDiagram-v2
    [*] --> Generated
    Generated --> Archived : periodEnd
```
**Explanation**: Usage reports for admin.
