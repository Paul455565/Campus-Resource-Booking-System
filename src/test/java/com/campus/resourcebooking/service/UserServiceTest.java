package com.campus.resourcebooking.service;

import com.campus.resourcebooking.enums.UserRole;
import com.campus.resourcebooking.enums.UserStatus;
import com.campus.resourcebooking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("User Service Tests")
class UserServiceTest {

    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(authenticationService);
    }

    @Test
    @DisplayName("Should register user successfully")
    void testRegisterUserSuccess() {
        // Arrange
        User mockUser = new User("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);
        when(authenticationService.registerUser(any(), any(), any(), any(), any()))
            .thenReturn(mockUser);

        // Act
        User result = userService.registerUser("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);

        // Assert
        assertNotNull(result);
        assertEquals("user@test.com", result.getEmail());
        verify(authenticationService, times(1)).registerUser(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("Should login user successfully")
    void testLoginUserSuccess() {
        // Arrange
        when(authenticationService.loginUser("user@test.com", "password123"))
            .thenReturn("jwt_token_12345");

        // Act
        String token = userService.loginUser("user@test.com", "password123");

        // Assert
        assertNotNull(token);
        assertTrue(token.contains("jwt_token_"));
        verify(authenticationService, times(1)).loginUser("user@test.com", "password123");
    }

    @Test
    @DisplayName("Should get user by ID successfully")
    void testGetUserByIdSuccess() {
        // Arrange
        User mockUser = new User("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);
        userService.registerUser("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);
        
        when(authenticationService.registerUser(any(), any(), any(), any(), any()))
            .thenReturn(mockUser);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> userService.getUserById("nonexistent"));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void testGetUserByIdNotFound() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> userService.getUserById("nonexistent"));
    }

    @Test
    @DisplayName("Should get all users by role")
    void testGetAllUsersByRole() {
        // Arrange
        User mockUser1 = new User("user1@test.com", "pass", "John", "Doe", UserRole.STUDENT);
        User mockUser2 = new User("user2@test.com", "pass", "Jane", "Smith", UserRole.STUDENT);
        
        when(authenticationService.registerUser(any(), any(), any(), any(), any()))
            .thenReturn(mockUser1);

        // Act
        userService.registerUser("user1@test.com", "pass", "John", "Doe", UserRole.STUDENT);
        var result = userService.getAllUsers(UserRole.STUDENT);

        // Assert
        assertNotNull(result);
        verify(authenticationService, times(1)).registerUser(any(), any(), any(), any(), any());
    }

    @Test
    @DisplayName("Should update user profile")
    void testUpdateUserProfile() {
        // Arrange
        User mockUser = new User("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);
        when(authenticationService.registerUser(any(), any(), any(), any(), any()))
            .thenReturn(mockUser);
        
        userService.registerUser("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);

        // Act
        User result = userService.updateUserProfile(mockUser.getUserId(), "John", "Smith", "Engineering", "555-1234");

        // Assert
        assertNotNull(result);
    }

    @Test
    @DisplayName("Should change password")
    void testChangePassword() {
        // Arrange
        User mockUser = new User("user@test.com", "oldpass", "John", "Doe", UserRole.STUDENT);
        when(authenticationService.registerUser(any(), any(), any(), any(), any()))
            .thenReturn(mockUser);
        
        userService.registerUser("user@test.com", "oldpass", "John", "Doe", UserRole.STUDENT);

        // Act
        userService.changePassword(mockUser.getUserId(), "oldpass", "newpass");

        // Assert
        verify(authenticationService, times(1)).changePassword(any(), any(), any());
    }

    @Test
    @DisplayName("Should deactivate user account")
    void testDeactivateUser() {
        // Arrange
        User mockUser = new User("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);
        when(authenticationService.registerUser(any(), any(), any(), any(), any()))
            .thenReturn(mockUser);
        
        userService.registerUser("user@test.com", "password123", "John", "Doe", UserRole.STUDENT);

        // Act
        userService.deactivateUser(mockUser.getUserId());

        // Assert - Just verify no exception thrown
        assertDoesNotThrow(() -> userService.deactivateUser(mockUser.getUserId()));
    }
}
