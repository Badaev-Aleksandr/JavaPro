package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestApiCarControllerTest {

    private RestApiCarController restApiCarController;

    @BeforeEach
    public void setUp() {
        //TODO
        restApiCarController = new RestApiCarController(null);
    }

    @Test
    void testGetCarsReturnDefaultCars() {
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        assertEquals(4,resultCars.size());
        assertEquals("BMW M1", resultCars.get(0).getName());
    }

    @Test
    void testGetCarByIdWasFound() {
        Optional<Car> result = restApiCarController.getCarById("1");
        assertTrue(result.isPresent());
        assertEquals("BMW M1", result.get().getName());
    }

    @Test
    void testGetCarByIdWasNotFound() {
        Optional<Car> result = restApiCarController.getCarById("10");
        assertFalse(result.isPresent());
    }

    @Test
    void testPostCarAddNewCar() {
        Car carToApp = new Car("5", "Tesla Model 1");
        Car result = restApiCarController.postCar(carToApp);
        assertEquals(carToApp.getName(), result.getName());

        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        assertEquals(5,resultCars.size());
    }

    @Test
    void testPutCarUpdateCarInfo(){
        Car carToApp = new Car("1", "Tesla Model 1");
        ResponseEntity<Car> responseEntityResult = restApiCarController.putCar("1", carToApp);
        assertEquals("Tesla Model 1", responseEntityResult.getBody().getName());
        assertEquals(200,responseEntityResult.getStatusCodeValue());
    }

    @Test
    void testPutCarUpdateCarNotFound(){
        Car carToApp = new Car("5", "Tesla Model 1");
        ResponseEntity<Car> responseEntityResult = restApiCarController.putCar("5", carToApp);
        assertEquals("Tesla Model 1", responseEntityResult.getBody().getName());
        assertEquals(201,responseEntityResult.getStatusCodeValue());
    }

    @Test
    void testDeleteCarSuccess(){
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        assertEquals(4,resultCars.size());
        restApiCarController.deleteCar("1");

        Iterable<Car> resultCarsIterableDeletedCar = restApiCarController.getCars();
        List<Car> resultCarsDeleted = new ArrayList<>();
        resultCarsIterableDeletedCar.forEach(resultCarsDeleted::add);
        assertEquals(3,resultCarsDeleted.size());

    }

}
