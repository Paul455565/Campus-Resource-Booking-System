package com.campus.resourcebooking.service;

import com.campus.resourcebooking.enums.UserRole;
import com.campus.resourcebooking.enums.UserStatus;
import com.campus.resourcebooking.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user operations
 */
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final AuthenticationService authenticationService;

    public UserService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public User registerUser(String email, String password, String firstName,
                            String lastName, UserRole role) {
        User user = authenticationService.registerUser(email, password, firstName, lastName, role);
        users.add(user);
        return user;
    }

    public String loginUser(String email, String password) {
        return authenticationService.loginUser(email, password);
    }

    public User getUserById(String userId) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
        return user.orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    public List<User> getAllUsers(UserRole role) {
        if (role == null) {
            return new ArrayList<>(users);
        }
        return users.stream()
                .filter(u -> u.getRole() == role)
                .toList();
    }

    public User updateUserProfile(String userId, String firstName, String lastName,
                                 String department, String phone) {
        User user = getUserById(userId);
        user.updateProfile(firstName, lastName, department, phone);
        return user;
    }

    public void changePassword(String userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        authenticationService.changePassword(userId, oldPassword, newPassword);
    }

    public void deactivateUser(String userId) {
        User user = getUserById(userId);
        user.deactivate();
    }
}
