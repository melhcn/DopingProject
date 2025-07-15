package com.dopingProject.service.impl;

import com.dopingProject.model.Question;
import com.dopingProject.repository.QuestionRepository;
import com.dopingProject.repository.TestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private QuestionRepository questionRepository;
    private TestRepository testRepository;
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        questionRepository = mock(QuestionRepository.class);
        testRepository = mock(TestRepository.class);
        questionService = new QuestionServiceImpl(questionRepository, testRepository);
    }

    @Test
    void createQuestion_whenTestExists_savesQuestion() {
        com.dopingProject.model.Test test = new com.dopingProject.model.Test();
        test.setId(1L);

        Question question = new Question();
        question.setContent("What is Java?");

        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        when(questionRepository.save(any(Question.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Question saved = questionService.createQuestion(1L, question);

        assertNotNull(saved);
        assertEquals(test, saved.getTest());
        assertEquals("What is Java?", saved.getContent());

        ArgumentCaptor<Question> captor = ArgumentCaptor.forClass(Question.class);
        verify(questionRepository).save(captor.capture());
        assertEquals(test, captor.getValue().getTest());
    }

    @Test
    void createQuestion_whenTestNotExists_returnsNull() {
        Question question = new Question();
        when(testRepository.findById(1L)).thenReturn(Optional.empty());

        Question saved = questionService.createQuestion(1L, question);

        assertNull(saved);
        verify(questionRepository, never()).save(any());
    }

    @Test
    void getQuestionById_returnsQuestion() {
        Question question = new Question();
        question.setId(5L);

        when(questionRepository.findById(5L)).thenReturn(Optional.of(question));

        Question found = questionService.getQuestionById(5L);

        assertNotNull(found);
        assertEquals(5L, found.getId());
    }

    @Test
    void getAllQuestions_returnsList() {
        when(questionRepository.findAll()).thenReturn(List.of(
                new Question(1L, "Q1", null),
                new Question(2L, "Q2", null)
        ));

        List<Question> list = questionService.getAllQuestions();

        assertEquals(2, list.size());
        assertEquals("Q1", list.get(0).getContent());
    }
}
