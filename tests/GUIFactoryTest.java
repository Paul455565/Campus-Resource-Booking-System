package com.campus.resourcebooking.creational.abstractfactory;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Abstract Factory Pattern
 */
public class GUIFactoryTest {

    @Test
    public void testWindowsGUIFactory() {
        GUIFactory factory = new WindowsGUIFactory();

        Button button = factory.createButton();
        Window window = factory.createWindow();

        assertNotNull(button);
        assertNotNull(window);
        assertEquals("Windows Button", button.getStyle());
        assertEquals("Windows Theme", window.getTheme());
    }

    @Test
    public void testMacOSGUIFactory() {
        GUIFactory factory = new MacOSGUIFactory();

        Button button = factory.createButton();
        Window window = factory.createWindow();

        assertNotNull(button);
        assertNotNull(window);
        assertEquals("MacOS Button", button.getStyle());
        assertEquals("MacOS Theme", window.getTheme());
    }

    @Test
    public void testGUIFactoryCreatorWindows() {
        GUIFactory factory = GUIFactoryCreator.createFactory("windows");
        assertTrue(factory instanceof WindowsGUIFactory);

        Button button = factory.createButton();
        assertEquals("Windows Button", button.getStyle());
    }

    @Test
    public void testGUIFactoryCreatorMacOS() {
        GUIFactory factory = GUIFactoryCreator.createFactory("macos");
        assertTrue(factory instanceof MacOSGUIFactory);

        Button button = factory.createButton();
        assertEquals("MacOS Button", button.getStyle());
    }

    @Test
    public void testGUIFactoryCreatorCaseInsensitive() {
        GUIFactory windowsFactory = GUIFactoryCreator.createFactory("WINDOWS");
        assertTrue(windowsFactory instanceof WindowsGUIFactory);

        GUIFactory macFactory = GUIFactoryCreator.createFactory("Mac");
        assertTrue(macFactory instanceof MacOSGUIFactory);
    }

    @Test
    public void testGUIFactoryCreatorNullType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GUIFactoryCreator.createFactory(null);
        });
        assertEquals("OS type cannot be null", exception.getMessage());
    }

    @Test
    public void testGUIFactoryCreatorUnknownType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            GUIFactoryCreator.createFactory("linux");
        });
        assertEquals("Unknown OS type: linux", exception.getMessage());
    }

    @Test
    public void testComponentMethods() {
        GUIFactory factory = new WindowsGUIFactory();

        Button button = factory.createButton();
        Window window = factory.createWindow();

        // Test that methods don't throw exceptions
        assertDoesNotThrow(() -> button.paint());
        assertDoesNotThrow(() -> window.render());
    }

    @Test
    public void testFamilyConsistency() {
        // Windows family
        GUIFactory windowsFactory = new WindowsGUIFactory();
        Button windowsButton = windowsFactory.createButton();
        Window windowsWindow = windowsFactory.createWindow();

        assertTrue(windowsButton.getStyle().contains("Windows"));
        assertTrue(windowsWindow.getTheme().contains("Windows"));

        // MacOS family
        GUIFactory macFactory = new MacOSGUIFactory();
        Button macButton = macFactory.createButton();
        Window macWindow = macFactory.createWindow();

        assertTrue(macButton.getStyle().contains("MacOS"));
        assertTrue(macWindow.getTheme().contains("MacOS"));
    }
}