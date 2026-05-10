package com.campus.resourcebooking.repositories.database;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.repositories.BookingRepository;
import java.util.List;
import java.util.Optional;

/**
 * Stub for a future database-backed booking repository.
 */
public class DatabaseBookingRepository implements BookingRepository {
    @Override
    public Booking save(Booking entity) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public Optional<Booking> findById(String id) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public List<Booking> findAll() {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public void deleteById(String id) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public boolean existsById(String id) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public List<Booking> findByUserId(String userId) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public List<Booking> findByResourceId(String resourceId) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public Optional<Booking> findByBookingId(String bookingId) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }
}
