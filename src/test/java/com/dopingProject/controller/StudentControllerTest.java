package com.dopingProject.controller;

import com.dopingProject.model.Student;
import com.dopingProject.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void registerStudent_returnsSavedStudent() throws Exception {
        Student saved = new Student(1L, "John", "Doe", "12345", "john@example.com", "pass");

        Mockito.when(studentService.registerStudent(any(Student.class))).thenReturn(saved);

        mockMvc.perform(post("/students/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name": "John",
                        "surname": "Doe",
                        "studentNo": "12345",
                        "email": "john@example.com",
                        "password": "pass"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void login_validCredentials_returnsDto() throws Exception {
        Student student = new Student(1L, "John", "Doe", "12345", "john@example.com", "pass");

        Mockito.when(studentService.login(eq("john@example.com"), eq("pass"))).thenReturn(student);

        mockMvc.perform(post("/students/login")
                .param("email", "john@example.com")
                .param("password", "pass"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void login_invalidCredentials_returnsUnauthorized() throws Exception {
        Mockito.when(studentService.login(eq("john@example.com"), eq("wrong")))
                .thenReturn(null);

        mockMvc.perform(post("/students/login")
                .param("email", "john@example.com")
                .param("password", "wrong"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Invalid credentials"));
    }

    @Test
    void getStudentById_found_returnsStudent() throws Exception {
        Student student = new Student(1L, "John", "Doe", "12345", "john@example.com", "pass");

        Mockito.when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void getStudentById_notFound_returns404() throws Exception {
        Mockito.when(studentService.getStudentById(99L)).thenReturn(null);

        mockMvc.perform(get("/students/99"))
            .andExpect(status().isNotFound());
    }
}
