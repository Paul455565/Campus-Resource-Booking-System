# Domain Modeling & Class Diagram Reflection

**Assignment 9 - Reflective Analysis**  
**Author:** PAULOSE MAJA  
**Date:** April 27, 2026

---

## 1. Introduction

This reflection document critically examines the process of designing the domain model and class diagram for the Campus Resource Booking System. Drawing from prior assignments (requirements, use cases, state diagrams, and activity diagrams), I analyze the challenges encountered, design trade-offs made, and key lessons learned in object-oriented modeling. The reflection addresses how effective domain modeling bridges the gap between business requirements and technical implementation.

---

## 2. Challenges Faced in Domain Modeling

### 2.1 Identifying the Right Level of Abstraction

**Challenge:** Determining which concepts should be first-class entities (classes) versus properties or methods was initially ambiguous. For example, the decision to model "Approval" as a separate entity rather than a simple boolean field on Booking required careful consideration.

**Analysis:**
The first instinct was to include approval details directly in the Booking class (e.g., `isApproved: boolean`, `approvalDate: DateTime`). However, this approach violates several principles:
1. **Separation of Concerns:** Approval is a distinct business concept with its own lifecycle
2. **Auditability:** Tracking who approved and when requires first-class representation
3. **Reversibility:** The ability to reverse an approval before a booking activates requires independent state management
4. **Extensibility:** Future conditional approvals or escalation workflows need dedicated entity logic

**Resolution:** I created Approval as a standalone entity with its own attributes (decision, reason, conditions) and methods (approveBooking, rejectBooking, updateDecision). This aligned with the state diagram showing approval as a distinct state machine.

**Lesson:** Higher abstraction levels often emerge by asking "What can change independently?" and "Is this concept referenced by multiple other entities?" Approval satisfies both criteria.

### 2.2 Managing Relationships: Composition vs. Aggregation vs. Association

**Challenge:** Defining the correct relationship types between entities was complex. Initially, I treated all relationships uniformly, but careful analysis revealed subtle but important distinctions.

**Analysis Example - Booking vs. Notification:**
- **First Attempt:** Modeled as simple association (Booking → Notification)
- **Issue:** Didn't capture the semantic that notifications are generated *by* booking events, not independently
- **Revision:** Changed to "triggers" relationship, indicating causality and dependency

**Composition vs. Aggregation Decision:**
- **Composition (User ⊗ Booking):** User deletion cascades to bookings (too strict; bookings have independent audit value)
- **Aggregation (User ◇ Booking):** User can be deleted, bookings persist (better aligns with business logic)
- **Association (Booking ← → Approval):** Optional 1:1 relationship (some bookings lack approvals initially)

**Resolution:** I applied formal UML relationship semantics:
- **Composition:** Rare in this domain (Resource ⊗ Maintenance would imply deleting all maintenance records if resource deleted—incorrect)
- **Aggregation:** Common (User ◇ Bookings; Resource ◇ Maintenance)
- **Association:** For conditional dependencies (Booking → Approval, one-to-optional-one)

**Lesson:** Relationship semantics matter profoundly for system behavior. Composition implies cascading deletes; aggregation means independent lifecycles. Choosing incorrectly leads to data loss or orphaned records.

### 2.3 Defining Attributes vs. Derived Values

**Challenge:** Distinguishing between stored attributes and computed values (methods) requires understanding data persistence vs. computation.

**Example - Booking.duration:**
- **Option 1:** Store `duration: Integer` in Booking
- **Option 2:** Derive from `endDate - startDate` via method `calculateDuration()`

**Analysis:**
- Storing duration introduces redundancy (violates normalization)
- Storing creates maintenance burden (must update if dates change)
- Derivation ensures consistency but requires computation on-read

**Resolution:** I defined `calculateDuration(): Integer` as a method, not an attribute. This ensures consistency and reduces storage. Similarly:
- `Resource.getUtilizationRate()`: Computed from historical bookings, not stored
- `User.getBookingHistory()`: Queried from Booking table, not denormalized
- `Booking.isApproved()`: Method checking `approval?.decision == APPROVED`, not boolean field

**Lesson:** Store immutable or independent data; derive dependent data. Use methods for values that depend on multiple attributes or change frequently.

### 2.4 Balancing Completeness with Simplicity

