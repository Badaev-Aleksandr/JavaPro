package de.ait.javalessons.controller;

import de.ait.DevelopmentGr42Application;
import de.ait.configuration.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Интеграционные тесты для класса DemoController.
 * Этот тестовый класс проверяет корректность и работоспособность различных эндпоинтов DemoController,
 * используя TestRestTemplate для выполнения HTTP-запросов к приложению, работающему в тестовой среде.
 * Тесты гарантируют, что эндпоинты возвращают правильные HTTP-ответы и содержимое
 * в различных сценариях — как при авторизованном, так и при неавторизованном доступе.
 *
 * Аннотации и конфигурация тестов:
 * - @SpringBootTest: Поднимает весь контекст Spring-приложения для интеграционного тестирования.
 * - @ActiveProfiles: Указывает использовать профиль "test" при выполнении этого класса.
 * - @LocalServerPort: Вставляет случайный порт, используемый тестовым сервером.
 *
 * Этот класс также использует следующие пользовательские конфигурации:
 * - DevelopmentGr42Application: Основной класс Spring Boot приложения.
 * - SecurityConfigJDBC: Класс конфигурации, настраивающий правила безопасности и аутентификацию в приложении.
 *
 * Тестовые случаи:
 * - testHomeAPI: Проверяет домашний эндпоинт API на успешный ответ и ожидаемое содержимое.
 * - testPublicInfoAPI: Проверяет публичный API-эндпоинт, доступный без авторизации.
 * - testDashboardPageAPIUnauthorized: Проверяет недопустимость доступа к пользовательской панели без авторизации.
 * - testDashboardPageAPIAuthorized: Проверяет доступ администратора к панели и корректность ответа и содержимого.
 */
@SpringBootTest(classes = {DevelopmentGr42Application.class, SecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DemoControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

   @Test
    void testHomeAPI(){
       String url = "http://localhost:" + port + "/";
       ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       assertThat(response.getBody()).isEqualTo("Главная страница");
   }

    @Test
    void testPublicInfoAPI(){
        String url = "http://localhost:" + port + "/public/info";
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Страница доступна без авторизации");
    }

    @Test
    void testDashboardPageAPIUnauthorized(){
       String url = "http://localhost:" + port + "/user/dashboard";
       ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().contains("Please sing in"));
    }

    @Test
    void testDashboardAPIAuthorized(){
       String url = "http://localhost:" + port + "/admin/dashboard";
       TestRestTemplate adminTemplate = new TestRestTemplate("admin", "adminpass");
       ResponseEntity<String> response = adminTemplate.getForEntity(url, String.class);
       assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
       assertThat(response.getBody().contains("Админский раздел"));
    }
}
