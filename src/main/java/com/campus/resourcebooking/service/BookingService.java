package com.campus.resourcebooking.service;

import com.campus.resourcebooking.enums.ApprovalDecision;
import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.model.User;
import com.campus.resourcebooking.repositories.BookingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing booking operations
 */
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ResourceService resourceService;
    private final NotificationService notificationService;

    public BookingService(BookingRepository bookingRepository,
                          ResourceService resourceService,
                          NotificationService notificationService) {
        this.bookingRepository = bookingRepository;
        this.resourceService = resourceService;
        this.notificationService = notificationService;
    }

    public Booking createBooking(User user, Resource resource, LocalDateTime startDate,
                                LocalDateTime endDate) {
        if (!resourceService.checkResourceAvailability(resource.getResourceId(), startDate, endDate)) {
            throw new IllegalArgumentException("Resource not available for the requested time");
        }

        Booking booking = new Booking(user.getUserId(), resource.getResourceId(),
                                      startDate, endDate, "Business purpose");
        bookingRepository.save(booking);
        return booking;
    }

    public void cancelBooking(String bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingId(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }

        Booking booking = optionalBooking.get();
        booking.cancelBooking();
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getBookingsByResource(String resourceId) {
        return bookingRepository.findByResourceId(resourceId);
    }

    public boolean checkConflicts(String resourceId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingRepository.findByResourceId(resourceId).stream()
                .anyMatch(booking -> booking.getStartDate().isBefore(endDate)
                        && booking.getEndDate().isAfter(startDate));
    }

    public void approveBooking(String bookingId, User admin, String conditions) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingId(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }

        Booking booking = optionalBooking.get();
        booking.updateStatus(ApprovalDecision.APPROVED);
        bookingRepository.save(booking);
    }

    public void rejectBooking(String bookingId, User admin, String reason) {
        Optional<Booking> optionalBooking = bookingRepository.findByBookingId(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }

        Booking booking = optionalBooking.get();
        booking.updateStatus(ApprovalDecision.REJECTED);
        bookingRepository.save(booking);
    }
}
