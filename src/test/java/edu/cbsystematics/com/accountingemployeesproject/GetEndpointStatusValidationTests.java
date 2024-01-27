package edu.cbsystematics.com.accountingemployeesproject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;


@SpringBootTest
@AutoConfigureMockMvc
class GetEndpointStatusValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest(name = "=> JUnit: Test GET {0} endpoint returns OK status")
    @DisplayName("=> JUnit: Test GET endpoints return OK status")
    @MethodSource("endpointsProvider")
    void testEndpoints(String endpoint) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
                // Verify that the response status is OK (200)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        printTestResult(result);
    }

    // Provide a stream of arguments for parameterized testing
    private static Stream<Arguments> endpointsProvider() {
        return Stream.of(
                Arguments.of("/departments"),
                Arguments.of("/positions"),
                Arguments.of("/periods"),
                Arguments.of("/employees"),
                Arguments.of("/pageable")
        );
    }

    // Print details
    private void printTestResult(MvcResult result) {
        System.out.println("Method: " + result.getRequest().getMethod());
        System.out.println("URI: " + result.getRequest().getRequestURI());
        System.out.println("Status: " + result.getResponse().getStatus());
    }


}