package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.repositories.inmemory.InMemoryResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceRepositoryTest {
    private ResourceRepository resourceRepository;

    @BeforeEach
    public void setUp() {
        resourceRepository = new InMemoryResourceRepository();
    }

    @Test
    public void saveAndFindById_shouldPersistResource() {
        Resource resource = new Resource("Conference Room A", "Large room", ResourceType.ROOM, "Building 1", 20);
        resourceRepository.save(resource);

        assertTrue(resourceRepository.findById(resource.getResourceId()).isPresent());
        assertEquals(resource.getName(), resourceRepository.findById(resource.getResourceId()).get().getName());
    }

    @Test
    public void findByAvailability_shouldReturnAvailableResources() {
        Resource resource1 = new Resource("Projector 1", "HD projector", ResourceType.EQUIPMENT, "Building 2", 1);
        Resource resource2 = new Resource("Lab 2", "Computer lab", ResourceType.COMPUTER_LAB, "Building 3", 30);
        resourceRepository.save(resource1);
        resourceRepository.save(resource2);

        List<Resource> available = resourceRepository.findByAvailability(ResourceAvailability.AVAILABLE);
        assertEquals(2, available.size());
    }

    @Test
    public void deleteById_shouldRemoveResource() {
        Resource resource = new Resource("Seminar Room", "Training room", ResourceType.ROOM, "Building 1", 15);
        resourceRepository.save(resource);
        resourceRepository.deleteById(resource.getResourceId());

        assertFalse(resourceRepository.findById(resource.getResourceId()).isPresent());
        assertFalse(resourceRepository.existsById(resource.getResourceId()));
    }

    @Test
    public void findAll_shouldReturnAllResources() {
        Resource resource1 = new Resource("Desk 1", "Study desk", ResourceType.EQUIPMENT, "Building 4", 1);
        Resource resource2 = new Resource("Meeting Room", "Small meeting room", ResourceType.ROOM, "Building 5", 10);
        resourceRepository.save(resource1);
        resourceRepository.save(resource2);

        assertEquals(2, resourceRepository.findAll().size());
    }
}
