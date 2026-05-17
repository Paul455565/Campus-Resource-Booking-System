package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.enums.ResourceAvailability;
import com.campus.resourcebooking.enums.ResourceType;
import com.campus.resourcebooking.model.Resource;
import com.campus.resourcebooking.service.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for Resource operations
 */
@RestController
@RequestMapping("/resources")
@Tag(name = "Resources", description = "API for managing campus resources")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * Get all available resources
     */
    @GetMapping
    @Operation(summary = "Get all available resources", description = "Retrieves a list of all available resources for booking")
    @ApiResponse(responseCode = "200", description = "List of resources retrieved",
            content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<Resource>> getAllResources(
            @Parameter(description = "Filter by availability (optional)")
            @RequestParam(required = false) ResourceAvailability availability) {
        // Implementation: fetch from service
        return ResponseEntity.ok(List.of());
    }

    /**
     * Get resource by ID
     */
    @GetMapping("/{resourceId}")
    @Operation(summary = "Get resource details", description = "Retrieves detailed information about a specific resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource found",
                    content = @Content(schema = @Schema(implementation = Resource.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Resource> getResource(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId) {
        try {
            Resource resource = resourceService.getResourceById(resourceId);
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Create a new resource
     */
    @PostMapping
    @Operation(summary = "Create a new resource", description = "Creates a new resource that can be booked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Resource created successfully",
                    content = @Content(schema = @Schema(implementation = Resource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid resource data")
    })
    public ResponseEntity<Resource> createResource(
            @Parameter(description = "Resource name")
            @RequestParam String name,
            @Parameter(description = "Resource description")
            @RequestParam String description,
            @Parameter(description = "Resource type")
            @RequestParam ResourceType type,
            @Parameter(description = "Resource location")
            @RequestParam String location,
            @Parameter(description = "Resource capacity")
            @RequestParam int capacity) {
        try {
            Resource resource = new Resource(name, description, type, location, capacity);
            return ResponseEntity.status(HttpStatus.CREATED).body(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Update an existing resource
     */
    @PutMapping("/{resourceId}")
    @Operation(summary = "Update resource details", description = "Updates information for an existing resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resource updated successfully",
                    content = @Content(schema = @Schema(implementation = Resource.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Resource> updateResource(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId,
            @Parameter(description = "Updated resource name")
            @RequestParam String name,
            @Parameter(description = "Updated resource description")
            @RequestParam String description,
            @Parameter(description = "Updated resource capacity")
            @RequestParam int capacity) {
        try {
            Resource resource = resourceService.getResourceById(resourceId);
            resource.editResource(name, description, capacity);
            return ResponseEntity.ok(resource);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Delete a resource
     */
    @DeleteMapping("/{resourceId}")
    @Operation(summary = "Delete a resource", description = "Removes a resource from the system (marks as retired)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Resource deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<Void> deleteResource(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId) {
        try {
            Resource resource = resourceService.getResourceById(resourceId);
            resource.deleteResource();
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Check resource availability
     */
    @GetMapping("/{resourceId}/availability")
    @Operation(summary = "Check resource availability", description = "Checks if a resource is available during a specific time period")
    @ApiResponse(responseCode = "200", description = "Availability check completed")
    public ResponseEntity<Boolean> checkAvailability(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId,
            @Parameter(description = "Start date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            boolean available = resourceService.checkResourceAvailability(resourceId, startDate, endDate);
            return ResponseEntity.ok(available);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Schedule maintenance
     */
    @PostMapping("/{resourceId}/maintenance")
    @Operation(summary = "Schedule maintenance", description = "Schedules a maintenance period for a resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance scheduled successfully"),
            @ApiResponse(responseCode = "404", description = "Resource not found")
    })
    public ResponseEntity<String> scheduleMaintenance(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId,
            @Parameter(description = "Maintenance start date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Maintenance end date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            Resource resource = resourceService.getResourceById(resourceId);
            resource.markMaintenance(startDate, endDate);
            return ResponseEntity.ok("Maintenance scheduled successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }
    }

    /**
     * Get resource utilization
     */
    @GetMapping("/{resourceId}/utilization")
    @Operation(summary = "Get resource utilization", description = "Retrieves utilization statistics for a resource")
    @ApiResponse(responseCode = "200", description = "Utilization data retrieved")
    public ResponseEntity<Object> getUtilization(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId,
            @Parameter(description = "Period for utilization analysis")
            @RequestParam(required = false, defaultValue = "MONTHLY") String period) {
        try {
            Object utilization = resourceService.getResourceUtilization(resourceId, period);
            return ResponseEntity.ok(utilization);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
