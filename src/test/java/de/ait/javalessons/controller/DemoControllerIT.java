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
