package com.campus.resourcebooking.creational.factorymethod;

/**
 * Factory Method Pattern Implementation
 * Delegates payment processor instantiation to subclasses
 */

// Abstract Creator
public abstract class PaymentProcessorFactory {

    /**
     * Factory method that subclasses must implement
     */
    public abstract PaymentProcessor createPaymentProcessor();

    /**
     * Process a payment using the created processor
     */
    public void processPayment(double amount) {
        PaymentProcessor processor = createPaymentProcessor();
        processor.processPayment(amount);
    }
}

// Concrete Creators
public class CreditCardProcessorFactory extends PaymentProcessorFactory {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new CreditCardProcessor();
    }
}

public class PayPalProcessorFactory extends PaymentProcessorFactory {
    @Override
    public PaymentProcessor createPaymentProcessor() {
        return new PayPalProcessor();
    }
}

// Product interface
interface PaymentProcessor {
    void processPayment(double amount);
    String getProcessorType();
}

// Concrete Products
class CreditCardProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Credit Card");
        // Credit card processing logic
    }

    @Override
    public String getProcessorType() {
        return "Credit Card";
    }
}

class PayPalProcessor implements PaymentProcessor {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing $" + amount + " via PayPal");
        // PayPal processing logic
    }

    @Override
    public String getProcessorType() {
        return "PayPal";
    }
}