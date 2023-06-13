package com.appfire.taskmanagement.model;

import java.util.HashMap;
import java.util.Map;

public class Navbar {
    private Map<String, String> elements;

    public Navbar() {
        elements = new HashMap<>();
    }

    public Navbar(Map<String, String> elements) {
        this.elements = elements;
    }

    public Map<String, String> getElements() {
        return elements;
    }

    public Navbar setElements(Map<String, String> elements) {
        this.elements = elements;
        return this;
    }
}