**Challenge:** The domain model risks over-specification (too many entities) or under-specification (missing important concepts).

**Over-Specification Temptation:** Initially considered separate entities for:
- `BookingStatus` (instead of enum)
- `UserRole` (instead of enum)
- `NotificationTemplate` (for email templates)

**Under-Specification Risk:** Could have omitted:
- Maintenance entity (treated as property of Resource)
- Approval entity (treated as property of Booking)
- Notification entity (implicit in Booking status change)

**Resolution:** I applied the "Domain-Driven Design" principle: model entities only if they have distinct lifecycle, attributes, and behavior. Results:
- Enums sufficient for status/role values (no lifecycle needed)
- Approval and Maintenance justified as entities (independent lifecycle, audit trails)
- Notification justified (retry logic, delivery tracking, multi-channel support)

**Validation:** Mapped each entity to use cases:
- User: Login, Profile management
- Resource: Browse, Manage inventory
- Booking: Submit, Cancel, View
- Approval: Review pending, Approve/Reject
- Notification: Send confirmations, Reminders, Status updates
- Maintenance: Schedule, Handle affected bookings

All entities align with use cases; no orphaned entities.

**Lesson:** Include an entity if it satisfies multiple criteria:
1. Appears in multiple use cases
2. Has independent state transitions
3. Needs audit trail or historical tracking
4. Can be queried/filtered independently

### 2.5 Enforcing Business Rules at the Right Layer

**Challenge:** Deciding where business logic (28 business rules) should be enforced: in entities (domain layer), services (application layer), or database (persistence layer).

**Examples:**

**BR10: "Students can maintain max 5 concurrent bookings"**
- Option A: Enforced in `BookingService.createBooking()` (application layer)
- Option B: Enforced as database constraint (CHECK clause)
- Option C: Enforced in `Booking.submitBooking()` method (domain layer)

**Decision:** Three-layer approach:
1. **Domain Layer** (Booking class): Validate duration (15 min–8 hours)
2. **Application Layer** (BookingService): Enforce business rules (conflict detection, quota checks)
3. **Database Layer** (PostgreSQL constraints): Enforce immutable rules (unique booking per resource/time)

**BR14: "No double-booking on resource"**
- Database: UNIQUE constraint on (resourceId, startDate, endDate) prevents duplicates at persistence
- Service: Conflict detection algorithm checks before insertion
- Entity: `checkConflict()` method available for pre-submission validation

**Resolution:** I created a conceptual three-layer validation framework:
- **Entity Methods:** Local, immutable validations (date formats, enum values)
- **Service Methods:** Contextual, business-rule validations (conflicts, quotas, approvals)
- **Database Constraints:** Systemic, immutable constraints (unique, foreign keys, not null)

**Benefit:** Prevents rule enforcement from being scattered; ensures consistency across access paths.

**Lesson:** Business rules require defense-in-depth. No single layer is sufficient; layered validation ensures resilience against bugs and bypasses.

### 2.6 Modeling Temporal Constraints

**Challenge:** Accurately representing time-based constraints (booking windows, maintenance periods, temporal ordering) in a static class diagram is inherently difficult.

**Examples:**
- BR9: Booking duration must be 15 min–8 hours (temporal constraint)
- BR11: Bookings cannot start in the past (temporal ordering)
- BR21: 24-hour reminder sent before booking start (temporal calculation)
- BR24: Maintenance cannot overlap existing bookings (temporal conflict detection)

**Struggle:** The class diagram shows *structure*, not temporal logic. Methods like `checkAvailability(date, time, duration)` and `checkConflict()` exist in the diagram but their *how* (algorithm) isn't captured.

**Resolution:** I addressed temporal constraints through:
1. **Explicit Attributes:** `startDate`, `endDate` on Booking and Maintenance
2. **Temporal Methods:** `calculateDuration()`, `getBookingsInTimeRange()`
3. **Temporal Invariants:** Documented in class descriptions (e.g., "endDate ≥ startDate")
4. **Behavioral Documentation:** Included pre/post-conditions in method descriptions

**Not Resolved in Class Diagram:** The actual conflict-detection algorithm, reminder scheduling, or temporal queries—these belong in detailed design or implementation.

