package de.ait.repositories;

import de.ait.javalessons.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, String> {

}
