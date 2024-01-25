package edu.cbsystematics.com.accountingemployeesproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class HttpRequestTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test GET /departments endpoint returns OK status")
    void testDepartmentsEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/departments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        printTestResult(result);
    }

    @Test
    @DisplayName("Test GET /positions endpoint returns OK status")
    void testPositionsEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/positions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        printTestResult(result);
    }

    @Test
    @DisplayName("Test GET /periods endpoint returns OK status")
    void testPeriodsEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/periods"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        printTestResult(result);
    }

    @Test
    @DisplayName("Test GET /employees endpoint returns OK status")
    void testEmployeesEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        printTestResult(result);
    }

    @Test
    @DisplayName("Test GET /pageable endpoint returns OK status")
    void testPageableEndpoint() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pageable"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        printTestResult(result);
    }

    private void printTestResult(MvcResult result) {
        System.out.println("Method: " + result.getRequest().getMethod());
        System.out.println("URI: " + result.getRequest().getRequestURI());
        System.out.println("Status: " + result.getResponse().getStatus());
    }
}