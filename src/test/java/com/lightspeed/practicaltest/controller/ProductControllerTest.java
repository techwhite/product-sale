package com.lightspeed.practicaltest.controller;

import com.lightspeed.practicaltest.service.ProductService;
import com.lightspeed.practicaltest.util.ProductConverter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// currently only used to check return http status since data is mocked
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    private final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbmR5LnlvdSIsImlhdCI6MTcwNDU5NDY1M30" +
            ".RdBMCIUlpZJJ0-mHWNwwzJc70cuNOR2BoTz385GBtOA";
    private static final Logger logger = LoggerFactory.getLogger(ProductControllerTest.class);

    // Http mock tool
    @Autowired
    MockMvc mockMvc;

    // Should mock these beans since ProductController depends on it.
    @MockBean
    ProductService service;
    @MockBean
    ProductConverter converter;

    @Test
    public void testGetAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products?page=1&size=2")
                        .header("token", token)
                ).andExpect(status().isOk());
    }

    @Test
    public void testSaveProduct() throws Exception {
        // this case can't be processed since mock productservice might return result randomly
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/products")
//                .content("{\n" +
//                        "    \"name\": \"Andy You\",\n" +
//                        "    \"price\": 1023\n" +
//                        "}")
//                .header("token", token)
//                .contentType("application/json")
//        ).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content("{\n" +
                        "    \"price\": 1023\n" +
                        "}")
                .header("token", token)
                .contentType("application/json")
        ).andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/products")
                .content("{\n" +
                        "    \"name\": \"Andy You\",\n" +
                        "    \"price\": -1.0\n" +
                        "}")
                .header("token", token)
                .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }

/*    @Test
    public void testGetProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/products/12345")
                .header("token", token)
        ).andExpect(status().isOk());
    }

    @Test
    public void testDeleteProductById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/12345")
                .header("token", token)
        ).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/products/-1")
                .header("token", token)
        ).andExpect(status().isBadRequest())
                .andExpect(content().string("{\"code\":400,\"status\":\"BAD_REQUEST\",\"message\":\"id should be more than 0!\",\"stackTrace\":null,\"data\":null}"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/products")
                .content("{\n" +
                        "    \"name\": \"testUpdateProduct\",\n" +
                        "    \"price\": 1023\n" +
                        "}")
                .header("token", token)
                .contentType("application/json")
        ).andExpect(status().isUnprocessableEntity());

        mockMvc.perform(MockMvcRequestBuilders
                .put("/products")
                .content("{\n" +
                        "    \"price\": 1023\n" +
                        "}")
                .header("token", token)
                .contentType("application/json")
        ).andExpect(status().isBadRequest());

        mockMvc.perform(MockMvcRequestBuilders
                .put("/products")
                .content("{\n" +
                        "    \"name\": \"Andy You\",\n" +
                        "    \"price\": -1.0\n" +
                        "}")
                .header("token", token)
                .contentType("application/json")
        ).andExpect(status().isBadRequest());
    }*/
}
