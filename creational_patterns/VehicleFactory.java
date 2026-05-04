package com.campus.resourcebooking.creational.simplefactory;

/**
 * Simple Factory Pattern Implementation
 * Centralizes object creation logic for different vehicle types
 */
public class VehicleFactory {

    /**
     * Creates a vehicle based on the type string
     * @param type The type of vehicle to create
     * @return A new Vehicle instance
     */
    public static Vehicle createVehicle(String type) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle type cannot be null or empty");
        }

        return switch (type.toLowerCase()) {
            case "car" -> new Car();
            case "bike" -> new Bike();
            case "truck" -> new Truck();
            default -> throw new IllegalArgumentException("Unknown vehicle type: " + type);
        };
    }

    // Vehicle interface
    public interface Vehicle {
        String getType();
        int getMaxSpeed();
        void start();
    }

    // Concrete vehicle implementations
    public static class Car implements Vehicle {
        @Override
        public String getType() { return "Car"; }
        @Override
        public int getMaxSpeed() { return 180; }
        @Override
        public void start() { System.out.println("Car started with key ignition"); }
    }

    public static class Bike implements Vehicle {
        @Override
        public String getType() { return "Bike"; }
        @Override
        public int getMaxSpeed() { return 120; }
        @Override
        public void start() { System.out.println("Bike started with kickstart"); }
    }

    public static class Truck implements Vehicle {
        @Override
        public String getType() { return "Truck"; }
        @Override
        public int getMaxSpeed() { return 100; }
        @Override
        public void start() { System.out.println("Truck started with diesel engine"); }
    }
}