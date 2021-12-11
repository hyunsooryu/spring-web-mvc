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

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SessionAttributeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void sessionTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/session/events/form"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("/events/form"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("event"));
                //.andExpect(MockMvcResultMatchers.request().sessionAttribute("event",Matchers.notNullValue()))
                //.andReturn().getRequest().getSession().getAttribute("event");

    }
}