**Lesson:** Class diagrams are static structures; temporal logic requires behavioral documentation (sequence diagrams, algorithm descriptions, or pseudocode). Acknowledge this limitation upfront.

---

## 3. Alignment with Previous Assignments

### 3.1 Requirements (Assignment 4) Validation

**Requirement FR1: User Authentication**
- Domain Model: User entity with email, password (hashed), status, role attributes; login/register/changePassword methods
- Class Diagram: AuthenticationService encapsulates auth logic; User class has visibility constraints
- Alignment: ✓ Complete; all authentication requirements mapped

**Requirement FR4: Booking Submission with Conflict Detection**
- Domain Model: Booking entity with PENDING status; conflict check business rule (BR14)
- Class Diagram: BookingService.createBooking() with checkConflict() method
- Alignment: ✓ Complete; conflict detection explicit in both model and diagram

**Requirement FR7: Email Notifications with 95% SLA**
- Domain Model: Notification entity with status tracking; retry logic; 95% SLA business rule (BR23)
- Class Diagram: NotificationService with sendNotification(), retryFailedNotification(), getDeliveryStatus() methods
- Alignment: ✓ Complete; SLA tracking and retry mechanism explicitly modeled

**Requirement FR11: Maintenance Scheduling**
- Domain Model: Maintenance entity with status, severity; methods to schedule, start, complete; affect affected bookings
- Class Diagram: MaintenanceService with scheduleMaintenance(), startMaintenance(), handleAffectedBookings()
- Alignment: ✓ Complete; maintenance lifecycle fully modeled

**Overall Assessment:** 12/12 functional requirements mapped. Domain model and class diagram provide implementation roadmap for each requirement.

### 3.2 Use Cases (Assignment 5) Validation

| Use Case | Mapped Entities | Methods Involved | Status |
|----------|-----------------|-----------------|--------|
| Login | User, AuthenticationService | loginUser(), validateToken() | ✓ |
| Browse Resources | Resource, ResourceService | getAvailableResources(), checkAvailability() | ✓ |
| Submit Booking | Booking, BookingService | createBooking(), checkConflict() | ✓ |
| Approve/Reject Booking | Approval, ApprovalService | approveBooking(), rejectBooking() | ✓ |
| Manage Resources | Resource, ResourceService | addResource(), editResource(), deleteResource() | ✓ |
| View Profile | User | updateProfile(), getBookingHistory() | ✓ |
| Generate Reports | Resource, ResourceService | getResourceUtilization() | ✓ |
| Schedule Maintenance | Maintenance, MaintenanceService | scheduleMaintenance(), handleAffectedBookings() | ✓ |

**Validation:** Every use case maps to at least one domain entity and corresponding service method. No orphaned use cases; no orphaned classes.

### 3.3 State Diagrams (Assignment 8) Validation

**Booking State Machine (Assignment 8):**
```
PENDING → APPROVED → ACTIVE → COMPLETED
   ↓         ↓          ↓
REJECTED   CANCELLED   NO_SHOW
```

**Domain Model / Class Diagram Mapping:**
- Booking.status enum includes all 7 states
- updateStatus() method enforces valid transitions
- State transitions triggered by methods:
  - PENDING→APPROVED: ApprovalService.approveBooking()
  - PENDING→REJECTED: ApprovalService.rejectBooking()
  - APPROVED→ACTIVE: (implicit on booking start time)
  - ACTIVE→COMPLETED: (implicit on booking end time)
  - APPROVED→CANCELLED: Booking.cancelBooking()
  - ACTIVE→NO_SHOW: Booking.markAsNoShow()

**Validation:** Class diagram *enables* all state transitions defined in state diagrams. No state defined in diagrams is missing from model.

**Resource State Machine (Assignment 8):**
```
AVAILABLE → BOOKED → InUse → AVAILABLE
              ↓             ↓
           MAINTENANCE    MAINTENANCE
```

**Domain Model / Class Diagram Mapping:**
- Resource.availability enum: AVAILABLE, BOOKED, MAINTENANCE, RETIRED
- Transitions triggered by:
  - AVAILABLE→BOOKED: Booking creation (implicit)
  - BOOKED→InUse: Check-in logic (implicit)
  - InUse→AVAILABLE: Check-out logic (implicit)
  - *→MAINTENANCE: Maintenance.startMaintenance()
  - MAINTENANCE→AVAILABLE: Maintenance.completeMaintenance()

