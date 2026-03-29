# Reflection: Challenges in Balancing Stakeholder Needs

**Campus Resource Booking System**  
**Assignment 4 - Reflection**  
**Author:** PAULOSE MAJA  
**Date:** [18 March 2026]

## Overview
Documenting requirements revealed complex trade-offs in stakeholder priorities for the Campus Resource Booking System. Balancing 7 diverse groups—while building atop Assignment 3's foundation—required prioritization, negotiation simulations, and measurable compromises.

## Key Challenges

### 1. Conflicting Priorities: Instant vs Controlled Access (Students vs Admins)
- **Issue:** Students demand self-service instant booking for flexibility (*pain point: double-bookings from manual systems*), but Resource Admins/IT require approval workflows to prevent abuse/resource overuse (*concern: fair allocation*).
- **Trade-off:** Implemented pending status with <30min approvals (NFR compromise: auto-reminders). Students get real-time visibility (FR3 Calendar), admins get dashboards (FR5).
- **Resolution:** Success metric: 90% student satisfaction via quick feedback loops. Challenge: Agile iterations to refine approval SLAs.

### 2. Scalability vs Usability Overhead
- **Issue:** University Admins/IT want scalability for 5k users (NFR7), but Faculty/Students prioritize intuitive UI without perf hits (*pain: slow manual tracking*).
- **Trade-off:** Lightweight React SPA + PostgreSQL indexing (<2s loads, NFR10). Avoided heavy real-time websockets initially; used polling.
- **Lesson:** Non-functional reqs drove stack choice (Node for concurrency).

### 3. Security vs Friction (Auditors vs Users)
- **Issue:** Auditors demand full encryption/logs (NFR8/9), but Students/Faculty resist complex logins (*pain: forgotten sessions*).
- **Trade-off:** JWT + optional 2FA; minimal fields in profiles. RBAC ensures auditors access logs without exposing user data.
- **Resolution:** Balance via privacy-by-design; transparent policy docs.

### 4. Resource Equity vs Peak Demand (Admins vs Students/Faculty)
- **Issue:** Maintenance Staff need easy downtime flags (FR11), but peaks overload popular rooms.
- **Trade-off:** Priority queues + analytics (FR10) for fair allocation. Utilization metrics (>75%) guide policy.

### 5. Documentation Overhead in Agile Context
- **Challenge:** Detailed tables/SRD took precedence over code, but ensured traceability. Risk: Requirements creep—mitigated by scoping to Assignment 3 features.

## Lessons Learned & Future
- **MoSCoW Prioritization** helped: Must-haves (booking/auth) over wants (advanced reports).
- **Stakeholder Personas** clarified pains (e.g., mobile for students).
- **Trade-off Matrix:** Quantified impacts (e.g., instant booking +10% no-shows).
Challenges built empathy for real-world RE; system now robust for iterations. Word count: 512.

## Assignment 5: Challenges in Translating Requirements to Use Cases/Tests

Translating the detailed requirements from Assignment 4 into use case diagrams, specifications, and test cases revealed several challenges in bridging the gap between stakeholder needs and actionable system models. This process required careful abstraction, ensuring completeness, and anticipating edge cases while maintaining traceability.

### 1. Abstraction from Requirements to Use Cases
- **Issue:** Functional requirements (FR) are often granular (e.g., FR4: "Submit requests with conflict check"), but use cases need to encapsulate user goals and interactions. Mapping 12 FRs to 8+ use cases risked oversimplification or redundancy.
- **Challenge:** Identifying critical use cases (e.g., selecting "Submit Booking" over minor ones like "View Profile") while ensuring all FRs are covered. Relationships like inclusions (e.g., Submit Booking includes Browse Resources) added complexity.
- **Resolution:** Prioritized use cases based on stakeholder impact (e.g., booking workflow for students/admins). Used the architecture diagrams from Assignment 3 to guide actor interactions. Lesson: Use cases should focus on "what" users achieve, not "how" the system implements.

### 2. Defining Actors and Relationships
- **Issue:** Stakeholders from Assignment 4 (7 total) translated to actors, but some overlap (e.g., Resource Admin vs. University Admin). Deciding on 6+ actors without bloating the diagram.
- **Trade-off:** Grouped similar roles (e.g., Faculty and Student share booking use cases) but kept distinct for role-based access (RBAC from NFR8). Generalizations were minimal to avoid confusion.
- **Resolution:** Aligned with system context diagram; ensured each actor has unique concerns. Challenge: Balancing diagram clarity with completeness—too many actors made it cluttered.

### 3. Detailing Use Case Specifications
- **Issue:** Writing specifications for 8 use cases required expanding FRs into preconditions, postconditions, basic/alternative flows. Ensuring consistency with NFRs (e.g., performance in flows) without introducing untraceable details.
- **Challenge:** Anticipating exceptions (e.g., "Book not found" in alternative flows) based on requirements, but avoiding over-specification that could limit design flexibility.
- **Resolution:** Used acceptance criteria from FRs as postconditions. Incorporated stakeholder pain points (e.g., conflict checks for admins). Word count per spec kept concise.

### 4. Developing Test Cases
- **Issue:** Creating 8+ functional tests traceable to FRs was straightforward, but non-functional tests (e.g., performance for 1000 users) required simulation tools not yet implemented.
- **Challenge:** Defining measurable steps (e.g., "Simulate 1000 concurrent users") without actual code. Ensuring tests cover edge cases like invalid inputs or security breaches.
- **Trade-off:** Focused on black-box tests for validation; placeholders for actual results. Non-functional tests highlighted gaps (e.g., need for load testing tools).
- **Resolution:** Mapped tests directly to FR/NFR IDs; included examples like email delivery rate. Lesson: Tests should be executable in future sprints.

### 5. Ensuring Traceability and Consistency
- **Issue:** Maintaining links to prior assignments (e.g., stakeholders in use cases, FRs in tests) while updating documentation.
- **Challenge:** Avoiding inconsistencies (e.g., actor names across files). Documentation overhead increased with multiple files.
- **Resolution:** Cross-referenced files; used Mermaid for visual diagrams to aid clarity. Overall, the process emphasized iterative refinement—use cases informed tests, and vice versa.

This assignment deepened understanding of UML/test-driven development in requirements engineering. Challenges like abstraction underscored the importance of user-centric modeling, preparing the system for implementation. Future iterations could integrate automated testing tools. Word count: 498.

