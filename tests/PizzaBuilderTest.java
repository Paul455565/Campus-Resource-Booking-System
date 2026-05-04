package com.campus.resourcebooking.creational.builder;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Unit tests for Builder Pattern
 */
public class PizzaBuilderTest {

    @Test
    public void testBasicPizzaBuild() {
        Pizza pizza = new Pizza.Builder()
                .setSize("Large")
                .addTopping("Pepperoni")
                .build();

        assertNotNull(pizza);
        assertEquals("Large", pizza.getSize());
        assertEquals("Regular", pizza.getDough()); // default
        assertEquals("Tomato", pizza.getSauce()); // default
        assertEquals("Mozzarella", pizza.getCheese()); // default
        assertFalse(pizza.hasExtraCheese()); // default
        assertTrue(pizza.getToppings().contains("Pepperoni"));
    }

    @Test
    public void testPizzaWithAllCustomizations() {
        Pizza pizza = new Pizza.Builder()
                .setSize("Small")
                .setDough("Thin Crust")
                .setSauce("Pesto")
                .addTopping("Mushrooms")
                .addTopping("Olives")
                .addCheese("Cheddar")
                .addExtraCheese()
                .build();

        assertEquals("Small", pizza.getSize());
        assertEquals("Thin Crust", pizza.getDough());
        assertEquals("Pesto", pizza.getSauce());
        assertEquals("Cheddar", pizza.getCheese());
        assertTrue(pizza.hasExtraCheese());
        assertTrue(pizza.getToppings().contains("Mushrooms"));
        assertTrue(pizza.getToppings().contains("Olives"));
        assertEquals(2, pizza.getToppings().size());
    }

    @Test
    public void testMargheritaBuilder() {
        Pizza pizza = Pizza.margherita().build();

        assertEquals("Medium", pizza.getSize());
        assertTrue(pizza.getToppings().contains("Tomato"));
        assertTrue(pizza.getToppings().contains("Basil"));
    }

    @Test
    public void testPepperoniBuilder() {
        Pizza pizza = Pizza.pepperoni().build();

        assertEquals("Large", pizza.getSize());
        assertTrue(pizza.getToppings().contains("Pepperoni"));
        assertTrue(pizza.getToppings().contains("Mushrooms"));
    }

    @Test
    public void testBuildPizzaWithoutSize() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new Pizza.Builder()
                    .addTopping("Cheese")
                    .build();
        });
        assertEquals("Pizza size must be specified", exception.getMessage());
    }

    @Test
    public void testBuildPizzaWithoutToppings() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            new Pizza.Builder()
                    .setSize("Medium")
                    .build();
        });
        assertEquals("Pizza must have at least one topping", exception.getMessage());
    }

    @Test
    public void testMultipleToppings() {
        Pizza pizza = new Pizza.Builder()
                .setSize("Medium")
                .addTopping("Pepperoni")
                .addTopping("Mushrooms")
                .addTopping("Onions")
                .addTopping("Peppers")
                .build();

        List<String> toppings = pizza.getToppings();
        assertEquals(4, toppings.size());
        assertTrue(toppings.contains("Pepperoni"));
        assertTrue(toppings.contains("Mushrooms"));
        assertTrue(toppings.contains("Onions"));
        assertTrue(toppings.contains("Peppers"));
    }

    @Test
    public void testToppingsListIsImmutable() {
        Pizza pizza = new Pizza.Builder()
                .setSize("Medium")
                .addTopping("Cheese")
                .build();

        List<String> toppings = pizza.getToppings();
        assertThrows(UnsupportedOperationException.class, () -> {
            toppings.add("Extra topping");
        });
    }

    @Test
    public void testToString() {
        Pizza pizza = new Pizza.Builder()
                .setSize("Large")
                .setDough("Thick")
                .addTopping("Pepperoni")
                .addExtraCheese()
                .build();

        String description = pizza.toString();
        assertTrue(description.contains("Large"));
        assertTrue(description.contains("Thick"));
        assertTrue(description.contains("Pepperoni"));
        assertTrue(description.contains("true")); // extra cheese
    }
}