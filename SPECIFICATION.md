# System Specification Document

**Project Title:** Campus Resource Booking System  
**Author:** PAULOSE MAJA 
**Date:** 10 MARCH, 2026 

## 1. Introduction

### 1.1 Domain
**University / Education Domain**  
Modern universities manage shared resources (study rooms, projectors, lab computers) manually via email or physical sign-ups. This leads to inefficiencies like double-bookings, no-shows, and administrative overload. The system digitizes this for a typical mid-sized campus (e.g., 5,000 students, 50 resources).

### 1.2 Problem Statement
Students struggle to access resources reliably, while admins spend excessive time coordinating. **Purpose:** Provide a streamlined, digital booking platform ensuring fair allocation, real-time availability, and instant notifications.

### 1.3 Individual Scope & Feasibility Justification
**Scope:** Full end-to-end booking workflow for 2 user roles (Student/Admin), supporting 1,000 concurrent users, <2s response time, 99% uptime.  
**Why Feasible Individually:** 
- Simple CRUD operations + basic auth/workflow.
- Modular: UI/API/DB separated.
- No advanced features (AI/ML/real-time chat).
- Prototype testable in 1 semester using standard stack (React/Node/PostgreSQL).
- Estimated Effort: 150 hours (docs: 20h, dev: 100h, test: 30h).

## 2. Functional Requirements
- FR1: Student login/browse resources/booking.
- FR2: Admin approve/reject with reasons.
- FR3: Email notifications on status change.
- FR4: Calendar view (availability).

## 3. Non-Functional Requirements
- Scalable to 5k users.
- Secure (JWT auth).
- Accessible (WCAG 2.1).

## 4. Assumptions & Risks
- Internet access required.
- Risk: Double-booking → Mitigation: DB locking.

