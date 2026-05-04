package com.campus.resourcebooking.creational.prototype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Prototype Pattern
 */
public class ShapeCacheTest {

    @Test
    public void testGetShapeRedCircle() {
        Shape shape = ShapeCache.getShape("RedCircle");
        assertNotNull(shape);
        assertEquals("Circle", shape.getType());
        assertTrue(shape instanceof Circle);

        Circle circle = (Circle) shape;
        assertEquals(5, circle.getRadius());
    }

    @Test
    public void testGetShapeBlueCircle() {
        Shape shape = ShapeCache.getShape("BlueCircle");
        assertNotNull(shape);
        assertEquals("Circle", shape.getType());
        assertTrue(shape instanceof Circle);

        Circle circle = (Circle) shape;
        assertEquals(10, circle.getRadius());
    }

    @Test
    public void testGetShapeGreenRectangle() {
        Shape shape = ShapeCache.getShape("GreenRectangle");
        assertNotNull(shape);
        assertEquals("Rectangle", shape.getType());
        assertTrue(shape instanceof Rectangle);

        Rectangle rectangle = (Rectangle) shape;
        assertEquals(20, rectangle.getWidth());
        assertEquals(10, rectangle.getHeight());
    }

    @Test
    public void testGetShapeYellowSquare() {
        Shape shape = ShapeCache.getShape("YellowSquare");
        assertNotNull(shape);
        assertEquals("Rectangle", shape.getType());
        assertTrue(shape instanceof Rectangle);

        Rectangle square = (Rectangle) shape;
        assertEquals(15, square.getWidth());
        assertEquals(15, square.getHeight());
    }

    @Test
    public void testShapeCloning() {
        Shape original = ShapeCache.getShape("RedCircle");
        Shape clone = original.clone();

        assertNotNull(clone);
        assertNotSame(original, clone); // Different instances
        assertEquals(original.getType(), clone.getType());
        assertEquals(original.getInfo(), clone.getInfo());
    }

    @Test
    public void testShapeIndependenceAfterCloning() {
        Shape original = ShapeCache.getShape("RedCircle");
        Shape clone = original.clone();

        // Modify clone
        clone.setPosition(10, 20);

        // Original should remain unchanged
        assertNotEquals(original.getInfo(), clone.getInfo());
    }

    @Test
    public void testGetShapeUnknownId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ShapeCache.getShape("UnknownShape");
        });
        assertEquals("Shape not found in cache: UnknownShape", exception.getMessage());
    }

    @Test
    public void testAddShapeToCache() {
        Circle newCircle = new Circle(25);
        ShapeCache.addShape("BigCircle", newCircle);

        Shape retrieved = ShapeCache.getShape("BigCircle");
        assertNotNull(retrieved);
        assertEquals("Circle", retrieved.getType());
        assertEquals(25, ((Circle) retrieved).getRadius());
    }

    @Test
    public void testCacheSize() {
        int initialSize = ShapeCache.getCacheSize();
        assertTrue(initialSize >= 4); // At least the 4 predefined shapes

        ShapeCache.addShape("TestShape", new Circle(1));
        assertEquals(initialSize + 1, ShapeCache.getCacheSize());
    }

    @Test
    public void testClearCache() {
        ShapeCache.clearCache();
        assertEquals(0, ShapeCache.getCacheSize());

        // Should throw exception for any shape request
        assertThrows(IllegalArgumentException.class, () -> {
            ShapeCache.getShape("RedCircle");
        });
    }

    @Test
    public void testShapeDrawMethod() {
        Shape circle = ShapeCache.getShape("RedCircle");
        assertDoesNotThrow(() -> circle.draw());

        Shape rectangle = ShapeCache.getShape("GreenRectangle");
        assertDoesNotThrow(() -> rectangle.draw());
    }

    @Test
    public void testShapeSetPosition() {
        Shape shape = ShapeCache.getShape("RedCircle");
        shape.setPosition(100, 200);

        String info = shape.getInfo();
        assertTrue(info.contains("100"));
        assertTrue(info.contains("200"));
    }
}