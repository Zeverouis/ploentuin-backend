package nl.ploentuin.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @SpringBootTest(
            classes = nl.ploentuin.ploentuin.PloentuinApplication.class,
            webEnvironment = SpringBootTest.WebEnvironment.MOCK
    )
    @AutoConfigureMockMvc
    @ActiveProfiles("test")
    @Sql(scripts = "classpath:data-test.sql")
    class UserControllerIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Test
        void getPublicUser_existingUser_returns200AndUserData() throws Exception {
            mockMvc.perform(get("/users/public/testuser"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data.username").value("testuser"))
                    .andExpect(jsonPath("$.message").value("User gevonden"));
        }
    }