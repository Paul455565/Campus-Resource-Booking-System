# Activity Workflow Modeling - Activity Diagrams

8 key workflows with swimlanes (User/System/Admin).

## 1. User Registration
```mermaid
flowchart TD
    A[Start] --> B[User enters details]
    B --> C{Valid email?}
    C -->|No| D[Error message]
    D --> A
    C -->|Yes| E[System: Create account]
    E --> F[System: Send verification email]
    F --> G[User verifies]
    G --> H[Account Active]
    H --> I[End]
```
**Explanation**: FR-001, addresses student registration needs.

## 2. Resource Booking
```mermaid
flowchart TD
    subgraph User [User]
        U1[Search resources]
        U2[Select time slot]
        U3[Submit request]
    end
    subgraph System [System]
        S1{Slot available?}
        S2[Create pending booking]
        S3[Send confirmation]
    end
    subgraph Admin [Admin]
        A1[Review request]
        A1 --> A2{Approve?}
    end
    U1 --> U2 --> U3
    U3 --> S1
    S1 -->|Yes| S2 --> A1
    A2 -->|Yes| S3
    S3 --> U3
```
**Explanation**: Core booking, parallel search/notify possible; meets scalability.

## 3. Payment Processing
```mermaid
flowchart TD
    P1[Initiate payment] --> P2{Valid?}
    P2 -->|No| P3[Fail]
    P2 -->|Yes| P4[Charge]
    P4 --> P5[Send receipt]
    P5 --> P6[End]
```
**Explanation**: For deposits.

## 4. Booking Cancellation
```mermaid
flowchart TD
    C1[User requests cancel] --> C2{Within policy?}
    C2 -->|Yes| C3[System: Release resource]
    C3 --> C4[Refund if paid]
```
**Explanation**: FR-007.

## 5. Check-in/Check-out
```mermaid
flowchart TD
    CI1[User arrives] --> CI2[System: Scan QR]
    CI2 --> CI3[Activate booking]
    CO1[User departs] --> CO2[Check-out]
    CO2 --> CO3[Update status]
```
**Explanation**: Usage tracking.

## 6. Admin Approval
```mermaid
flowchart TD
    AA1[Notification] --> AA2[Review]
    AA2 --> AA3{Approve?}
    AA3 -->|Yes| AA4[Notify user]
```
**Explanation**: Bottleneck control.

## 7. Issue Reporting
```mermaid
flowchart TD
    IR1[Report issue] --> IR2[System: Notify maintenance]
    IR2 --> IR3[Resolve]
```
**Explanation**: Maintenance.

## 8. Generate Report
```mermaid
flowchart TD
    GR1[Select period] --> GR2[Query DB]
    GR2 --> GR3[Generate PDF]
    GR3 --> GR4[Email/Save]
```
**Explanation**: Admin analytics.
