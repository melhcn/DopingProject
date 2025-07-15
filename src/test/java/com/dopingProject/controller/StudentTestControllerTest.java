package com.dopingProject.controller;

import com.dopingProject.model.StudentTest;
import com.dopingProject.service.StudentTestService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentTestController.class)
class StudentTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentTestService studentTestService;

    @Test
    void enrollStudentToTest_success() throws Exception {
        StudentTest st = new StudentTest();
        st.setId(1L);

        Mockito.when(studentTestService.enrollStudentToTest(1L, 2L)).thenReturn(st);

        mockMvc.perform(post("/student-tests/enroll")
                .param("studentId", "1")
                .param("testId", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void enrollStudentToTest_badRequest() throws Exception {
        Mockito.when(studentTestService.enrollStudentToTest(1L, 2L)).thenReturn(null);

        mockMvc.perform(post("/student-tests/enroll")
                .param("studentId", "1")
                .param("testId", "2"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void finishTest_success() throws Exception {
        StudentTest st = new StudentTest();
        st.setId(1L);

        Mockito.when(studentTestService.finishTest(eq(1L), any())).thenReturn(st);

        mockMvc.perform(post("/student-tests/finish")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "studentTestId": 1,
                        "answers": ["A", "B", "C"]
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void finishTest_badRequest() throws Exception {
        Mockito.when(studentTestService.finishTest(eq(1L), any())).thenReturn(null);

        mockMvc.perform(post("/student-tests/finish")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "studentTestId": 1,
                        "answers": ["A", "B", "C"]
                    }
                """))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("StudentTest record not found"));
    }

    @Test
    void getTestsByStudent_returnsList() throws Exception {
        StudentTest st = new StudentTest();
        st.setId(1L);

        Mockito.when(studentTestService.getTestsByStudent(1L))
                .thenReturn(List.of(st));

        mockMvc.perform(get("/student-tests/student/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getStudentsByTest_returnsList() throws Exception {
        StudentTest st = new StudentTest();
        st.setId(2L);

        Mockito.when(studentTestService.getStudentsByTest(5L))
                .thenReturn(List.of(st));

        mockMvc.perform(get("/student-tests/test/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].id").value(2L));
    }
}
