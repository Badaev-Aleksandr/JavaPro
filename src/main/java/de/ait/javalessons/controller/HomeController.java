package de.ait.javalessons.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";//переход на шаблон home.html
    }

    @GetMapping("/public/info")
    public String info() {
        return "home";//переход на шаблон home.html
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/profile")
    public String userProfile() {
        return "user"; //переход на шаблон user.html
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin";//переход на шаблон admin.html
    }
}

