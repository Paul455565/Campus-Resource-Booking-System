package com.campus.resourcebooking.creational.simplefactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Simple Factory Pattern
 */
public class VehicleFactoryTest {

    @Test
    public void testCreateCar() {
        VehicleFactory.Vehicle car = VehicleFactory.createVehicle("car");
        assertNotNull(car);
        assertEquals("Car", car.getType());
        assertEquals(180, car.getMaxSpeed());
    }

    @Test
    public void testCreateBike() {
        VehicleFactory.Vehicle bike = VehicleFactory.createVehicle("bike");
        assertNotNull(bike);
        assertEquals("Bike", bike.getType());
        assertEquals(120, bike.getMaxSpeed());
    }

    @Test
    public void testCreateTruck() {
        VehicleFactory.Vehicle truck = VehicleFactory.createVehicle("truck");
        assertNotNull(truck);
        assertEquals("Truck", truck.getType());
        assertEquals(100, truck.getMaxSpeed());
    }

    @Test
    public void testCreateVehicleCaseInsensitive() {
        VehicleFactory.Vehicle car = VehicleFactory.createVehicle("CAR");
        assertEquals("Car", car.getType());

        VehicleFactory.Vehicle bike = VehicleFactory.createVehicle("Bike");
        assertEquals("Bike", bike.getType());
    }

    @Test
    public void testCreateVehicleNullType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            VehicleFactory.createVehicle(null);
        });
        assertEquals("Vehicle type cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testCreateVehicleEmptyType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            VehicleFactory.createVehicle("");
        });
        assertEquals("Vehicle type cannot be null or empty", exception.getMessage());
    }

    @Test
    public void testCreateVehicleUnknownType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            VehicleFactory.createVehicle("airplane");
        });
        assertEquals("Unknown vehicle type: airplane", exception.getMessage());
    }

    @Test
    public void testVehicleStartMethods() {
        VehicleFactory.Vehicle car = VehicleFactory.createVehicle("car");
        // Since start() just prints, we can't easily test output without capturing System.out
        // In a real test, we'd use a logger or return a value to verify
        assertDoesNotThrow(() -> car.start());

        VehicleFactory.Vehicle bike = VehicleFactory.createVehicle("bike");
        assertDoesNotThrow(() -> bike.start());

        VehicleFactory.Vehicle truck = VehicleFactory.createVehicle("truck");
        assertDoesNotThrow(() -> truck.start());
    }
}