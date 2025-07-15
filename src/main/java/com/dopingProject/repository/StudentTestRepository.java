package com.dopingProject.repository;

import com.dopingProject.model.StudentTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTestRepository extends JpaRepository<StudentTest, Long> {
    List<StudentTest> findByStudentId(Long studentId);
    List<StudentTest> findByTestId(Long testId);
}
