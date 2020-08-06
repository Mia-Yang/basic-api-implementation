package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ErrorHandlingTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_throw_index_exception() throws Exception {
        mockMvc.perform(get("/rs/0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }

    @Test
    public void should_throw_method_not_valid_exception() throws Exception {
        //String jsonString = "{\"eventName\":\"添加一条热搜\",\"keyWord\":\"娱乐\"，\"user\":{\"userName\":\"SiyuYang678\",\"gender\":\"female\",\"age\":25, \"email\":\"siyu@c.com\",\"phone\":\"18866688888\"}}";

        User newUser = new User("Siyuyuyuyu", "female", 25, "siyu@c.com", "18866688888");
        RsEvent rsEvent = new RsEvent("添加一条热搜", "娱乐", newUser);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
    }

    @Test
    void should_throw_index_out_of_bounds_exception() throws Exception {
        mockMvc.perform(get("/rs/list?start=-1&end=2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }

    @Test
    void should_throw_index_invalid_request_param_exception() throws Exception {
        mockMvc.perform(get("/rs/list?start=5&end=1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }


    @Test
    public void should_throw_invalid_user_exception() throws Exception {
        User user = new User("SiyuYang77889", "female", 25, "siyu@c.com", "18866688888");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/user").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
    }
}
