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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Проверяем, что публичный эндпоинт доступен всем и возвращает правильный контент")
    void testGetPublicProductsList() throws Exception {
        mockMvc.perform(get("/products/public/list"))
                .andExpect(status().isOk())
                .andExpect(content().string("Products: Radio, Handy, Computer, Alexa"));
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /products/customer/cart")
    class CustomerCartTests{

        @Test
        @DisplayName("Когда пользователь авторизован с ролью CUSTOMER, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testCustomer",roles = {"CUSTOMER"})
        void testGetCustomerCartAsCustomer() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Customer cart: Handy"));
        }

        @Test
        @DisplayName("Когда пользователь не авторизован")
        void testGetCustomerCartAsAnonymous() throws Exception {
            mockMvc.perform(get("/products/customer/cart"))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Nested
    @DisplayName("Тесты для эндпоинта /products/manager/add")
    class ManagerAddTests {

        @Test
        @DisplayName("Когда пользователь авторизован с ролью MANAGER, возвращается статус 200 и нужный контент")
        @WithMockUser(username = "testManager", roles = {"MANAGER"})
        void testGetManagerAddAsManager() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Neu Product: AirPods"));
        }

        @Test
        @DisplayName("Когда пользователь не имеет роли MANAGER, возвращается ошибка 403")
        @WithMockUser(username = "testCustomer",roles = {"CUSTOMER"})
        void testGetManagerAddAsCustomer() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("Когда пользователь не авторизован, возвращается ошибка 401 (или 403)")
        void testGetManagerAddAsAnonymous() throws Exception {
            mockMvc.perform(get("/products/manager/add"))
                    .andExpect(status().is3xxRedirection());
        }
    }
}
