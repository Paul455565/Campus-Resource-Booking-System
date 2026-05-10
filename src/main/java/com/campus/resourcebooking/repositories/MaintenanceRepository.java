package com.campus.resourcebooking.repositories;

import com.campus.resourcebooking.model.Maintenance;
import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends Repository<Maintenance, String> {
    List<Maintenance> findByResourceId(String resourceId);
    Optional<Maintenance> findByMaintenanceId(String maintenanceId);
}
