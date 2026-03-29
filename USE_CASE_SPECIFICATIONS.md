# Use Case Specifications

**Campus Resource Booking System**  
**Assignment 5 - Use Case Specifications**  
**Author:** PAULOSE MAJA  
**Date:** March 30, 2026  

## 1. Use Case: Login
**Actor:** Student, Faculty, Resource Administrator, University Administrator, Maintenance Staff, IT Support Staff, Security Auditor  
**Description:** Purpose: Authenticate users to access the system. Scope: Covers login and optional registration for new users.  
**Preconditions:** User has valid credentials (username/password); system is accessible.  
**Postconditions:** User is authenticated with a valid session (JWT token).  
**Basic Flow:**  
1. User enters username and password on login page.  
2. System validates credentials against the database.  
3. System generates and returns a JWT token for session management.  
4. User is redirected to the dashboard or home page.  
**Alternative Flows:**  
- If credentials are invalid: Display error message "Invalid username or password."  
- If account is locked (e.g., after failed attempts): Display "Account locked. Contact support."  
- For new users: Option to register, which creates a new account and proceeds to login.

## 2. Use Case: Browse Resources
**Actor:** Student, Faculty  
**Description:** Purpose: Allow users to view available resources with filters. Scope: Displays list of resources with real-time availability.  
**Preconditions:** User is logged in.  
**Postconditions:** User sees filtered list of resources.  
**Basic Flow:**  
1. User selects filters (type, location, time).  
2. System queries database for matching resources.  
3. System displays resources with availability status.  
**Alternative Flows:**  
- If no resources match: Display "No resources found. Try different filters."  
- If system error: Display "Unable to load resources. Try again later."

## 3. Use Case: Submit Booking
**Actor:** Student, Faculty  
**Description:** Purpose: Submit a booking request for a resource. Scope: Includes conflict check and sets status to pending.  
**Preconditions:** User is logged in; resource is available for selected time.  
**Postconditions:** Booking is created with pending status; notification sent if applicable.  
**Basic Flow:**  
1. User selects resource and enters date/time/duration.  
2. System checks for conflicts.  
3. System creates booking with pending status.  
4. System sends confirmation email.  
**Alternative Flows:**  
- If conflict detected: Display "Resource unavailable. Choose different time."  
- If invalid input: Display "Please enter valid date/time."

## 4. Use Case: Approve/Reject Booking
**Actor:** Resource Administrator  
**Description:** Purpose: Review and decide on pending bookings. Scope: Updates status and notifies user.  
**Preconditions:** Admin is logged in; there are pending bookings.  
**Postconditions:** Booking status updated; user notified via email.  
**Basic Flow:**  
1. Admin views pending bookings on dashboard.  
2. Admin selects booking and chooses approve/reject with notes.  
3. System updates booking status.  
4. System sends notification email to user.  
**Alternative Flows:**  
- If rejection: Require reason; update status to rejected.  
- If system error: Display "Update failed. Try again."

## 5. Use Case: Manage Resources
**Actor:** Resource Administrator  
**Description:** Purpose: Add, edit, or delete resources. Scope: Maintains resource inventory.  
**Preconditions:** Admin is logged in.  
**Postconditions:** Resource database updated.  
**Basic Flow:**  
1. Admin selects add/edit/delete action.  
2. Admin enters/updates resource details.  
3. System validates and saves changes.  
**Alternative Flows:**  
- If validation fails: Display "Invalid details. Check required fields."  
- If resource in use: Prevent deletion; display warning.

## 6. Use Case: View Profile
**Actor:** Student, Faculty  
**Description:** Purpose: View and edit personal information and bookings. Scope: Includes booking history and cancellation.  
**Preconditions:** User is logged in.  
**Postconditions:** Profile updated if edited.  
**Basic Flow:**  
1. User accesses profile page.  
2. System displays user info and booking history.  
3. User edits details or cancels pending bookings.  
4. System saves changes.  
**Alternative Flows:**  
- If cancellation: Confirm action; update status.  
- If edit fails: Display "Update failed."

## 7. Use Case: Generate Reports
**Actor:** University Administrator  
**Description:** Purpose: Export usage statistics. Scope: Filters and exports data.  
**Preconditions:** Admin is logged in.  
**Postconditions:** Report file generated/downloaded.  
**Basic Flow:**  
1. Admin selects report type and filters.  
2. System queries database.  
3. System generates and downloads report.  
**Alternative Flows:**  
- If no data: Display "No data for selected filters."  
- If export fails: Display "Report generation failed."

## 8. Use Case: Schedule Maintenance
**Actor:** Resource Administrator, Maintenance Staff  
**Description:** Purpose: Mark resources for downtime. Scope: Blocks bookings during maintenance.  
**Preconditions:** Admin/Staff is logged in.  
**Postconditions:** Resource marked unavailable; bookings blocked.  
**Basic Flow:**  
1. User selects resource and maintenance period.  
2. System updates resource status to maintenance.  
3. System blocks new bookings for that period.  
**Alternative Flows:**  
- If overlapping bookings: Cancel or notify affected users.  
- If invalid period: Display "Invalid maintenance schedule."</content>
<parameter name="filePath">c:\Users\tania\OneDrive\Desktop\Campus Resource Booking System\USE_CASE_SPECIFICATIONS.md