package com.campus.resourcebooking.repositories.inmemory;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.repositories.ResourceRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryResourceRepository extends InMemoryRepository<Resource, String> implements ResourceRepository {
    public InMemoryResourceRepository() {
        super(Resource::getResourceId);
    }

    @Override
    public Optional<Resource> findByResourceId(String resourceId) {
        return findById(resourceId);
    }

    @Override
    public List<Resource> findByAvailability(ResourceAvailability availability) {
        return findAll().stream()
                .filter(resource -> resource.getAvailability() == availability)
                .collect(Collectors.toList());
    }
}
