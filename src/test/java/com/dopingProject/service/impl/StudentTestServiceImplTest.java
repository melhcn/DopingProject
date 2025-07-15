package com.dopingProject.service.impl;

import com.dopingProject.model.Question;
import com.dopingProject.model.Student;
import com.dopingProject.model.StudentTest;
import com.dopingProject.repository.StudentRepository;
import com.dopingProject.repository.TestRepository;
import com.dopingProject.repository.StudentTestRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentTestServiceImplTest {

    private StudentRepository studentRepository;
    private TestRepository testRepository;
    private StudentTestRepository studentTestRepository;
    private StudentTestServiceImpl studentTestService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        testRepository = mock(TestRepository.class);
        studentTestRepository = mock(StudentTestRepository.class);
        studentTestService = new StudentTestServiceImpl(studentRepository, testRepository, studentTestRepository);
    }

    @Test
    void enrollStudentToTest_savesStudentTest_whenStudentAndTestExist() {
        Student student = new Student();
        student.setId(1L);
        com.dopingProject.model.Test test = new com.dopingProject.model.Test();
        test.setId(2L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(testRepository.findById(2L)).thenReturn(Optional.of(test));
        when(studentTestRepository.save(any(StudentTest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StudentTest saved = studentTestService.enrollStudentToTest(1L, 2L);

        assertNotNull(saved);
        assertEquals(student, saved.getStudent());
        assertEquals(test, saved.getTest());
        assertEquals(0, saved.getCorrectCount());

        ArgumentCaptor<StudentTest> captor = ArgumentCaptor.forClass(StudentTest.class);
        verify(studentTestRepository).save(captor.capture());
        assertEquals(student, captor.getValue().getStudent());
    }

    @Test
    void enrollStudentToTest_returnsNull_whenStudentOrTestNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentTest result = studentTestService.enrollStudentToTest(1L, 2L);
        assertNull(result);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(testRepository.findById(2L)).thenReturn(Optional.empty());
        result = studentTestService.enrollStudentToTest(1L, 2L);
        assertNull(result);
    }

    @Test
    void getTestsByStudent_returnsList() {
        when(studentTestRepository.findByStudentId(1L)).thenReturn(List.of(new StudentTest()));

        List<StudentTest> list = studentTestService.getTestsByStudent(1L);

        assertEquals(1, list.size());
    }

    @Test
    void getStudentsByTest_returnsList() {
        when(studentTestRepository.findByTestId(2L)).thenReturn(List.of(new StudentTest()));

        List<StudentTest> list = studentTestService.getStudentsByTest(2L);

        assertEquals(1, list.size());
    }

    @Test
    void finishTest_calculatesAndSaves() {
        com.dopingProject.model.Test test = new com.dopingProject.model.Test();

        Question q1 = new Question(1L, "Q1", test);
        q1.setCorrectAnswer("A");

        Question q2 = new Question(2L, "Q2", test);
        q2.setCorrectAnswer("B");

        test.setQuestions(List.of(q1, q2));

        StudentTest st = new StudentTest();
        st.setId(10L);
        st.setTest(test);

        Map<String, String> answers = Map.of(
                "1", "A", 
                "2", "C"  
        );

        when(studentTestRepository.findById(10L)).thenReturn(Optional.of(st));
        when(studentTestRepository.save(any(StudentTest.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StudentTest finished = studentTestService.finishTest(10L, answers);

        assertNotNull(finished);
        assertEquals(1, finished.getCorrectCount());
        assertEquals(1, finished.getWrongCount());
        assertEquals(0, finished.getBlankCount());
        assertEquals(1.0 - 0.33, finished.getScore());
    }

    @Test
    void finishTest_returnsNull_whenNotFound() {
        when(studentTestRepository.findById(10L)).thenReturn(Optional.empty());

        StudentTest result = studentTestService.finishTest(10L, Map.of());

        assertNull(result);
    }
}