**Validation:** All state machine transitions map to class methods or business logic.

### 3.4 Agile Planning (Assignment 6) & Activity Diagrams (Assignment 8)

**Activity Diagram for "Submit Booking" flow:**
```
[Start] → Login → Browse Resources → Submit Booking 
→ [Validate] → Conflict Check → Create PENDING → Send Confirmation → [End]
```

**Domain Model / Class Diagram Mapping:**
1. **Login:** AuthenticationService.loginUser() → returns User with JWT
2. **Browse Resources:** ResourceService.getAvailableResources()
3. **Submit Booking:** BookingService.createBooking()
4. **Validate:** Booking.submitBooking() pre-conditions (user active, resource available)
5. **Conflict Check:** BookingService.checkConflicts()
6. **Create PENDING:** Booking entity created with status=PENDING
7. **Send Confirmation:** NotificationService.sendBookingConfirmation() triggered
8. **End**

**Validation:** Every activity in diagram is implemented via a class method. No activity lacks corresponding implementation.

---

## 4. Trade-Offs Made During Design

### Trade-Off 1: Explicit vs. Implicit Status Management

**Trade-Off:** Should resource availability (AVAILABLE, BOOKED, InUse, MAINTENANCE) be:
- **Explicit:** Dedicated `availability` attribute updated on every booking/maintenance change
- **Implicit:** Derived from current bookings and maintenance windows at query time

**Pros of Explicit:**
- O(1) query performance for availability checks
- Clear current state immediately visible

**Cons of Explicit:**
- Requires synchronization logic (if a booking is cancelled, who updates resource.availability?)
- Risk of state inconsistency (availability field and actual bookings out of sync)

**Pros of Implicit:**
- Single source of truth (bookings table)
- No synchronization needed
- Always consistent

**Cons of Implicit:**
- O(n) query cost (must scan all bookings to determine availability)
- Slower for resource browsing

**Decision:** Explicit attribute with synchronization logic
- **Rationale:** Booking availability is frequently queried (every page load); O(1) performance critical for UX
- **Mitigation:** Synchronization enforced in both BookingService and MaintenanceService
- **Database:** Trigger or application-level logic ensures consistency

**Lesson:** Performance often trumps purity in practical systems. Accept synchronization burden to achieve acceptable query speed. Document synchronization thoroughly to prevent bugs.

### Trade-Off 2: Foreign Keys vs. Denormalization

**Trade-Off:** Should Booking include?
- **Option A (Normalized):** Only `userId` and `resourceId` foreign keys; look up user/resource details on demand
- **Option B (Denormalized):** Store `userName`, `resourceName`, `resourceLocation` directly on Booking

**Normalized Approach:**
- **Pros:** Single source of truth; updates to user/resource name reflected everywhere
- **Cons:** Join queries to retrieve booking details

**Denormalized Approach:**
- **Pros:** Single-table queries faster; no joins needed
- **Cons:** Data redundancy; update anomalies (if user name changes, stale data in Bookings)

**Decision:** Normalized (foreign keys only)
- **Rationale:** Data consistency more important than read performance for operational system
- **Mitigation:** Caching at application level for frequently accessed data
- **DB Optimization:** Indexes on foreign keys ensure efficient joins

**Lesson:** Normalization reduces bugs; denormalization increases performance. Choose normalization for correctness; optimize later if needed, with careful cache management.

### Trade-Off 3: Service Layer Overhead

**Trade-Off:** Every operation filtered through services (BookingService, ApprovalService, etc.)?
- **Option A (Services Everywhere):** All operations routed through services
- **Option B (Direct Entity Use):** Entities directly modified; less boilerplate

**Service Layer Overhead:**
- **Pros:** Business logic centralized; testable; enforces consistent rules
- **Cons:** Extra method calls; more classes; potential complexity

**Direct Entity Use:**
- **Pros:** Faster to code; fewer indirections
- **Cons:** Business logic scattered; hard to enforce consistency; difficult to test

**Decision:** Service layer for all public operations
- **Rationale:** Enforcement of business rules (28 rules!) requires centralization
- **Structure:** User-facing methods in services; internal helper methods in entities
- **Example:** `BookingService.createBooking()` calls `Booking.submitBooking()` (validation), then `BookingRepository.save()`

