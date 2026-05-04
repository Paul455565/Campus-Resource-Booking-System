package com.campus.resourcebooking.creational.abstractfactory;

/**
 * Abstract Factory Pattern Implementation
 * Creates families of related UI objects for different platforms
 */

// Abstract Factory
public interface GUIFactory {
    Button createButton();
    Window createWindow();
}

// Concrete Factories
public class WindowsGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Window createWindow() {
        return new WindowsWindow();
    }
}

public class MacOSGUIFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Window createWindow() {
        return new MacOSWindow();
    }
}

// Abstract Products
interface Button {
    void paint();
    String getStyle();
}

interface Window {
    void render();
    String getTheme();
}

// Concrete Products for Windows
class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting Windows-style button");
    }

    @Override
    public String getStyle() {
        return "Windows Button";
    }
}

class WindowsWindow implements Window {
    @Override
    public void render() {
        System.out.println("Rendering Windows-style window");
    }

    @Override
    public String getTheme() {
        return "Windows Theme";
    }
}

// Concrete Products for MacOS
class MacOSButton implements Button {
    @Override
    public void paint() {
        System.out.println("Painting MacOS-style button");
    }

    @Override
    public String getStyle() {
        return "MacOS Button";
    }
}

class MacOSWindow implements Window {
    @Override
    public void render() {
        System.out.println("Rendering MacOS-style window");
    }

    @Override
    public String getTheme() {
        return "MacOS Theme";
    }
}

// Factory Creator
public class GUIFactoryCreator {
    public static GUIFactory createFactory(String osType) {
        if (osType == null) {
            throw new IllegalArgumentException("OS type cannot be null");
        }

        return switch (osType.toLowerCase()) {
            case "windows" -> new WindowsGUIFactory();
            case "macos", "mac" -> new MacOSGUIFactory();
            default -> throw new IllegalArgumentException("Unknown OS type: " + osType);
        };
    }
}