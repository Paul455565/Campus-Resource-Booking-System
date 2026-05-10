package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.model.Booking;
import com.campus.resourcebooking.repositories.inmemory.InMemoryBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingRepositoryTest {
    private BookingRepository bookingRepository;

    @BeforeEach
    public void setUp() {
        bookingRepository = new InMemoryBookingRepository();
    }

    @Test
    public void saveAndFindById_shouldPersistBooking() {
        Booking booking = new Booking("user-1", "resource-1", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Study session");
        bookingRepository.save(booking);

        assertTrue(bookingRepository.findById(booking.getBookingId()).isPresent());
        assertEquals(booking.getUserId(), bookingRepository.findById(booking.getBookingId()).get().getUserId());
    }

    @Test
    public void findByUserId_shouldReturnAllBookingsForUser() {
        Booking booking1 = new Booking("user-2", "resource-2", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Meeting");
        Booking booking2 = new Booking("user-2", "resource-3", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), "Presentation");
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        List<Booking> bookings = bookingRepository.findByUserId("user-2");
        assertEquals(2, bookings.size());
    }

    @Test
    public void deleteById_shouldRemoveBooking() {
        Booking booking = new Booking("user-3", "resource-4", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Lecture");
        bookingRepository.save(booking);
        bookingRepository.deleteById(booking.getBookingId());

        assertFalse(bookingRepository.findById(booking.getBookingId()).isPresent());
        assertFalse(bookingRepository.existsById(booking.getBookingId()));
    }

    @Test
    public void findAll_shouldReturnAllBookings() {
        Booking booking1 = new Booking("user-4", "resource-5", LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Seminar");
        Booking booking2 = new Booking("user-5", "resource-6", LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(3), "Workshop");
        bookingRepository.save(booking1);
        bookingRepository.save(booking2);

        assertEquals(2, bookingRepository.findAll().size());
    }
}
