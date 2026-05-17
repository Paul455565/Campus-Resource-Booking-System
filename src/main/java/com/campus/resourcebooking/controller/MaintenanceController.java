package com.campus.resourcebooking.controller;

import com.campus.resourcebooking.model.Maintenance;
import com.campus.resourcebooking.enums.MaintenanceStatus;
import com.campus.resourcebooking.service.MaintenanceService;
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
 * REST Controller for Maintenance operations
 */
@RestController
@RequestMapping("/maintenance")
@Tag(name = "Maintenance", description = "API for managing resource maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    /**
     * Schedule maintenance
     */
    @PostMapping
    @Operation(summary = "Schedule maintenance", description = "Schedules a maintenance task for a resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Maintenance scheduled successfully",
                    content = @Content(schema = @Schema(implementation = Maintenance.class))),
            @ApiResponse(responseCode = "400", description = "Invalid maintenance data")
    })
    public ResponseEntity<Maintenance> scheduleMaintenance(
            @Parameter(description = "Resource ID")
            @RequestParam String resourceId,
            @Parameter(description = "Maintenance start date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Maintenance end date and time")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Parameter(description = "Maintenance description")
            @RequestParam String description) {
        try {
            Maintenance maintenance = maintenanceService.scheduleMaintenance(resourceId, startDate, endDate, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(maintenance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Get maintenance by ID
     */
    @GetMapping("/{maintenanceId}")
    @Operation(summary = "Get maintenance details", description = "Retrieves details of a specific maintenance task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance found",
                    content = @Content(schema = @Schema(implementation = Maintenance.class))),
            @ApiResponse(responseCode = "404", description = "Maintenance not found")
    })
    public ResponseEntity<Maintenance> getMaintenance(
            @Parameter(description = "Maintenance ID")
            @PathVariable String maintenanceId) {
        try {
            Maintenance maintenance = maintenanceService.getMaintenanceById(maintenanceId);
            return ResponseEntity.ok(maintenance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Get maintenance tasks for a resource
     */
    @GetMapping("/resource/{resourceId}")
    @Operation(summary = "Get resource maintenance tasks", description = "Retrieves all maintenance tasks for a specific resource")
    @ApiResponse(responseCode = "200", description = "List of maintenance tasks")
    public ResponseEntity<List<Maintenance>> getResourceMaintenance(
            @Parameter(description = "Resource ID")
            @PathVariable String resourceId) {
        List<Maintenance> maintenanceTasks = maintenanceService.getResourceMaintenance(resourceId);
        return ResponseEntity.ok(maintenanceTasks);
    }

    /**
     * Get pending maintenance tasks
     */
    @GetMapping("/pending")
    @Operation(summary = "Get pending maintenance", description = "Retrieves all pending maintenance tasks")
    @ApiResponse(responseCode = "200", description = "List of pending maintenance tasks")
    public ResponseEntity<List<Maintenance>> getPendingMaintenance() {
        List<Maintenance> maintenanceTasks = maintenanceService.getPendingMaintenance();
        return ResponseEntity.ok(maintenanceTasks);
    }

    /**
     * Complete maintenance task
     */
    @PutMapping("/{maintenanceId}/complete")
    @Operation(summary = "Complete maintenance", description = "Marks a maintenance task as completed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maintenance completed successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance not found")
    })
    public ResponseEntity<String> completeMaintenance(
            @Parameter(description = "Maintenance ID")
            @PathVariable String maintenanceId,
            @Parameter(description = "Completion notes")
            @RequestParam(required = false) String notes) {
        try {
            maintenanceService.completeMaintenance(maintenanceId, notes);
            return ResponseEntity.ok("Maintenance completed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Maintenance not found");
        }
    }

    /**
     * Cancel maintenance task
     */
    @DeleteMapping("/{maintenanceId}")
    @Operation(summary = "Cancel maintenance", description = "Cancels a scheduled maintenance task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Maintenance cancelled successfully"),
            @ApiResponse(responseCode = "404", description = "Maintenance not found")
    })
    public ResponseEntity<Void> cancelMaintenance(
            @Parameter(description = "Maintenance ID")
            @PathVariable String maintenanceId) {
        try {
            maintenanceService.cancelMaintenance(maintenanceId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