**Lesson:** Boilerplate worth the cost when domain is complex. Service layer is investment in maintainability, not premature abstraction.

### Trade-Off 4: Role-Based Access Control (RBAC) Complexity

**Trade-Off:** How much RBAC logic belongs in domain model?
- **Option A (Domain-Level RBAC):** Each entity checks caller's role before allowing operations
- **Option B (Application-Level RBAC):** Domain model trusts caller; application layer enforces permissions

**Domain-Level RBAC:**
- **Pros:** Defense-in-depth; difficult to bypass
- **Cons:** Role-checking logic scattered across entities

**Application-Level RBAC:**
- **Pros:** Cleaner domain model; RBAC logic centralized
- **Cons:** Easy to accidentally bypass if layer not checked

**Decision:** Application-level RBAC (in services and controllers) with validation in domain
- **Rationale:** RBAC is cross-cutting; centralizing in security middleware (not domain) reduces coupling
- **Example:** `ApprovalService.rejectBooking()` called only by authenticated admin; entity doesn't re-check role
- **Safety:** Framework (Spring, Express.js) enforces RBAC at controller layer before service call

**Lesson:** RBAC is infrastructure concern, not domain logic. Keep domain model focused on business rules; enforce access control at boundaries (API controllers, services).

### Trade-Off 5: Cascading Deletes

**Trade-Off:** If admin deletes Resource, should Bookings be cascade-deleted?
- **Option A (Cascade Delete):** Resource.delete() → all bookings deleted automatically
- **Option B (Soft Delete):** Resource marked RETIRED; bookings persisted for audit
- **Option C (Prevent Delete):** Throw error if resource has bookings

**Cascade Delete:**
- **Pros:** Clean (no orphans)
- **Cons:** Data loss; audit trail destroyed

**Soft Delete:**
- **Pros:** Audit trail preserved; can query historical data
- **Cons:** "Deleted" resources still appear in queries (unless filtered)

**Prevent Delete:**
- **Pros:** Forces administrative cleanup
- **Cons:** Rigid; may prevent resource removal

**Decision:** Soft delete (Option B) implemented as Resource.retire()
- **Rationale:** Bookings are business records; never delete; only deprecate resources
- **Implementation:** `Resource.availability = RETIRED`; queries filter out retired resources
- **Benefit:** Admin can view historical bookings for retired resources; audit trail preserved

**Lesson:** Rarely truly delete data in transactional systems. Soft deletes better preserve audit trails and system integrity. Cost is extra filtering in queries.

---

## 5. Lessons Learned About Object-Oriented Design

### 5.1 Entities vs. Value Objects

**Realization:** Not all classes are equal. Some are *entities* (have identity and lifecycle), others are *value objects* (immutable, identified by value).

**Entities (in this domain):**
- User (identity: userId)
- Resource (identity: resourceId)
- Booking (identity: bookingId)
- Approval (identity: approvalId)
- Notification (identity: notificationId)
- Maintenance (identity: maintenanceId)

**Value Objects (in this domain):**
- DateTime values (start/end dates)
- Status enums (PENDING, APPROVED, etc.)
- Address/Location strings

**Impact:** Entities need:
- Unique identity
- Lifecycle methods (create, update, delete)
- Relationships to other entities
- Audit fields (createdAt, updatedAt)

Value objects:
- No identity tracking needed
- Can be compared by value
- Immutable

**Lesson:** Distinguish entities from value objects early. Entities are expensive; only create if needed. Overusing entities complicates the model.

### 5.2 Domain-Driven Design Principles

**Realization:** The 6 entities cluster into a natural "Bounded Context"—a cohesive domain area. They're tightly related, not scattered across different concerns.

**Bounded Context (Campus Resource Booking):**
```
Core Entities: User, Resource, Booking
Support Entities: Approval, Notification, Maintenance
Invariants: No double-booking, approval within 24h, 95% notification delivery
```

This boundary helps future scaling. If the system grows to include, e.g., payment processing, that would be a separate bounded context with its own entities (Payment, Invoice, Refund).

**Lesson:** Group related entities into bounded contexts. Each context has clear boundaries, consistent rules, and minimal external dependencies. When contexts grow too large, split them.

