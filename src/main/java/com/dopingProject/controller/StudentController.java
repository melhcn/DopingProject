package com.dopingProject.controller;

import com.dopingProject.dto.StudentLoginResponse;
import com.dopingProject.model.Student;
import com.dopingProject.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        Student saved = studentService.registerStudent(student);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Student student = studentService.login(email, password);
        if (student != null) {
            StudentLoginResponse dto = new StudentLoginResponse(
                student.getId(),
                student.getName(),
                student.getSurname(),
                student.getStudentNo(),
                student.getEmail()
            );
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
