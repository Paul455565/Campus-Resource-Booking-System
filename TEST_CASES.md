# Test Cases

**Campus Resource Booking System**  
**Assignment 5 - Test Case Development**  
**Author:** PAULOSE MAJA  
**Date:** March 30, 2026  

## Functional Test Cases

| Test Case ID | Requirement ID | Description | Steps | Expected Result | Actual Result | Status |
|--------------|----------------|-------------|-------|-----------------|---------------|--------|
| TC-001 | FR-1 | User Authentication - Valid Login | 1. Navigate to login page. 2. Enter valid username/password. 3. Click login. | User logged in, JWT token generated, redirected to dashboard. |  |  |
| TC-002 | FR-2 | Resource Browsing - Filter by Type | 1. Login as student. 2. Go to browse page. 3. Select filter "Study Room". 4. Click search. | List of study rooms displayed with availability. |  |  |
| TC-003 | FR-3 | Calendar View - Display Availability | 1. Login as student. 2. Select a resource. 3. View calendar. | Calendar shows free/busy slots color-coded. |  |  |
| TC-004 | FR-4 | Booking Submission - Valid Booking | 1. Login as student. 2. Browse and select resource/time. 3. Submit booking. | Booking created with pending status, confirmation sent. |  |  |
| TC-005 | FR-5 | Admin Dashboard - View Pending | 1. Login as admin. 2. Access dashboard. | List of pending bookings shown. |  |  |
| TC-006 | FR-6 | Approval/Rejection - Approve Booking | 1. Login as admin. 2. Select pending booking. 3. Click approve. | Status updated to approved, email sent to user. |  |  |
| TC-007 | FR-7 | Email Notifications - Status Change | 1. Trigger approval (from TC-006). | User receives email with status update. |  |  |
| TC-008 | FR-8 | Resource Management - Add Resource | 1. Login as admin. 2. Go to manage resources. 3. Add new resource details. 4. Save. | Resource added to database, appears in browse. |  |  |

## Non-Functional Test Cases

| Test Case ID | Requirement ID | Description | Steps | Expected Result | Actual Result | Status |
|--------------|----------------|-------------|-------|-----------------|---------------|--------|
| TC-NF-001 | NFR-10 | Performance - Page Load Time | 1. Simulate 1000 concurrent users browsing resources. 2. Measure response time. | Page loads in <2 seconds for 95th percentile. |  |  |
| TC-NF-002 | NFR-8 | Security - Unauthorized Access | 1. Attempt to access admin dashboard without login. 2. Try to approve booking without admin role. | Access denied, 403 error returned. |  |  |</content>
<parameter name="filePath">c:\Users\tania\OneDrive\Desktop\Campus Resource Booking System\TEST_CASES.md