### 5.3 The Importance of Enums

**Realization:** Enums (UserRole, BookingStatus, ResourceType, etc.) are underrated. They're not just convenience; they're critical for correctness.

**Before (naive approach):** `booking.status = "pending"` (string)
- **Problem:** Typos: `"PENING"`, `"pending"`, `"Pending"` all possible
- **Detection:** Only caught at runtime when string doesn't match expected value
- **Propagation:** Bugs in booking lookup, filtering, reporting

**After (enum approach):** `booking.status = BookingStatus.PENDING` (enumeration)
- **Compile-time checking:** Invalid values rejected by type system
- **IDE support:** Auto-completion prevents typos
- **Exhaustiveness checking:** Compiler warns if all enum cases not handled

**Impact:** Introduced 8 enums (UserRole, BookingStatus, ResourceType, etc.); dramatically reduced potential bugs.

**Lesson:** Replace string magic values with enums. Cost is minimal; benefit is type safety and self-documentation. Always.

### 5.4 Composition Over Inheritance

**Realization:** Inheritance is tempting but often wrong. Composition is safer.

**Failed Inheritance Attempt:**
```
class Entity (abstract)
  - id: String
  - createdAt: DateTime
  - updatedAt: DateTime

class User extends Entity { ... }
class Booking extends Entity { ... }
class Notification extends Entity { ... }
```

**Problem:** Not all entities need identical audit fields. Notification.createdAt and Notification.sentAt have different meanings. Forced inheritance adds unnecessary baggage.

**Revised (Composition):**
```
class Entity {
  - id: String
  - createdAt: DateTime
  - updatedAt: DateTime
}

class User extends Entity { ... }
class Booking extends Entity { ... }
class Notification extends Entity { ... }
```

Actually, inheritance *was* appropriate here! All entities do need audit fields. But the key lesson:

**Better Approach:** Don't inherit fields; use composition or trait/mixin pattern if language supports.

**Lesson:** Inheritance is fragile; prefer composition. "Is-A" relationships are rare. "Has-A" relationships are common. When in doubt, compose.

### 5.5 Validation at Multiple Layers

**Realization:** A single validation layer is insufficient. Robust systems validate at every layer.

**Example - Booking Duration:**
1. **Frontend:** HTML5 `<input type="time">` prevents nonsensical time entry
2. **API Layer:** REST controller validates `duration >= 15 min AND duration <= 8 hours`
3. **Domain Layer:** Booking.submitBooking() checks invariant
4. **Database Layer:** CHECK constraint `(endDate - startDate) BETWEEN 15m AND 8h`
5. **Integration Tests:** Verify violation rejected at each layer

**Benefit:** Defense-in-depth. A frontend bug doesn't expose system to invalid data. An API bypass doesn't expose database. No single failure point.

**Lesson:** Never rely on a single validation layer. Invest in layered validation. The cost of redundant checks is trivial compared to damage of invalid data.

### 5.6 Naming Clarity

**Realization:** Clear naming prevents bugs more than complex logic catches.

**Examples:**
- `checkAvailability()` vs. `isAvailable()` — former returns detailed info; latter boolean (ambiguous)
- `markAsNoShow()` vs. `setNoShow()` — former (action verb) clarifies it's a system action; latter suggests property update
- `sendNotification()` vs. `notify()` — former specific (send); latter vague (could mean "mark notified")

**Impact:** Reviewed naming across all methods; renamed ambiguous ones. Result: code is self-documenting.

**Lesson:** Invest in naming. Method names should communicate intent (action, state check, computation). Ambiguous names breed bugs. Use names to enforce business semantics.

---

## 6. Key Insights

### 6.1 Domain Modeling is Discovery, Not Documentation

The domain modeling process wasn't about formalizing requirements into a diagram. It was a *discovery* process:
1. **Requirements Define Boundaries:** FR1-FR12 scope the domain
2. **Use Cases Define Interactions:** Which entities must interact to fulfill each use case?
3. **State Diagrams Define Dynamics:** How do entities transition through states?
4. **Domain Model Synthesizes:** Consolidate entities, methods, relationships, rules

Without this iterative discovery, the class diagram would have missed subtleties (e.g., Approval as distinct entity, Maintenance blocking logic).

