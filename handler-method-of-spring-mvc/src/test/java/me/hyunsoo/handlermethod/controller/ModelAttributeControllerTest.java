package me.hyunsoo.handlermethod.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ModelAttributeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getEventTest() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.post("/model/event")
                .param("id", "0")
                .param("name","")
        ).andExpect(MockMvcResultMatchers.jsonPath("id").value("0"))
         .andExpect(MockMvcResultMatchers.jsonPath("name").value(""))
         .andDo(MockMvcResultHandlers.print());
    }
}