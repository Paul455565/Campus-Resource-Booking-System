package com.campus.resourcebooking.creational.builder;

/**
 * Builder Pattern Implementation
 * Constructs complex Pizza objects step-by-step with many optional ingredients
 */

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private String dough;
    private String sauce;
    private List<String> toppings;
    private String cheese;
    private boolean extraCheese;
    private String size;

    // Private constructor - only accessible through Builder
    private Pizza(Builder builder) {
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.extraCheese = builder.extraCheese;
        this.size = builder.size;
    }

    // Getters
    public String getDough() { return dough; }
    public String getSauce() { return sauce; }
    public List<String> getToppings() { return new ArrayList<>(toppings); }
    public String getCheese() { return cheese; }
    public boolean hasExtraCheese() { return extraCheese; }
    public String getSize() { return size; }

    @Override
    public String toString() {
        return String.format("Pizza [Size: %s, Dough: %s, Sauce: %s, Cheese: %s, Extra Cheese: %s, Toppings: %s]",
                size, dough, sauce, cheese, extraCheese, toppings);
    }

    // Builder class
    public static class Builder {
        private String dough = "Regular";
        private String sauce = "Tomato";
        private List<String> toppings = new ArrayList<>();
        private String cheese = "Mozzarella";
        private boolean extraCheese = false;
        private String size = "Medium";

        public Builder() {}

        public Builder setSize(String size) {
            this.size = size;
            return this;
        }

        public Builder setDough(String dough) {
            this.dough = dough;
            return this;
        }

        public Builder setSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Builder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        public Builder addCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public Builder addExtraCheese() {
            this.extraCheese = true;
            return this;
        }

        public Pizza build() {
            // Validation
            if (size == null || size.trim().isEmpty()) {
                throw new IllegalStateException("Pizza size must be specified");
            }
            if (toppings.isEmpty()) {
                throw new IllegalStateException("Pizza must have at least one topping");
            }
            return new Pizza(this);
        }
    }

    // Static factory method for common pizza types
    public static Builder margherita() {
        return new Builder()
                .setSize("Medium")
                .addTopping("Tomato")
                .addTopping("Basil");
    }

    public static Builder pepperoni() {
        return new Builder()
                .setSize("Large")
                .addTopping("Pepperoni")
                .addTopping("Mushrooms");
    }
}