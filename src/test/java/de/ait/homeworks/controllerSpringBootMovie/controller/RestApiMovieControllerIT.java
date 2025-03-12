package de.ait.homeworks.controllerSpringBootMovie.controller;

import de.ait.homeworks.controllerSpringBootMovie.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiMovieControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static final String BASE_URL = "/movies";

    @Test
    void testGetAllMoviesDefault() {
        ResponseEntity<Movie[]> response = testRestTemplate.getForEntity(BASE_URL, Movie[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5, response.getBody().length);
        assertEquals("Темный рыцарь", response.getBody()[2].getTitle());
    }

    @Test
    void testGetMovieByIdWasFound() {
        ResponseEntity<Movie> response = testRestTemplate.getForEntity(BASE_URL + "/4", Movie.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Начало", response.getBody().getTitle());
    }

    @Test
    void testGetMovieByIdWasNotFound() {
        ResponseEntity<Movie> response = testRestTemplate.getForEntity(BASE_URL + "/55", Movie.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    void testAddNewMovieSuccess() {
        Movie movie = new Movie(77, "Тор", "фэнтези", 2018);
        ResponseEntity<Movie> response = testRestTemplate.postForEntity(BASE_URL, movie, Movie.class);
        ResponseEntity<Movie[]> responseList = testRestTemplate.getForEntity(BASE_URL, Movie[].class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movie.getId(), response.getBody().getId());
        assertEquals(6, responseList.getBody().length);
    }

    @Test
    void testAddNewMovieConflict() {
        Movie movie = new Movie(5, "Матрица", "Научная фантастика", 1999);
        ResponseEntity<Movie> response = testRestTemplate.postForEntity(BASE_URL, movie, Movie.class);
        ResponseEntity<Movie[]> responseList = testRestTemplate.getForEntity(BASE_URL, Movie[].class);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(5, responseList.getBody().length);
    }

    @Test
    void testDeleteMovieSuccess() {
        ResponseEntity<Void> response = testRestTemplate.exchange(BASE_URL + "/4", HttpMethod.DELETE, null, Void.class);
        ResponseEntity<Movie[]> movies = testRestTemplate.getForEntity(BASE_URL, Movie[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(4, movies.getBody().length);
    }

    @Test
    void testDeleteMovieNotFound() {
        ResponseEntity<Void> response = testRestTemplate.exchange(BASE_URL + "/55", HttpMethod.DELETE, null, Void.class);
        ResponseEntity<Movie[]> movies = testRestTemplate.getForEntity(BASE_URL, Movie[].class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(5, movies.getBody().length);
    }
}
