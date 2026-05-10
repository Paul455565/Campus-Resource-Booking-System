package com.campus.resourcebooking.service;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.repositories.ResourceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing resources
 */
public class ResourceService {
    private final ResourceRepository resourceRepository;
    private final MaintenanceService maintenanceService;

    public ResourceService(ResourceRepository resourceRepository, MaintenanceService maintenanceService) {
        this.resourceRepository = resourceRepository;
        this.maintenanceService = maintenanceService;
    }

    public List<Resource> getAvailableResources(LocalDateTime date, LocalDateTime time) {
        return resourceRepository.findByAvailability(ResourceAvailability.AVAILABLE);
    }

    public Resource getResourceById(String resourceId) {
        Optional<Resource> resource = resourceRepository.findByResourceId(resourceId);
        return resource.orElseThrow(() -> new IllegalArgumentException("Resource not found: " + resourceId));
    }

    public boolean checkResourceAvailability(String resourceId, LocalDateTime startDate, LocalDateTime endDate) {
        Resource resource = getResourceById(resourceId);
        return resource.checkAvailability(startDate, endDate, null);
    }

    public void updateResourceAvailability(String resourceId, ResourceAvailability status) {
        Resource resource = getResourceById(resourceId);
        resource.updateAvailability(status);
        resourceRepository.save(resource);
    }

    public Object getResourceUtilization(String resourceId, Object period) {
        // Implementation would calculate utilization statistics
        return "75% utilization";
    }
}