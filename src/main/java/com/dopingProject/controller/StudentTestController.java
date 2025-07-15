package com.dopingProject.controller;

import com.dopingProject.model.StudentTest;
import com.dopingProject.service.StudentTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dopingProject.dto.FinishTestRequest;

import java.util.List;

@RestController
@RequestMapping("/student-tests")
public class StudentTestController {

    private final StudentTestService studentTestService;

    @Autowired
    public StudentTestController(StudentTestService studentTestService) {
        this.studentTestService = studentTestService;
    }

    
    @PostMapping("/enroll")
    public ResponseEntity<StudentTest> enrollStudentToTest(@RequestParam Long studentId,
                                                           @RequestParam Long testId) {
        StudentTest st = studentTestService.enrollStudentToTest(studentId, testId);
        if (st != null) {
            return ResponseEntity.ok(st);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/finish")
public ResponseEntity<?> finishTest(@RequestBody FinishTestRequest request) {
    StudentTest st = studentTestService.finishTest(
        request.getStudentTestId(),
        request.getAnswers()
    );
    if (st != null) {
        return ResponseEntity.ok(st);
    } else {
        return ResponseEntity.badRequest().body("StudentTest record not found");
    }
}

    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentTest>> getTestsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentTestService.getTestsByStudent(studentId));
    }

    
    @GetMapping("/test/{testId}")
    public ResponseEntity<List<StudentTest>> getStudentsByTest(@PathVariable Long testId) {
        return ResponseEntity.ok(studentTestService.getStudentsByTest(testId));
    }
}
