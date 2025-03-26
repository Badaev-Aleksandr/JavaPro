package de.ait.configuration;

import de.ait.javalessons.model.User;
import de.ait.javalessons.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
/**
 * Класс, отвечающий за инициализацию пользовательских данных в приложении.
 * Обеспечивает создание учетных записей по умолчанию — "admin" и "user" — в базе данных,
 * если они ещё не существуют. Полезен для настройки начальных данных при запуске приложения.
 *
 * Основные функции:
 * - Создает пользователя "admin" с ролью "ROLE_ADMIN", если он отсутствует в базе данных.
 * - Создает пользователя "user" с ролью "ROLE_USER", если он отсутствует в базе данных.
 * - Шифрует пароли для создаваемых пользователей с помощью переданного {@link PasswordEncoder}.
 *
 * Зависимости:
 * - {@link UserRepository}: используется для доступа к данным пользователей и их сохранения.
 * - {@link PasswordEncoder}: используется для шифрования паролей перед сохранением.
 */
@Component
public class UserDataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if(userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.getRoles().add("ROLE_ADMIN");
            admin.getRoles().add("ROLE_USER");
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("userpass"));
            user.getRoles().add("ROLE_USER");
            userRepository.save(user);
        }
    }
}
