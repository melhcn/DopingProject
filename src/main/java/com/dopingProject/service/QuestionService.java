package com.dopingProject.service;

import com.dopingProject.model.Question;
import java.util.List;

public interface QuestionService {
    Question createQuestion(Long testId, Question question);
    Question getQuestionById(Long id);
    List<Question> getAllQuestions();
}
