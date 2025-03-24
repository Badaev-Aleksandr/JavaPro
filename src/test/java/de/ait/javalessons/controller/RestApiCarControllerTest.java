package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import de.ait.javalessons.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RestApiCarControllerTest {

    private RestApiCarController restApiCarController;


    @Autowired
    private CarRepository carRepository;


    @BeforeEach
    void setUp() {
        //TODO
        restApiCarController = new RestApiCarController(carRepository);
    }

    @Test
    void testGetCarsReturnDefaultCars(){
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);

        assertEquals(5, resultCars.size());
        assertEquals("BMW M1", resultCars.get(0).getName());
    }

    @Test
    void testGetCarByIdWasFound(){
        Optional<Car> result = restApiCarController.getCarById("1");
        assertTrue(result.isPresent());
        assertEquals("BMW M1", result.get().getName());
    }

    @Test
    void testGetCarByIdWasNotFound(){
        Optional<Car> result = restApiCarController.getCarById("10");
        assertFalse(result.isPresent());
    }

    @Test
    void testPostCarAddNewCar(){
        Car carToAdd = new Car("5", "Tesla Model 1");
        Car result = restApiCarController.postCar(carToAdd);
        assertEquals("Tesla Model 1", result.getName());
        assertEquals("5", result.getId());

        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        assertEquals(5, resultCars.size());
    }

    @Test
    void testPutCarUpdateCarInfo(){
        Car carToAdd = new Car("1", "Tesla Model 1");
        ResponseEntity<Car> responseEntityResult = restApiCarController.putCar("1", carToAdd);
        assertEquals(200, responseEntityResult.getStatusCodeValue());
        assertEquals("Tesla Model 1", responseEntityResult.getBody().getName());
    }

    @Test
    void testPutCarCreateNewCar(){
        Car carToAdd = new Car("10", "Tesla Model 1");
        ResponseEntity<Car> responseEntityResult = restApiCarController.putCar("10", carToAdd);
        assertEquals(201, responseEntityResult.getStatusCodeValue());
        assertEquals("Tesla Model 1", responseEntityResult.getBody().getName());
    }

    @Test
    void testDeleteCarSuccess(){
        Iterable<Car> resultCarsIterable = restApiCarController.getCars();
        List<Car> resultCars = new ArrayList<>();
        resultCarsIterable.forEach(resultCars::add);
        assertEquals(5, resultCars.size());
        restApiCarController.deleteCar("1");
        Iterable<Car> resultCarsIterableDeletedCar = restApiCarController.getCars();
        resultCars =  new ArrayList<>();
        resultCarsIterableDeletedCar.forEach(resultCars::add);
        assertEquals(4, resultCars.size());
    }
}