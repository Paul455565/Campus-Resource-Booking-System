# System Requirements Document (SRD)

**Campus Resource Booking System**  
**Assignment 4 - Detailed Requirements**  
**Author:** PAULOSE MAJA  
**Date:** [18 March 2026] 
**Version:** 1.0 (Builds on Assignment 3 SPECIFICATION.md)

## 1. Introduction
This SRD defines detailed functional (FR) and non-functional requirements (NFR) for the Campus Resource Booking System, traceable to [Stakeholder Analysis](STAKEHOLDER_ANALYSIS.md). References Assignment 3: Digitizes booking for study rooms/projectors/lab computers; supports students/admins; React/Node/PostgreSQL stack.

**Traceability:** Each requirement links to stakeholder concerns.

## 2. Functional Requirements (12 Total)
FR stated as "The system shall...". Critical ones include acceptance criteria (AC).

1. **FR1: User Authentication** - Allow students/faculty to login/register. *Stakeholders: Students, Faculty*. AC: JWT tokens valid 24h; 2FA optional.
2. **FR2: Resource Browsing** - Display resources with filters (type, location, time). *AC: Real-time availability.*
3. **FR3: Calendar View** - Show availability matrix. *AC: Color-coded (free/busy/maintenance).*
4. **FR4: Booking Submission** - Submit requests with date/time/duration. *AC: Conflict check; pending status.*
5. **FR5: Admin Dashboard** - List pending/approved bookings. *Stakeholders: Admins.*
6. **FR6: Approval/Rejection** - Approve/reject with reasons/notes. *AC: Auto-notify on change.*
7. **FR7: Email Notifications** - Send on status change, reminders (24h prior). *AC: 95% delivery rate.*
8. **FR8: Resource Management** - Admins add/edit/delete resources. *Stakeholders: Admins, Maintenance.*
9. **FR9: User Profile** - View/edit personal bookings/history. *AC: Cancel own pending bookings.*
10. **FR10: Reports Generation** - Admins export usage stats. *Stakeholders: University Admins.*
11. **FR11: Maintenance Scheduling** - Mark downtime. *AC: Auto-block bookings.*
12. **FR12: Search & Audit Logs** - Search bookings; view logs. *Stakeholders: IT, Auditors.* *AC: Full history exportable.*

## 3. Non-Functional Requirements (10 Total)
Categorized by quality attributes.

### Usability (3)
- **NFR1:** Interface WCAG 2.1 AA compliant (accessible to screen readers).
- **NFR2:** Responsive design for mobile/desktop (Bootstrap/Tailwind).
- **NFR3:** Intuitive navigation; tooltips/help for all forms.

### Deployability (1)
- **NFR4:** Dockerized deployment on Windows/Linux; CI/CD via GitHub Actions.

### Maintainability (2)
- **NFR5:** 80% code coverage; full API docs (Swagger).
- **NFR6:** Modular monorepo structure; README for setup.

### Scalability (1)
- **NFR7:** Handle 1,000 concurrent users; auto-scale via cloud (future).

### Security (2)
- **NFR8:** AES-256 encryption for data; HTTPS enforced; RBAC.
- **NFR9:** OWASP Top 10 compliant; rate limiting.

### Performance (1)
- **NFR10:** <2s page loads; <500ms API responses (95th percentile).

**Verification:** All requirements testable/measurable; traceable to stakeholders.

