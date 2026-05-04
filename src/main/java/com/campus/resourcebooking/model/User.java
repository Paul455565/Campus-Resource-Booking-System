package com.campus.resourcebooking.model;

import com.campus.resourcebooking.enums.UserRole;
import com.campus.resourcebooking.enums.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * User class representing all system actors with role-based access
 */
public class User {
    private String userId;
    private String email;
    private String password; // hashed
    private String firstName;
    private String lastName;
    private UserRole role;
    private String department;
    private String phone;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor for new user registration
    public User(String email, String password, String firstName, String lastName, UserRole role) {
        this.userId = UUID.randomUUID().toString();
        this.email = email;
        this.password = password; // Should be hashed before storage
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Full constructor for loading from database
    public User(String userId, String email, String password, String firstName, String lastName,
                UserRole role, String department, String phone, UserStatus status,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.department = department;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Static factory method for registration
    public static User register(String email, String password, String firstName, String lastName, UserRole role) {
        if (!validateEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return new User(email, password, firstName, lastName, role);
    }

    // Authentication method (simplified - in real app would verify hash)
    public String login(String email, String password) {
        if (this.email.equals(email) && this.password.equals(password) && this.status == UserStatus.ACTIVE) {
            // Return JWT token (simplified)
            return "jwt_token_" + userId;
        }
        throw new IllegalArgumentException("Invalid credentials or inactive account");
    }

    public void updateProfile(String firstName, String lastName, String department, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!this.password.equals(oldPassword)) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
        this.password = newPassword; // Should be hashed
        this.updatedAt = LocalDateTime.now();
    }

    public void logout() {
        // In real implementation, invalidate JWT token
    }

    public void suspendAccount() {
        this.status = UserStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    public List<Booking> getBookingHistory() {
        // Implementation would query database
        return List.of(); // Placeholder
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isActive() {
        return status == UserStatus.ACTIVE;
    }

    public static boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getDepartment() { return department; }
    public String getPhone() { return phone; }
    public UserStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setDepartment(String department) { this.department = department; }
    public void setPhone(String phone) { this.phone = phone; }
}