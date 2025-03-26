package de.ait.javalessons.service;

import de.ait.javalessons.model.User;
import de.ait.javalessons.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user registration functionality.
 * This class handles creating new user accounts, encoding passwords,
 * assigning default roles, and persisting users into the database.
 *
 * Key Responsibilities:
 * - Register new users by providing a username and raw password.
 * - Encode passwords using the provided {@link PasswordEncoder}.
 * - Assign default roles to newly registered users.
 * - Persist user details into the database using the {@link UserRepository}.
 *
 * Dependencies:
 * - {@link UserRepository}: For managing user persistence operations.
 * - {@link PasswordEncoder}: For secure password encoding.
 */
/**
 * Сервисный класс, отвечающий за функциональность регистрации пользователей.
 * Этот класс обрабатывает создание новых учетных записей, хеширование паролей,
 * назначение ролей по умолчанию и сохранение пользователей в базе данных.
 *
 * Основные задачи:
 * - Регистрировать новых пользователей, принимая имя пользователя и "сырой" пароль.
 * - Кодировать (хешировать) пароли с использованием переданного {@link PasswordEncoder}.
 * - Назначать роли по умолчанию новым зарегистрированным пользователям.
 * - Сохранять данные пользователя в базе данных через {@link UserRepository}.
 *
 * Зависимости:
 * - {@link UserRepository}: для операций сохранения/чтения пользователей из базы.
 * - {@link PasswordEncoder}: для безопасного кодирования паролей.
 */
@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public  RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add("ROLE_USER");
        userRepository.save(user);
    }
}
