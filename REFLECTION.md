# Reflection: Challenges in Balancing Stakeholder Needs

**Campus Resource Booking System**  
**Assignment 4 - Reflection**  
**Author:** PAULOSE MAJA  
**Date:** [Current Date]

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

