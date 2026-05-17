package com.campus.resourcebooking.service;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.model.User;
import com.campus.resourcebooking.repositories.BookingRepository;
import com.campus.resourcebooking.enums.BookingStatus;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Booking Service Tests")
class BookingServiceTest {

    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ResourceService resourceService;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookingService = new BookingService(bookingRepository, resourceService, notificationService);
    }

    @Test
    @DisplayName("Should create booking successfully when resource is available")
    void testCreateBookingSuccess() {
        // Arrange
        User user = new User("user@test.com", "pass123", "John", "Doe", UserRole.STUDENT);
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = startDate.plusHours(2);

        when(resourceService.checkResourceAvailability(anyString(), any(), any())).thenReturn(true);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Booking booking = bookingService.createBooking(user, resource, startDate, endDate);

        // Assert
        assertNotNull(booking);
        assertEquals(user.getUserId(), booking.getUserId());
        assertEquals(resource.getResourceId(), booking.getResourceId());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    @DisplayName("Should throw exception when resource is not available")
    void testCreateBookingResourceNotAvailable() {
        // Arrange
        User user = new User("user@test.com", "pass123", "John", "Doe", UserRole.STUDENT);
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = startDate.plusHours(2);

        when(resourceService.checkResourceAvailability(anyString(), any(), any())).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> bookingService.createBooking(user, resource, startDate, endDate));
    }

    @Test
    @DisplayName("Should cancel booking successfully")
    void testCancelBookingSuccess() {
        // Arrange
        Booking booking = new Booking("user1", "resource1", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Test");
        booking.bookingId = "booking123";
        
        when(bookingRepository.findByBookingId("booking123")).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        bookingService.cancelBooking("booking123");

        // Assert
        assertEquals(BookingStatus.CANCELLED, booking.getStatus());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    @DisplayName("Should throw exception when cancelling non-existent booking")
    void testCancelNonExistentBooking() {
        // Arrange
        when(bookingRepository.findByBookingId("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> bookingService.cancelBooking("nonexistent"));
    }

    @Test
    @DisplayName("Should detect booking conflicts")
    void testCheckConflicts() {
        // Arrange
        Booking existingBooking = new Booking("user1", "resource1", 
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), "Test");
        
        when(bookingRepository.findByResourceId("resource1"))
            .thenReturn(java.util.List.of(existingBooking));

        // Act
        boolean hasConflicts = bookingService.checkConflicts("resource1", 
            LocalDateTime.now().plusDays(1).plusHours(1), 
            LocalDateTime.now().plusDays(1).plusHours(3));

        // Assert
        assertTrue(hasConflicts);
    }

    @Test
    @DisplayName("Should not detect conflicts when bookings don't overlap")
    void testNoConflicts() {
        // Arrange
        Booking existingBooking = new Booking("user1", "resource1", 
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), "Test");
        
        when(bookingRepository.findByResourceId("resource1"))
            .thenReturn(java.util.List.of(existingBooking));

        // Act
        boolean hasConflicts = bookingService.checkConflicts("resource1", 
            LocalDateTime.now().plusDays(2), 
            LocalDateTime.now().plusDays(2).plusHours(1));

        // Assert
        assertFalse(hasConflicts);
    }

    @Test
    @DisplayName("Should retrieve user bookings successfully")
    void testGetUserBookingsSuccess() {
        // Arrange
        java.util.List<Booking> userBookings = java.util.List.of(
            new Booking("user1", "resource1", LocalDateTime.now(), LocalDateTime.now().plusHours(2), "Test1"),
            new Booking("user1", "resource2", LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(2), "Test2")
        );
        
        when(bookingRepository.findByUserId("user1")).thenReturn(userBookings);

        // Act
        java.util.List<Booking> result = bookingService.getBookingsByUser("user1");

        // Assert
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findByUserId("user1");
    }
}
