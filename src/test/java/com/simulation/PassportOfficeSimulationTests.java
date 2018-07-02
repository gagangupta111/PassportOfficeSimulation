package com.simulation;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = {PassportOfficeSimulationTests.class})
@ContextConfiguration(classes = {DemoApplication.class})
public class PassportOfficeSimulationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_a_Create() throws Exception {

        this.mockMvc.perform(post("/person/save").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"Person1\"\n" +
                        "}"))
                .andDo(print())
                .andExpect(content().string(containsString("Person1")));
    }

    @Test
    public void test_b_Get() throws Exception {

        this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(content().string(containsString("Person1")));
    }

    @Test
    public void test_c_Get() throws Exception {

        this.mockMvc.perform(get("/personOrderBy/queueAdditionDesc"))
                .andDo(print())
                .andExpect(content().string(containsString("Person1")));
    }

    @Test
    public void test_d_Get() throws Exception {

        this.mockMvc.perform(get("/personOrderBy/totalTimeDesc"))
                .andDo(print())
                .andExpect(content().string(containsString("Person1")));
    }

}