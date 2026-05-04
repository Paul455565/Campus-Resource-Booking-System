package com.campus.resourcebooking.creational.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Prototype Pattern Implementation
 * Clones existing shape objects to avoid costly initialization
 */

// Prototype interface
public interface Shape extends Cloneable {
    Shape clone();
    void draw();
    String getType();
    void setPosition(int x, int y);
    String getInfo();
}

// Concrete Prototypes
public class Circle implements Shape {
    private int radius;
    private int x, y;
    private String color;

    public Circle(int radius) {
        this.radius = radius;
        this.color = "Black";
        // Simulate expensive initialization
        try {
            Thread.sleep(10); // Simulate costly operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Circle(Circle source) {
        this.radius = source.radius;
        this.x = source.x;
        this.y = source.y;
        this.color = source.color;
    }

    @Override
    public Shape clone() {
        return new Circle(this);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle at (" + x + "," + y + ") with radius " + radius);
    }

    @Override
    public String getType() {
        return "Circle";
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getInfo() {
        return String.format("Circle: radius=%d, position=(%d,%d), color=%s",
                radius, x, y, color);
    }

    public int getRadius() { return radius; }
    public void setColor(String color) { this.color = color; }
}

public class Rectangle implements Shape {
    private int width, height;
    private int x, y;
    private String color;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
        this.color = "Black";
        // Simulate expensive initialization
        try {
            Thread.sleep(10); // Simulate costly operation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Rectangle(Rectangle source) {
        this.width = source.width;
        this.height = source.height;
        this.x = source.x;
        this.y = source.y;
        this.color = source.color;
    }

    @Override
    public Shape clone() {
        return new Rectangle(this);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Rectangle at (" + x + "," + y + ") with size " + width + "x" + height);
    }

    @Override
    public String getType() {
        return "Rectangle";
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String getInfo() {
        return String.format("Rectangle: size=%dx%d, position=(%d,%d), color=%s",
                width, height, x, y, color);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public void setColor(String color) { this.color = color; }
}

// Prototype Manager/Cache
public class ShapeCache {
    private static Map<String, Shape> shapeCache = new HashMap<>();

    static {
        // Load cache with prototype shapes
        Circle circle = new Circle(5);
        circle.setColor("Red");
        shapeCache.put("RedCircle", circle);

        Circle bigCircle = new Circle(10);
        bigCircle.setColor("Blue");
        shapeCache.put("BlueCircle", bigCircle);

        Rectangle rectangle = new Rectangle(20, 10);
        rectangle.setColor("Green");
        shapeCache.put("GreenRectangle", rectangle);

        Rectangle square = new Rectangle(15, 15);
        square.setColor("Yellow");
        shapeCache.put("YellowSquare", square);
    }

    public static Shape getShape(String shapeId) {
        Shape cachedShape = shapeCache.get(shapeId);
        if (cachedShape == null) {
            throw new IllegalArgumentException("Shape not found in cache: " + shapeId);
        }
        return cachedShape.clone();
    }

    public static void addShape(String shapeId, Shape shape) {
        shapeCache.put(shapeId, shape);
    }

    public static int getCacheSize() {
        return shapeCache.size();
    }

    public static void clearCache() {
        shapeCache.clear();
    }
}