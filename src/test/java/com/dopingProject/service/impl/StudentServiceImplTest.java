package com.dopingProject.service.impl;

import com.dopingProject.model.Student;
import com.dopingProject.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        studentRepository = mock(StudentRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        studentService = new StudentServiceImpl(studentRepository, passwordEncoder);
    }

    @Test
    void registerStudent_encodesPasswordAndSaves() {
        Student student = new Student();
        student.setPassword("plain");

        when(passwordEncoder.encode("plain")).thenReturn("hashed");
        when(studentRepository.save(any(Student.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Student saved = studentService.registerStudent(student);

        assertEquals("hashed", saved.getPassword());

        ArgumentCaptor<Student> captor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(captor.capture());
        assertEquals("hashed", captor.getValue().getPassword());
    }

    @Test
    void login_whenPasswordMatches_returnsStudent() {
        Student student = new Student();
        student.setPassword("hashed");

        when(studentRepository.findByEmail("john@example.com")).thenReturn(student);
        when(passwordEncoder.matches("plain", "hashed")).thenReturn(true);

        Student loggedIn = studentService.login("john@example.com", "plain");

        assertNotNull(loggedIn);
        assertEquals(student, loggedIn);
    }

    @Test
    void login_whenPasswordDoesNotMatch_returnsNull() {
        Student student = new Student();
        student.setPassword("hashed");

        when(studentRepository.findByEmail("john@example.com")).thenReturn(student);
        when(passwordEncoder.matches("plain", "hashed")).thenReturn(false);

        Student loggedIn = studentService.login("john@example.com", "plain");

        assertNull(loggedIn);
    }

    @Test
    void login_whenUserNotFound_returnsNull() {
        when(studentRepository.findByEmail("john@example.com")).thenReturn(null);

        Student loggedIn = studentService.login("john@example.com", "plain");

        assertNull(loggedIn);
    }

    @Test
    void getStudentById_returnsStudent() {
        Student student = new Student();
        student.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student found = studentService.getStudentById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void getStudentById_notFound_returnsNull() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Student found = studentService.getStudentById(1L);

        assertNull(found);
    }
}
