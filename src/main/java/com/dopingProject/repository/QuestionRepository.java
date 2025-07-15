package com.dopingProject.repository;

import com.dopingProject.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long testId);
}
