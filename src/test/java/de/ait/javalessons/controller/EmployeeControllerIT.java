package de.ait.javalessons.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Проверяем, что публичный эндпоинт доступен всем и возвращает правильный контент")
    void testGetPublicInfo() throws Exception {
        mockMvc.perform(get("/employees/public/info"))
                .andExpect(status().isOk())
                .andExpect(content().string("User Info, public information"));
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /employees/user/info")
    class UserInfoTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью USER, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testUser", roles = {"USER"})
        void testGetUserInfoAsUser() throws Exception {
            mockMvc.perform(get("/employees/user/info"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("User Info, secured user information"));
        }

        @Test
        @DisplayName("Когда пользователь не авторизован, ")
        void testGetUserInfoAsAnonymous() throws Exception {
            mockMvc.perform(get("/employees/user/info"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /employees/admin/info")
    class AdminInfoTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью ADMIN, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "adminUser", roles = {"ADMIN"})
        void testGetAdminInfoAsAdmin() throws Exception {
            mockMvc.perform(get("/employees/admin/info"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Admin Info, secured admin information"));
        }

        @Test
        @DisplayName("Когда пользователь не имеет роли ADMIN, возвращается ошибка 403")
        @WithMockUser(username = "testUser", roles = {"USER"})
        void testGetAdminInfoAsUser() throws Exception {
            mockMvc.perform(get("/employees/admin/info"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Когда пользователь не авторизован, возвращается ошибка 401 (или 403)")
        void testGetAdminInfoAsAnonymous() throws Exception {
            mockMvc.perform(get("/employees/admin/info"))
                    .andExpect(status().is3xxRedirection());
        }
    }
}
