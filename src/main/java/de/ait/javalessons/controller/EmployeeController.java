package de.ait.javalessons.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Аннотация, указывающая, что этот класс является контроллером REST API.
@RequestMapping("/employees") // Базовый URL для всех методов в этом контроллере.
public class EmployeeController {

//    @GetMapping("/public/information") // Обрабатывает GET-запросы по URL /employees/public/info
//    public ResponseEntity<String> getPublicInfo() {
//        return ResponseEntity.ok("User Info, public information"); // Возвращает публичную информацию, доступную всем пользователям.
//    }
//
//    @GetMapping("/user/info") // Обрабатывает GET-запросы по URL /employees/user/info
//    public ResponseEntity<String> getUserInfo() {
//        return ResponseEntity.ok("User Info, secured user information"); // Возвращает информацию, доступную только авторизованным пользователям с ролью USER.
//    }
//
//    @GetMapping("/admin/info") // Обрабатывает GET-запросы по URL /employees/admin/info
//    public ResponseEntity<String> getAdminInfo() {
//        return ResponseEntity.ok("Admin Info, secured admin information"); // Возвращает информацию, доступную только авторизованным пользователям с ролью ADMIN.
//    }
}