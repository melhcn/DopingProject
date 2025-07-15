package com.dopingProject.controller;

import com.dopingProject.model.Question;
import com.dopingProject.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuestionService questionService;

    @Test
    void testGetAllQuestions() throws Exception {
        List<Question> questions = Arrays.asList(
            new Question(1L, "What is Java?", null),
            new Question(2L, "What is Spring?", null)
        );

        when(questionService.getAllQuestions()).thenReturn(questions);

        mockMvc.perform(get("/questions"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].content").value("What is Java?"));
    }

    @Test
    void testGetQuestionById_Found() throws Exception {
        Question question = new Question(1L, "What is Java?", null);

        when(questionService.getQuestionById(1L)).thenReturn(question);

        mockMvc.perform(get("/questions/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.content").value("What is Java?"));
    }

    @Test
    void testGetQuestionById_NotFound() throws Exception {
        when(questionService.getQuestionById(99L)).thenReturn(null);

        mockMvc.perform(get("/questions/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testCreateQuestion_Success() throws Exception {
        
        Question savedQuestion = new Question(3L, "New question?", null);

        when(questionService.createQuestion(eq(1L), any(Question.class))).thenReturn(savedQuestion);

        mockMvc.perform(post("/questions")
                .param("testId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "content": "New question?"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3L))
            .andExpect(jsonPath("$.content").value("New question?"));
    }

    @Test
    void testCreateQuestion_BadRequest() throws Exception {
        when(questionService.createQuestion(eq(1L), any(Question.class))).thenReturn(null);

        mockMvc.perform(post("/questions")
                .param("testId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "content": "New question?"
                    }
                """))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Test not found"));
    }
}
