package com.campus.resourcebooking.creational.singleton;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Set;
import java.util.HashSet;

/**
 * Unit tests for Singleton Pattern
 */
public class DatabaseConnectionTest {

    @Test
    public void testGetInstanceReturnsSameInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    @Test
    public void testGetInstanceSynchronizedReturnsSameInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstanceSynchronized();
        DatabaseConnection instance2 = DatabaseConnection.getInstanceSynchronized();

        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }

    @Test
    public void testBothMethodsReturnSameInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstanceSynchronized();

        assertSame(instance1, instance2);
    }

    @Test
    public void testThreadSafety() throws InterruptedException {
        final int THREAD_COUNT = 10;
        final Set<DatabaseConnection> instances = new HashSet<>();
        final CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        // Run multiple threads trying to get instances
        for (int i = 0; i < THREAD_COUNT; i++) {
            executor.submit(() -> {
                try {
                    DatabaseConnection instance = DatabaseConnection.getInstance();
                    synchronized (instances) {
                        instances.add(instance);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        // Wait for all threads to complete
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();

        // All instances should be the same
        assertEquals(1, instances.size());
    }

    @Test
    public void testExecuteQuery() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertDoesNotThrow(() -> instance.executeQuery("SELECT * FROM users"));
    }

    @Test
    public void testCloseConnection() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertDoesNotThrow(() -> instance.closeConnection());
    }

    @Test
    public void testClonePrevention() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        assertThrows(CloneNotSupportedException.class, () -> instance.clone());
    }

    @Test
    public void testGetConnection() {
        DatabaseConnection instance = DatabaseConnection.getInstance();
        // In a real test, we'd mock the connection or use an embedded database
        // For now, just verify it doesn't throw
        assertDoesNotThrow(() -> instance.getConnection());
    }

    @Test
    public void testMultipleCallsReturnSameInstance() {
        DatabaseConnection instance1 = DatabaseConnection.getInstance();
        DatabaseConnection instance2 = DatabaseConnection.getInstance();
        DatabaseConnection instance3 = DatabaseConnection.getInstance();

        assertSame(instance1, instance2);
        assertSame(instance2, instance3);
        assertSame(instance1, instance3);
    }
}