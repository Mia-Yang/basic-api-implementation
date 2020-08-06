package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_user() throws Exception {
        User user = new User("SiyuYang", "female", 25, "siyu@c.com", "18866688888");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void user_name_should_less_than_8() throws Exception {
        User user = new User("Siyu-Yang", "female", 25, "siyu@c.com", "18866688888");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void user_age_should_between_18_and_100() throws Exception {
        User user = new User("SiyuYang", "female", 5, "siyu@c.com", "18866688888");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void email_should_in_correct_format() throws Exception {
        User user = new User("SiyuYang", "female", 25, "siyuc.com", "18866688888");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void phone_should_in_correct_format() throws Exception {
        User user = new User("SiyuYang", "female", 25, "siyu@c.com", "18888866688888");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_user() throws Exception {
        this.should_register_user();
        mockMvc.perform(get("/user"))
                .andExpect(jsonPath("$[0].user_name", is("SiyuYang")))
                .andExpect(jsonPath("$[0].user_gender", is("female")))
                .andExpect(jsonPath("$[0].user_age", is(25)))
                .andExpect(jsonPath("$[0].user_email", is("siyu@c.com")))
                .andExpect(jsonPath("$[0].user_phone", is("18866688888")))
                .andExpect(status().isOk());
    }

}