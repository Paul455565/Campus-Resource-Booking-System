# Campus Resource Booking System

## Assignments

- [Ass4: System Specification](./SPECIFICATION.md), [Requirements](./SYSTEM_REQUIREMENTS.md)
- [Ass5: Use Case Specifications](./USE_CASE_SPECIFICATIONS.md)
- [Ass6: Kanban Board](./template_analysis.md), [Explanation](./kanban_explanation.md)
- [Ass7: Architecture & Modeling](./ARCHITECTURE.md), [State Diagrams](./state_diagrams.md), [Activity Diagrams](./activity_diagrams.md), [Reflection](./modeling_reflection.md)
- [Ass9: Domain Modeling & Class Diagram](./DOMAIN_MODEL.md), [Class Diagram](./CLASS_DIAGRAM.md), [Reflection](./DOMAIN_MODELING_REFLECTION.md)

## Repository Pattern Implementation

- Added generic `Repository<T, ID>` interface plus entity-specific interfaces such as `BookingRepository` and `ResourceRepository`.
- Used generics to avoid duplication across entity repositories and to keep CRUD signatures consistent across domain types.
- Implemented in-memory persistence with `HashMap` storage under `/src/main/java/com/campus/resourcebooking/repositories/inmemory`.
- Added a `RepositoryFactory` abstraction in `/src/main/java/com/campus/resourcebooking/factories` so services can request a storage backend without depending on implementation details.
- Included a future stub backend with `DatabaseBookingRepository` and `DatabaseResourceRepository` to show extensibility for later storage options.
- Added unit tests for in-memory CRUD operations under `/src/test/java/com/campus/resourcebooking/repositories`.

