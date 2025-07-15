package com.dopingProject.service.impl;

import com.dopingProject.model.Question;
import com.dopingProject.model.Test;
import com.dopingProject.repository.QuestionRepository;
import com.dopingProject.repository.TestRepository;
import com.dopingProject.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository,
                               TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    @Override
    public Question createQuestion(Long testId, Question question) {
        Test test = testRepository.findById(testId).orElse(null);
        if (test == null) return null;
        question.setTest(test);
        return questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
}
