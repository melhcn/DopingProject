package com.dopingProject.service;

import com.dopingProject.model.Student;

public interface StudentService {
    Student registerStudent(Student student);
    Student login(String email, String password);
    Student getStudentById(Long id);
}
