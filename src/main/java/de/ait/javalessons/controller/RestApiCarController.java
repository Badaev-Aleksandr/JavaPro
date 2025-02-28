package de.ait.javalessons.controller;

import de.ait.javalessons.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class RestApiCarController {

    private static List<Car> carsList = new ArrayList<>();

    public RestApiCarController() {
        carsList.addAll(List.of(
                new Car("1", "BMW M1"),
                new Car("2","Audi A8"),
                new Car("3", "Kia Spartage"),
                new Car("4", "Volvo 960")
        ));
    }

    //@RequestMapping(value = "/cars", method = RequestMethod.GET)
    @GetMapping
    Iterable<Car> getCars() {
        return carsList;
    }

    @GetMapping("/{id}")
    Optional<Car> getCarById(@PathVariable String id) {
        for (Car car : carsList) {
            if (car.getId().equals(id)) {
                return Optional.of(car);
            }
        }
        return Optional.empty();
    }

    @PostMapping
    Car postCar(Car car) {
        carsList.add(car);
        return car;
    }

    @PutMapping("/{id}")
    ResponseEntity<Car> putCar(@PathVariable String id,@RequestBody Car car) {
        int index = -1;
        for(Car carToFind: carsList){
            if(carToFind.getId().equals(id)){
                index = carsList.indexOf(carToFind);
                carsList.set(index, car);
            }
        }
        return (index == -1)?
            new ResponseEntity<>(postCar(car), HttpStatus.CREATED):
            new ResponseEntity<>(car, HttpStatus.OK);
        }

    @DeleteMapping("/{id}")
    void deleteCar(@PathVariable String id) {
        carsList.removeIf(car -> car.getId().equals(id));
    }
}
