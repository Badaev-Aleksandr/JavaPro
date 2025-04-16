package de.ait.javalessons.controller;

import de.ait.dto.RegistrationDto;
import de.ait.javalessons.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final RegistrationService registrationService;

    public RegisterController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("regForm", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("regForm") RegistrationDto registrationDto,
                                  Model model) {
        try {
            registrationService.registerUser(registrationDto.getUsername(), registrationDto.getPassword());
            return "redirect:/login";
        } catch (Exception exception) {
            model.addAttribute("error", exception.getMessage());
            return "register";
        }
    }
}
