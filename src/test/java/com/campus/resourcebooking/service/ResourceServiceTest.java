package com.campus.resourcebooking.service;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.repositories.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Resource Service Tests")
class ResourceServiceTest {

    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private MaintenanceService maintenanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceService = new ResourceService(resourceRepository, maintenanceService);
    }

    @Test
    @DisplayName("Should retrieve available resources")
    void testGetAvailableResources() {
        // Arrange
        List<Resource> availableResources = List.of(
            new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30),
            new Resource("Meeting Room 1", "Conference Room", ResourceType.MEETING_ROOM, "Building B", 15)
        );
        
        when(resourceRepository.findByAvailability(ResourceAvailability.AVAILABLE))
            .thenReturn(availableResources);

        // Act
        List<Resource> result = resourceService.getAvailableResources(LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        // Assert
        assertEquals(2, result.size());
        verify(resourceRepository, times(1)).findByAvailability(ResourceAvailability.AVAILABLE);
    }

    @Test
    @DisplayName("Should retrieve resource by ID successfully")
    void testGetResourceByIdSuccess() {
        // Arrange
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        when(resourceRepository.findByResourceId(resource.getResourceId())).thenReturn(Optional.of(resource));

        // Act
        Resource result = resourceService.getResourceById(resource.getResourceId());

        // Assert
        assertNotNull(result);
        assertEquals("Lab A", result.getName());
        verify(resourceRepository, times(1)).findByResourceId(resource.getResourceId());
    }

    @Test
    @DisplayName("Should throw exception when resource not found")
    void testGetResourceByIdNotFound() {
        // Arrange
        when(resourceRepository.findByResourceId("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, 
            () -> resourceService.getResourceById("nonexistent"));
    }

    @Test
    @DisplayName("Should check resource availability successfully")
    void testCheckResourceAvailability() {
        // Arrange
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        when(resourceRepository.findByResourceId(resource.getResourceId())).thenReturn(Optional.of(resource));

        // Act
        boolean available = resourceService.checkResourceAvailability(resource.getResourceId(), 
            LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        // Assert
        assertTrue(available);
    }

    @Test
    @DisplayName("Should update resource availability")
    void testUpdateResourceAvailability() {
        // Arrange
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        when(resourceRepository.findByResourceId(resource.getResourceId())).thenReturn(Optional.of(resource));
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);

        // Act
        resourceService.updateResourceAvailability(resource.getResourceId(), ResourceAvailability.MAINTENANCE);

        // Assert
        verify(resourceRepository, times(1)).save(resource);
    }

    @Test
    @DisplayName("Should get resource utilization")
    void testGetResourceUtilization() {
        // Arrange
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        when(resourceRepository.findByResourceId(resource.getResourceId())).thenReturn(Optional.of(resource));

        // Act
        Object utilization = resourceService.getResourceUtilization(resource.getResourceId(), "MONTHLY");

        // Assert
        assertNotNull(utilization);
    }

    @Test
    @DisplayName("Should mark resource for maintenance")
    void testMarkMaintenance() {
        // Arrange
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = startDate.plusHours(4);

        // Act
        resource.markMaintenance(startDate, endDate);

        // Assert
        assertEquals(ResourceAvailability.MAINTENANCE, resource.getAvailability());
    }

    @Test
    @DisplayName("Should delete resource (mark as retired)")
    void testDeleteResource() {
        // Arrange
        Resource resource = new Resource("Lab A", "Computer Lab", ResourceType.LABORATORY, "Building A", 30);

        // Act
        resource.deleteResource();

        // Assert
        assertEquals(ResourceAvailability.RETIRED, resource.getAvailability());
    }
}
