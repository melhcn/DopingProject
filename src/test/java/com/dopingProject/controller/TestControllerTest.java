package com.dopingProject.controller;

import com.dopingProject.service.TestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestController.class)
class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TestService testService;

    @Test
    void getAllTests_returnsList() throws Exception {
        com.dopingProject.model.Test t1 = new com.dopingProject.model.Test();
        t1.setId(1L);
        t1.setTestName("Test1");

        com.dopingProject.model.Test t2 = new com.dopingProject.model.Test();
        t2.setId(2L);
        t2.setTestName("Test2");

        Mockito.when(testService.getAllTests()).thenReturn(List.of(t1, t2));

        mockMvc.perform(get("/tests"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("Test1"))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].name").value("Test2"));
    }

    @Test
    void getTestById_found_returnsTest() throws Exception {
        com.dopingProject.model.Test t = new com.dopingProject.model.Test();
        t.setId(1L);
        t.setTestName("Sample Test");

        Mockito.when(testService.getTestById(1L)).thenReturn(t);

        mockMvc.perform(get("/tests/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Sample Test"));
    }

    @Test
    void getTestById_notFound_returns404() throws Exception {
        Mockito.when(testService.getTestById(99L)).thenReturn(null);

        mockMvc.perform(get("/tests/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void createTest_savesAndReturnsTest() throws Exception {
        com.dopingProject.model.Test t = new com.dopingProject.model.Test();
        t.setId(1L);
        t.setTestName("New Test");

        Mockito.when(testService.createTest(any(com.dopingProject.model.Test.class))).thenReturn(t);

        mockMvc.perform(post("/tests")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "New Test"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("New Test"));
    }
}
