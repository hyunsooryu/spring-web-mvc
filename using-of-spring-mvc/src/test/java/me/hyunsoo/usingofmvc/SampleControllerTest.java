package me.hyunsoo.usingofmvc;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest
class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("@RequestMapping에 method가 없을 시 Get 방식의 요청을 처리하는 테스트입니다.")
    void helloTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/hello"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.is("hello")));
    }

    @Test
    @DisplayName("@GetMapping에 headers = HttpHeaders.ACCEPT 일 때의 테스트입니다.")
    void byeTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/bye").header(HttpHeaders.FROM, "house"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("@GetMapping에 headers = HttpHeaders.ACCEPT 일 때의 테스트입니다.")
    void goodTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/good").header(HttpHeaders.FROM, "house").param("name","spring"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName("@GetMapping에 headers = HttpHeaders.ACCEPT 일 때의 테스트입니다.")
    void optionsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.options("/good"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());

    }




}