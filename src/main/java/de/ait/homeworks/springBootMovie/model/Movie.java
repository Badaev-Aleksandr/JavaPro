package de.ait.homeworks.springBootMovie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter // Аннотация Lombok для автоматического создания геттеров
// @Getter - Lombok annotation for automatic getter creation
@Setter // Аннотация Lombok для автоматического создания сеттеров
// @Setter - Lombok annotation for automatic setter creation
@AllArgsConstructor // Аннотация Lombok для создания конструктора со всеми параметрами
// @AllArgsConstructor - Lombok annotation for creating a constructor with all parameters
public class Movie {

    private int id; // Уникальный идентификатор фильма
    // private int id; - Unique movie identifier

    private String title; // Название фильма
    // private String title; - Movie title

    private String genre; // Жанр фильма
    // private String genre; - Movie genre

    private int year; // Год выпуска фильма
// private int year; - Movie release year
}
