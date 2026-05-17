package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.enums.UserRole;
import com.campus.resourcebooking.enums.UserStatus;
import com.campus.resourcebooking.model.User;
import com.campus.resourcebooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User operations
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "API for managing system users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Register a new user
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid registration data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<User> registerUser(
            @Parameter(description = "User email address")
            @RequestParam String email,
            @Parameter(description = "User password")
            @RequestParam String password,
            @Parameter(description = "User first name")
            @RequestParam String firstName,
            @Parameter(description = "User last name")
            @RequestParam String lastName,
            @Parameter(description = "User role")
            @RequestParam UserRole role) {
        try {
            User user = userService.registerUser(email, password, firstName, lastName, role);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Login user
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a session token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<String> loginUser(
            @Parameter(description = "User email address")
            @RequestParam String email,
            @Parameter(description = "User password")
            @RequestParam String password) {
        try {
            String token = userService.loginUser(email, password);
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    /**
     * Get user by ID
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get user details", description = "Retrieves information about a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> getUser(
            @Parameter(description = "User ID")
            @PathVariable String userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Get all users (admin only)
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users (admin only)")
    @ApiResponse(responseCode = "200", description = "List of users retrieved",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<User>> getAllUsers(
            @Parameter(description = "Filter by role (optional)")
            @RequestParam(required = false) UserRole role) {
        List<User> users = userService.getAllUsers(role);
        return ResponseEntity.ok(users);
    }

    /**
     * Update user profile
     */
    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Updates user profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User ID")
            @PathVariable String userId,
            @Parameter(description = "Updated first name")
            @RequestParam String firstName,
            @Parameter(description = "Updated last name")
            @RequestParam String lastName,
            @Parameter(description = "Updated department")
            @RequestParam String department,
            @Parameter(description = "Updated phone number")
            @RequestParam String phone) {
        try {
            User user = userService.updateUserProfile(userId, firstName, lastName, department, phone);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Change user password
     */
    @PostMapping("/{userId}/change-password")
    @Operation(summary = "Change user password", description = "Changes the password for a user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "401", description = "Current password is incorrect"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> changePassword(
            @Parameter(description = "User ID")
            @PathVariable String userId,
            @Parameter(description = "Current password")
            @RequestParam String oldPassword,
            @Parameter(description = "New password")
            @RequestParam String newPassword) {
        try {
            userService.changePassword(userId, oldPassword, newPassword);
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid current password");
        }
    }

    /**
     * Deactivate user account
     */
    @DeleteMapping("/{userId}")
    @Operation(summary = "Deactivate user account", description = "Deactivates a user account (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User account deactivated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deactivateUser(
            @Parameter(description = "User ID")
            @PathVariable String userId) {
        try {
            userService.deactivateUser(userId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
