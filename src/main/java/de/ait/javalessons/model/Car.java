package de.ait.javalessons.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // Аннотация, указывающая, что этот класс является сущностью JPA
// @Entity - Annotation indicating that this class is a JPA entity
public class Car {

    @Id // Аннотация, указывающая, что это поле является первичным ключом
    // @Id - Annotation indicating that this field is the primary key
    private String id;
    private String name;

    public Car() {
        // Конструктор по умолчанию, необходим для JPA
        // Default constructor, required for JPA
    }

    public Car(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
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
