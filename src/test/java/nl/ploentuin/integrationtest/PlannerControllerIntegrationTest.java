package nl.ploentuin.integrationtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(
        classes = nl.ploentuin.ploentuin.PloentuinApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "classpath:data-test.sql")
public class PlannerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPlannerCatalog_returnsCatalogItems() throws Exception {
        mockMvc.perform(get("/planner/catalog"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].name").value("Tomato"));
    }
}
