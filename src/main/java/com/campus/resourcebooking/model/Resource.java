package com.campus.resourcebooking.model;

import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.enums.ResourceAvailability;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Resource class modeling physical assets available for booking
 */
public class Resource {
    private String resourceId;
    private String name;
    private String description;
    private ResourceType type;
    private String location;
    private int capacity;
    private ResourceAvailability availability;
    private List<LocalDateTime> maintenanceSchedule;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor for new resource
    public Resource(String name, String description, ResourceType type, String location, int capacity) {
        this.resourceId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.capacity = capacity;
        this.availability = ResourceAvailability.AVAILABLE;
        this.maintenanceSchedule = List.of();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Full constructor
    public Resource(String resourceId, String name, String description, ResourceType type,
                   String location, int capacity, ResourceAvailability availability,
                   List<LocalDateTime> maintenanceSchedule, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.resourceId = resourceId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.location = location;
        this.capacity = capacity;
        this.availability = availability;
        this.maintenanceSchedule = maintenanceSchedule;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Static factory method
    public static Resource addResource(String name, ResourceType type, String location, int capacity) {
        return new Resource(name, "", type, location, capacity);
    }

    public void editResource(String name, String description, int capacity) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.updatedAt = LocalDateTime.now();
    }

    public void deleteResource() {
        this.availability = ResourceAvailability.RETIRED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean checkAvailability(LocalDateTime date, LocalDateTime time, int duration) {
        // Simplified availability check
        return this.availability == ResourceAvailability.AVAILABLE;
    }

    public void markMaintenance(LocalDateTime startDate, LocalDateTime endDate) {
        this.availability = ResourceAvailability.MAINTENANCE;
        this.maintenanceSchedule = List.of(startDate, endDate);
        this.updatedAt = LocalDateTime.now();
    }

    public List<Booking> getBookingHistory() {
        // Implementation would query database
        return List.of();
    }

    public double getUtilizationRate() {
        // Simplified calculation
        return 0.75; // 75% utilization
    }

    public void updateAvailability(ResourceAvailability status) {
        this.availability = status;
        this.updatedAt = LocalDateTime.now();
    }

    public List<Booking> getBookingsInTimeRange(LocalDateTime startDate, LocalDateTime endDate) {
        // Implementation would query database
        return List.of();
    }

    // Getters and setters
    public String getResourceId() { return resourceId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ResourceType getType() { return type; }
    public String getLocation() { return location; }
    public int getCapacity() { return capacity; }
    public ResourceAvailability getAvailability() { return availability; }
    public List<LocalDateTime> getMaintenanceSchedule() { return maintenanceSchedule; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setDescription(String description) { this.description = description; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}