package com.bridgelabz.addressbookapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddressBookApiTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;

    @Test
    void crudAndMissingId() throws Exception {
        String body = mvc.perform(post("/addressbookservice/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Lisa\",\"phoneNumber\":\"1234567890\",\"city\":\"Pune\"}"))
                .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
        long id = json.readTree(body).get("id").asLong();
        mvc.perform(get("/addressbookservice/get/{id}", id))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Lisa"));
        mvc.perform(put("/addressbookservice/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Mark\",\"phoneNumber\":\"9876543210\",\"city\":\"Mumbai\"}"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.city").value("Mumbai"));
        mvc.perform(delete("/addressbookservice/delete/{id}", id)).andExpect(status().isNoContent());
        mvc.perform(get("/addressbookservice/get/{id}", id))
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.error").exists());
    }

    @Test
    void rejectsInvalidCreateAndUpdate() throws Exception {
        mvc.perform(post("/addressbookservice/create")
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Ma\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").exists());
        mvc.perform(put("/addressbookservice/update/1")
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").exists());
    }
}
