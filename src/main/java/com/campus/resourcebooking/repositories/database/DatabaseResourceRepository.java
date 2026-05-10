package com.campus.resourcebooking.repositories.database;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.repositories.ResourceRepository;
import java.util.List;
import java.util.Optional;

/**
 * Stub for a future database-backed resource repository.
 */
public class DatabaseResourceRepository implements ResourceRepository {
    @Override
    public Resource save(Resource entity) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public Optional<Resource> findById(String id) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public List<Resource> findAll() {
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
    public Optional<Resource> findByResourceId(String resourceId) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }

    @Override
    public List<Resource> findByAvailability(ResourceAvailability availability) {
        throw new UnsupportedOperationException("Database storage is not yet implemented");
    }
}
