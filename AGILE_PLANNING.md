# Agile Planning Document

**Campus Resource Booking System**  
**Assignment 6 - Agile User Stories, Backlog, and Sprint Planning**  
**Author:** PAULOSE MAJA  
**Date:** March, 2026  

## 1. User Story Creation

User stories are derived from functional requirements (FR) in SYSTEM_REQUIREMENTS.md and use cases in USE_CASE_SPECIFICATIONS.md. They follow INVEST criteria: Independent, Negotiable, Valuable, Estimable, Small, Testable. Stories are formatted as "As a [role], I want [action] so that [benefit]."

| Story ID | User Story | Acceptance Criteria | Priority (High/Medium/Low) |
|----------|------------|---------------------|----------------------------|
| US-001 | As a Student, I want to log in with my credentials so that I can access the system securely. | - Valid credentials grant access with JWT token.<br>- Invalid credentials show error message.<br>- Optional 2FA for enhanced security. | High |
| US-002 | As a Student, I want to browse resources with filters (type, location, time) so that I can find available options quickly. | - Filters apply correctly.<br>- Real-time availability displayed.<br>- Results load within 2 seconds. | High |
| US-003 | As a Student, I want to view a calendar showing availability so that I can select suitable times. | - Calendar displays free/busy/maintenance slots color-coded.<br>- Updates in real-time. | High |
| US-004 | As a Student, I want to submit a booking request so that I can reserve a resource. | - Form validates input.<br>- Conflict check prevents overlaps.<br>- Booking set to pending status. | High |
| US-005 | As a Resource Administrator, I want to view a dashboard of pending bookings so that I can manage approvals efficiently. | - List of pending bookings shown.<br>- Filters by date/resource. | High |
| US-006 | As a Resource Administrator, I want to approve or reject bookings with reasons so that users are informed. | - Status updates correctly.<br>- Auto-notification sent.<br>- Reasons logged. | High |
| US-007 | As a Student, I want to receive email notifications on booking status changes so that I stay updated. | - Emails sent on approval/rejection/reminders.<br>- 95% delivery rate.<br>- 24h prior reminders. | Medium |
| US-008 | As a Resource Administrator, I want to manage resources (add/edit/delete) so that the inventory is current. | - CRUD operations work.<br>- Changes reflect in browse. | Medium |
| US-009 | As a Student, I want to view and edit my profile and bookings so that I can manage my account. | - History displayed.<br>- Pending bookings cancellable. | Medium |
| US-010 | As a University Administrator, I want to generate usage reports so that I can analyze resource utilization. | - Exportable stats.<br>- Filters by department/time. | Low |
| US-011 | As a Maintenance Staff, I want to schedule resource maintenance so that downtime is marked. | - Resources blocked during maintenance.<br>- Notifications sent. | Low |
| US-012 | As an IT Support Staff, I want to search bookings and view audit logs so that I can troubleshoot issues. | - Full history accessible.<br>- Searchable by criteria. | Low |
| US-NF-001 | As a Security Auditor, I want user data encrypted with AES-256 so that compliance is met. | - Data encrypted at rest/transit.<br>- OWASP Top 10 compliant. | High |
| US-NF-002 | As a System User, I want pages to load in <2 seconds so that the experience is responsive. | - 95th percentile <2s for API responses.<br>- Tested with 1000 concurrent users. | High |

*Note: Stories US-001 to US-012 from FRs and use cases; US-NF-001/002 from NFRs. All are traceable to prior assignments.*

## 2. Product Backlog Creation

The product backlog prioritizes user stories using MoSCoW (Must-have: Core MVP features; Should-have: Important but not critical; Could-have: Nice-to-have; Won’t-have: Future phases). Effort estimates use story points (1-5, Fibonacci-like). Dependencies noted.

| Story ID | User Story | Priority (MoSCoW) | Effort Estimate | Dependencies |
|----------|------------|-------------------|-----------------|--------------|
| US-001 | As a Student, I want to log in... | Must-have | 3 | None |
| US-002 | As a Student, I want to browse resources... | Must-have | 3 | US-001 |
| US-003 | As a Student, I want to view a calendar... | Must-have | 2 | US-002 |
| US-004 | As a Student, I want to submit a booking... | Must-have | 5 | US-002, US-003 |
| US-005 | As a Resource Administrator, I want to view a dashboard... | Must-have | 3 | US-001 |
| US-006 | As a Resource Administrator, I want to approve or reject... | Must-have | 4 | US-005 |
| US-NF-001 | As a Security Auditor, I want user data encrypted... | Must-have | 5 | None |
| US-NF-002 | As a System User, I want pages to load in <2 seconds... | Must-have | 3 | None |
| US-007 | As a Student, I want to receive email notifications... | Should-have | 3 | US-006 |
| US-008 | As a Resource Administrator, I want to manage resources... | Should-have | 4 | US-001 |
| US-009 | As a Student, I want to view and edit my profile... | Should-have | 2 | US-001 |
| US-010 | As a University Administrator, I want to generate reports... | Could-have | 5 | US-012 |
| US-011 | As a Maintenance Staff, I want to schedule maintenance... | Could-have | 3 | US-008 |
| US-012 | As an IT Support Staff, I want to search bookings... | Won’t-have | 4 | US-001 |

**Justification:** Must-have stories align with core MVP (authentication, browsing, booking, approval) and critical NFRs (security, performance) to meet stakeholder success metrics (e.g., 90% booking success rate). Should-have adds usability; Could-have for analytics; Won’t-have deferred for initial release. Effort based on complexity (e.g., booking involves multiple validations).

## 3. Sprint Planning

**Sprint Goal:** Deliver core booking functionality for students and admins, enabling end-to-end booking workflow as MVP foundation. This contributes to MVP by providing basic CRUD for bookings, ensuring usability and security.

Selected Stories: US-001, US-002, US-003, US-004, US-005, US-006 (6 stories, total ~20 points, feasible in 2 weeks).

**Sprint Backlog:**

| Task ID | Task Description | Assigned To | Estimated Hours | Status |
|---------|------------------|-------------|-----------------|--------|
| T-001 | Implement authentication API (login/logout) | Developer | 8 | To Do |
| T-002 | Design and implement resource browsing UI with filters | Developer | 6 | To Do |
| T-003 | Develop calendar view component with availability logic | Developer | 6 | To Do |
| T-004 | Create booking submission form and validation | Developer | 8 | To Do |
| T-005 | Build admin dashboard for pending bookings | Developer | 6 | To Do |
| T-006 | Implement approval/rejection workflow with notifications | Developer | 8 | To Do |
| T-007 | Integrate security (encryption, RBAC) | Developer | 4 | To Do |
| T-008 | Optimize performance for <2s loads | Developer | 4 | To Do |
| T-009 | Write unit tests for all features | Tester | 6 | To Do |
| T-010 | Conduct integration testing and bug fixes | Tester | 4 | To Do |

**Sprint Goal Statement:** By end of sprint, users can register, browse, book, and admins can approve, forming a functional MVP. This aligns with Agile MVP principles, delivering value incrementally while ensuring security and performance.