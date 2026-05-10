package com.campus.resourcebooking.factories;

import com.campus.resourcebooking.repositories.BookingRepository;
import com.campus.resourcebooking.repositories.ResourceRepository;
import com.campus.resourcebooking.repositories.database.DatabaseBookingRepository;
import com.campus.resourcebooking.repositories.database.DatabaseResourceRepository;
import com.campus.resourcebooking.repositories.inmemory.InMemoryBookingRepository;
import com.campus.resourcebooking.repositories.inmemory.InMemoryResourceRepository;

public final class RepositoryFactory {
    private RepositoryFactory() {
        // Utility class
    }

    public static BookingRepository getBookingRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryBookingRepository();
            case "DATABASE":
                return new DatabaseBookingRepository();
            default:
                throw new IllegalArgumentException("Invalid storage type: " + storageType);
        }
    }

    public static ResourceRepository getResourceRepository(String storageType) {
        switch (storageType.toUpperCase()) {
            case "MEMORY":
                return new InMemoryResourceRepository();
            case "DATABASE":
                return new DatabaseResourceRepository();
            default:
                throw new IllegalArgumentException("Invalid storage type: " + storageType);
        }
    }
}
