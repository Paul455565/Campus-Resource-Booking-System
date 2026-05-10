package com.campus.resourcebooking.repositories.inmemory;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.repositories.BookingRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryBookingRepository extends InMemoryRepository<Booking, String> implements BookingRepository {
    public InMemoryBookingRepository() {
        super(Booking::getBookingId);
    }

    @Override
    public Optional<Booking> findByBookingId(String bookingId) {
        return findById(bookingId);
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        return findAll().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByResourceId(String resourceId) {
        return findAll().stream()
                .filter(booking -> booking.getResourceId().equals(resourceId))
                .collect(Collectors.toList());
    }
}
