package com.dopingProject.service;

import com.dopingProject.model.StudentTest;
import java.util.List;
import java.util.Map;

public interface StudentTestService {
    StudentTest enrollStudentToTest(Long studentId, Long testId);
    List<StudentTest> getTestsByStudent(Long studentId);
    List<StudentTest> getStudentsByTest(Long testId);
    StudentTest finishTest(Long studentTestId, Map<String, String> answers);
}
