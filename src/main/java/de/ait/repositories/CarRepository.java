package de.ait.repositories;

import de.ait.javalessons.model.Car;
import org.springframework.data.repository.CrudRepository;

// Интерфейс репозитория для работы с сущностью Car
// Repository interface for working with the Car entity
public interface CarRepository extends CrudRepository<Car, String> {
// Расширяет CrudRepository, предоставляя базовые CRUD-операции для Car
// Extends CrudRepository, providing basic CRUD operations for Car
}
