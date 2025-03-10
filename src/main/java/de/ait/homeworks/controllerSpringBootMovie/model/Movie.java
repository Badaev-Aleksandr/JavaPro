package de.ait.homeworks.controllerSpringBootMovie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Movie {
    private final long id;
    private String title;
    private String genre;
    private int year;
}
