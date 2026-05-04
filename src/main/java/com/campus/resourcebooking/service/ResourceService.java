package com.campus.resourcebooking.service;

import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.enums.ResourceAvailability;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing resources
 */
public class ResourceService {
    private final Object resourceRepository; // Would be a proper repository interface
    private final MaintenanceService maintenanceService;

    public ResourceService(MaintenanceService maintenanceService) {
        this.resourceRepository = null;
        this.maintenanceService = maintenanceService;
    }

    public List<Resource> getAvailableResources(LocalDateTime date, LocalDateTime time) {
        // Implementation would query repository for available resources
        return List.of(
            new Resource("Conference Room A", "Large conference room", ResourceType.ROOM, "Building 1", 20),
            new Resource("Projector 1", "HD Projector", ResourceType.PROJECTOR, "Building 2", 1)
        );
    }

    public Resource getResourceById(String resourceId) {
        // Implementation would query repository
        return new Resource("Conference Room A", "Large conference room", ResourceType.ROOM, "Building 1", 20);
    }

    public boolean checkResourceAvailability(String resourceId, LocalDateTime startDate, LocalDateTime endDate) {
        Resource resource = getResourceById(resourceId);
        return resource.checkAvailability(startDate, endDate, null);
    }

    public void updateResourceAvailability(String resourceId, ResourceAvailability status) {
        Resource resource = getResourceById(resourceId);
        resource.updateAvailability(status);
    }

    public Object getResourceUtilization(String resourceId, Object period) {
        // Implementation would calculate utilization statistics
        return "75% utilization";
    }
}