**Lesson:** Involve stakeholders during domain modeling, not just at requirements gathering. Domain modeling surfaces questions: "Can an admin reverse an approval?" "What happens to bookings during maintenance?" Answering these upfront prevents late-stage redesign.

### 6.2 Traceability is Non-Negotiable

Every domain entity traces back to:
- At least one functional requirement (FR)
- At least one use case
- At least one state diagram
- At least one activity diagram

This traceability ensures:
1. No orphaned entities (wastes complexity)
2. No orphaned requirements (risks incomplete implementation)
3. Change impact analysis (if FR changes, which entities affected?)

**Lesson:** Build a traceability matrix during design. Artifacts should link bidirectionally. Missing links indicate design gaps.

### 6.3 Constraints Enable Correctness

Business rules (28 total) aren't "nice-to-have extras." They're the domain's essence. Constraints prevent bad states:
- **Temporal Constraints:** Prevent past bookings, overlapping maintenances
- **Cardinality Constraints:** Prevent students from overbooking, admins approving without reason
- **State Constraints:** Prevent cancelling active bookings, deleting in-use resources

Each constraint removes a degree of freedom, reducing the system's state space. Smaller state space = fewer bugs.

**Lesson:** Enumerate domain constraints early. Don't treat them as afterthoughts. Each constraint is an opportunity to prevent invalid states.

---

## 7. Conclusion

Designing the domain model and class diagram for the Campus Resource Booking System was a complex, iterative process that revealed deep insights into object-oriented design. Key takeaways:

1. **Abstraction Levels Matter:** Choose entity granularity carefully; not every concept warrants a class.
2. **Relationships are Semantics:** Composition, aggregation, association carry meaning; choose consciously.
3. **Business Rules are First-Class:** Constraints belong in the domain model, not scattered across code.
4. **Validation is Defense-in-Depth:** Single-layer validation insufficient; enforce at every layer.
5. **Traceability Ensures Coherence:** Link domain model to requirements, use cases, state diagrams.
6. **Names are Code:** Invest in clear, intention-revealing names.
7. **Enums are Safety:** Replace string magic with enums.
8. **Composition > Inheritance:** Favor delegation and composition over inheritance hierarchies.

The resulting domain model—6 core entities with 28 business rules, organized into service layer, aligned with all prior assignments—provides a solid foundation for implementation. It balances completeness (no orphaned requirements) with simplicity (no unnecessary abstractions). Future developers can understand the system's structure and constraints by reading the domain model, without diving into implementation details.

The domain modeling process itself proved invaluable. Questions raised during modeling ("Should approval be reversible?" "How do we handle maintenance-affected bookings?") forced clarifications that improved overall system design. This is why domain modeling is not documentation; it's discovery.

---

## 8. Reflection Summary Table

| Aspect | Challenge | Resolution | Lesson |
|--------|-----------|-----------|--------|
| **Abstraction** | What should be an entity? | Evaluate lifecycle, audit needs, independent querying | Higher-level concepts emerge by asking "changes independently?" |
| **Relationships** | Composition vs. Aggregation? | Analyzed cascading impacts; chose aggregation for user/bookings | Semantics matter; wrong choice causes data loss |
| **Attributes** | Store or compute? | Store immutable; compute derived values | Reduce redundancy; ensure consistency via methods |
| **Completeness** | Over/under-specify? | Mapped every entity to use cases | Include if used in 2+ use cases or needs audit |
| **Business Rules** | Where to enforce? | Three layers: entity, service, database | Defense-in-depth prevents bypasses |
| **Temporal Logic** | How to model time? | Attributes + methods + documentation | Class diagram static; temporal logic needs sequence diagrams |
| **Performance** | Explicit vs. implicit state? | Explicit with synchronization logic | Performance outweighs purity; mitigate with documentation |
| **Normalization** | Normalize or denormalize? | Normalized with caching | Correctness first; optimize if needed |
| **Service Layer** | Necessary overhead? | Yes; enforces consistency | Cost justified by business rule complexity |
| **RBAC** | Domain or app layer? | App layer (services/controllers) | RBAC is infrastructure; keep domain focused |
| **Cascading Deletes** | Delete or retain? | Soft delete; retain audit trail | Never truly delete; soft deletes preserve history |

---

**Reflection completed on April 27, 2026**  
**Words: 3,500+**
