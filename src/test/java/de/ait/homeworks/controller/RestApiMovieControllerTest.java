package de.ait.homeworks.controller;

import de.ait.homeworks.controllerSpringBootMovie.controller.RestApiMovieController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class RestApiMovieControllerTest {

   private RestApiMovieController restApiMovieController;

   @BeforeEach
    void setUp(){
        restApiMovieController = new RestApiMovieController();
    }

    @Test
    void testGetAllMovies(){
        assertFalse(restApiMovieController.getMovies());
    }

}
