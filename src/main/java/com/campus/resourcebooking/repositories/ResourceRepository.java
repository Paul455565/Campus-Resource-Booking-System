package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.model.Resource;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository extends Repository<Resource, String> {
    Optional<Resource> findByResourceId(String resourceId);
    List<Resource> findByAvailability(ResourceAvailability availability);
}
