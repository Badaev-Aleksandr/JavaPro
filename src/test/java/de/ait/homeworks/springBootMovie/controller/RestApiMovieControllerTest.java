package de.ait.homeworks.springBootMovie.controller;

import de.ait.homeworks.springBootMovie.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RestApiMovieControllerTest {

    private RestApiMovieController restApiMovieController;


    @BeforeEach
    public void setUp() {
        restApiMovieController = new RestApiMovieController();
    }

    @Test
    void testGetMoviesReturnDefaultMovies() {
       ResponseEntity<List<Movie>> resultMovie = restApiMovieController.getMovies();
       List<Movie> movieList = resultMovie.getBody();
       assertFalse(movieList.isEmpty());
       assertEquals(5, movieList.size());
       assertEquals(HttpStatus.OK, resultMovie.getStatusCode());
       assertEquals("Побег из Шоушенка",movieList.get(0).getTitle());
    }

    @Test
    void testGetMovieByIdSuccess() {
        ResponseEntity<Movie> resultMovie = restApiMovieController.getMovieById(1);
        Movie movie = resultMovie.getBody();
        assertEquals(HttpStatus.OK, resultMovie.getStatusCode());
        assertEquals("Драма",movie.getGenre());
        assertEquals(1994,movie.getYear());
    }

    @Test
    void testGetMovieByIdNotFound() {
        ResponseEntity<Movie> resultMovie = restApiMovieController.getMovieById(6);
        assertEquals(HttpStatus.NOT_FOUND, resultMovie.getStatusCode());
        assertEquals(null,resultMovie.getBody());
    }

    @Test
    void testAddMovieSuccess() {
        Movie movie = new Movie(6, "Терминатор", "Боевик", 1994);
        ResponseEntity<Movie> resultMovie = restApiMovieController.addMovie(movie);
        ResponseEntity<List<Movie>> movies = restApiMovieController.getMovies();
        List<Movie> movieList = movies.getBody();
        assertEquals(HttpStatus.CREATED,resultMovie.getStatusCode());
        assertEquals(6,movieList.size());
        assertEquals(movie,movieList.get(5));
    }

    @Test
    void testAddMovieConflict(){
        Movie movie = new Movie(2, "Крёстный отец", "Криминал", 1972);
        ResponseEntity<Movie> resultMovie = restApiMovieController.addMovie(movie);
        ResponseEntity<List<Movie>> movies = restApiMovieController.getMovies();
        assertEquals(HttpStatus.CONFLICT,resultMovie.getStatusCode());
        assertEquals(5,movies.getBody().size());
    }

    @Test
    void testDeleteMovieSuccess(){
        ResponseEntity<Void> resultDelete = restApiMovieController.deleteMovie(1L);
        assertEquals(HttpStatus.OK,resultDelete.getStatusCode());

    }

    @Test
    void testDeleteMovieNotFound(){
        ResponseEntity<Void> resultDelete = restApiMovieController.deleteMovie(6L);
        assertEquals(HttpStatus.NOT_FOUND,resultDelete.getStatusCode());

    }
}