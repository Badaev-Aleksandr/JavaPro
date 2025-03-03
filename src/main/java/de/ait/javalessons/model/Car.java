package de.ait.javalessons.model;

import lombok.extern.slf4j.Slf4j;

public class Car {

    private final String id;
    private String name;

    public Car(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
