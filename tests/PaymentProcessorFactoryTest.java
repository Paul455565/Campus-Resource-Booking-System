package com.campus.resourcebooking.creational.factorymethod;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Factory Method Pattern
 */
public class PaymentProcessorFactoryTest {

    @Test
    public void testCreditCardProcessorFactory() {
        PaymentProcessorFactory factory = new CreditCardProcessorFactory();
        PaymentProcessor processor = factory.createPaymentProcessor();

        assertNotNull(processor);
        assertEquals("Credit Card", processor.getProcessorType());
        assertDoesNotThrow(() -> processor.processPayment(100.0));
    }

    @Test
    public void testPayPalProcessorFactory() {
        PaymentProcessorFactory factory = new PayPalProcessorFactory();
        PaymentProcessor processor = factory.createPaymentProcessor();

        assertNotNull(processor);
        assertEquals("PayPal", processor.getProcessorType());
        assertDoesNotThrow(() -> processor.processPayment(50.0));
    }

    @Test
    public void testProcessPaymentThroughFactory() {
        PaymentProcessorFactory creditCardFactory = new CreditCardProcessorFactory();
        assertDoesNotThrow(() -> creditCardFactory.processPayment(200.0));

        PaymentProcessorFactory paypalFactory = new PayPalProcessorFactory();
        assertDoesNotThrow(() -> paypalFactory.processPayment(75.0));
    }

    @Test
    public void testProcessorTypesAreCorrect() {
        PaymentProcessor creditCard = new CreditCardProcessorFactory().createPaymentProcessor();
        PaymentProcessor paypal = new PayPalProcessorFactory().createPaymentProcessor();

        assertNotEquals(creditCard.getProcessorType(), paypal.getProcessorType());
        assertTrue(creditCard.getProcessorType().contains("Credit"));
        assertTrue(paypal.getProcessorType().contains("PayPal"));
    }

    @Test
    public void testMultipleInstancesFromSameFactory() {
        PaymentProcessorFactory factory = new CreditCardProcessorFactory();

        PaymentProcessor processor1 = factory.createPaymentProcessor();
        PaymentProcessor processor2 = factory.createPaymentProcessor();

        assertNotNull(processor1);
        assertNotNull(processor2);
        assertNotSame(processor1, processor2); // Different instances
        assertEquals(processor1.getProcessorType(), processor2.getProcessorType());
    }

    @Test
    public void testPaymentProcessingWithDifferentAmounts() {
        PaymentProcessorFactory factory = new PayPalProcessorFactory();
        PaymentProcessor processor = factory.createPaymentProcessor();

        // Test various amounts
        assertDoesNotThrow(() -> processor.processPayment(0.01));
        assertDoesNotThrow(() -> processor.processPayment(1000.0));
        assertDoesNotThrow(() -> processor.processPayment(999999.99));
    }
}