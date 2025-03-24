package de.ait.homeworks.springBootMovie.controller;

import de.ait.homeworks.springBootMovie.model.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j // Аннотация Lombok для автоматического создания логгера
@RestController // Аннотация Spring, указывающая, что этот класс является REST-контроллером
@RequestMapping("/movies")// Базовый путь для всех методов в этом контроллере
public class RestApiMovieController {

    private List<Movie> movieList = new ArrayList<>();

    public RestApiMovieController() {
        movieList.addAll(
                List.of(
                        new Movie(1, "Побег из Шоушенка", "Драма", 1994),
                        new Movie(2, "Крёстный отец", "Криминал", 1972),
                        new Movie(3, "Темный рыцарь", "Боевик/Криминал", 2008),
                        new Movie(4, "Начало", "Научная фантастика", 2010),
                        new Movie(5, "Матрица", "Научная фантастика", 1999)
                )
        );
    }

    // Возврат списка всех фильмов
    @GetMapping
    ResponseEntity<List<Movie>> getMovies() {
        return movieList.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new ArrayList<>(movieList), HttpStatus.OK);
    }

    // Возврат фильма по id
    // Если фильм не найден вернет подходящий HTTP-статус
    @GetMapping("/{id}")
    ResponseEntity<Movie> getMovieById(@PathVariable long id) {
       return movieList.stream()
                .filter(movie -> movie.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    //Добавляет новый фильм. Валидация и ответ с HTTP-статусом
    @PostMapping
    ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        boolean exists = movieList.stream()
                .anyMatch(movieFromList -> movieFromList.getId() == movie.getId());

        return exists ?
                new ResponseEntity<>(HttpStatus.CONFLICT) :
                new ResponseEntity<>(movie, movieList.add(movie) ?
                        HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Удаляет фильм по id
    // Вернет подходящий HTTP-статус
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteMovie(@PathVariable long id) {
        boolean removed = movieList.removeIf(movie -> movie.getId() == id);

        return removed ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
