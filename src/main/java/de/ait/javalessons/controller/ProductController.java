package de.ait.javalessons.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/public/list")
    ResponseEntity<String> getProducts() {
        return ResponseEntity.ok("Products: Radio, Handy, Computer, Alexa");
    }

    @GetMapping("/customer/cart")
    ResponseEntity<String> getCart() {
        return ResponseEntity.ok("Customer cart: Handy");
    }

    @GetMapping("/manager/add")
    ResponseEntity<String> addProduct() {
        return ResponseEntity.ok("Neu Product: AirPods");
    }
}
