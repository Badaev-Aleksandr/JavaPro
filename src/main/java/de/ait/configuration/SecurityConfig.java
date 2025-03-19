package de.ait.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // Аннотация, указывающая, что этот класс содержит конфигурацию Spring.
public class SecurityConfig {

    @Bean // Аннотация, указывающая, что метод возвращает bean, который должен быть управляем Spring контейнером.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Создает и возвращает bean для кодирования паролей с использованием BCrypt.
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        // Создает пользователя с именем "user", паролем "userpass" и ролью USER.
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("userpass")) // Пароль кодируется с использованием BCrypt.
                .roles("USER")
                .build();

        // Создает пользователя с именем "admin", паролем "adminpass" и ролью ADMIN.
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("adminpass")) // Пароль кодируется с использованием BCrypt.
                .roles("ADMIN")
                .build();

        // Возвращает менеджер пользователей, который хранит пользователей в памяти.
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Отключает CSRF защиту (обычно используется для REST API).
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(("/employees/public/**")).permitAll() // Разрешает доступ к /employees/public/** всем пользователям.
                        .requestMatchers(("/employees/user/**")).hasRole("USER") // Разрешает доступ к /employees/user/** только пользователям с ролью USER.
                        .requestMatchers(("/employees/admin/**")).hasRole("ADMIN") // Разрешает доступ к /employees/admin/** только пользователям с ролью ADMIN.
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации.
                )
                .formLogin(withDefaults()); // Включает форму входа по умолчанию.

        return http.build(); // Строит и возвращает цепочку фильтров безопасности.
    }
}