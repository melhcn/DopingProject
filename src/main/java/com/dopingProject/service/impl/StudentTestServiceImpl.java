package com.dopingProject.service.impl;

import com.dopingProject.model.Question;
import com.dopingProject.model.Student;
import com.dopingProject.model.Test;
import com.dopingProject.model.StudentTest;
import com.dopingProject.repository.StudentRepository;
import com.dopingProject.repository.TestRepository;
import com.dopingProject.repository.StudentTestRepository;
import com.dopingProject.service.StudentTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StudentTestServiceImpl implements StudentTestService {

    private final StudentRepository studentRepository;
    private final TestRepository testRepository;
    private final StudentTestRepository studentTestRepository;

    @Autowired
    public StudentTestServiceImpl(StudentRepository studentRepository,
                                  TestRepository testRepository,
                                  StudentTestRepository studentTestRepository) {
        this.studentRepository = studentRepository;
        this.testRepository = testRepository;
        this.studentTestRepository = studentTestRepository;
    }

    @Override
    public StudentTest enrollStudentToTest(Long studentId, Long testId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Test test = testRepository.findById(testId).orElse(null);

        if (student != null && test != null) {
            StudentTest studentTest = new StudentTest();
            studentTest.setStudent(student);
            studentTest.setTest(test);
            studentTest.setCorrectCount(0);
            studentTest.setWrongCount(0);
            studentTest.setRank(0);
            studentTest.setDateTaken(LocalDateTime.now());
            return studentTestRepository.save(studentTest);
        }
        return null;
    }

    @Override
    public List<StudentTest> getTestsByStudent(Long studentId) {
        return studentTestRepository.findByStudentId(studentId);
    }

    @Override
    public List<StudentTest> getStudentsByTest(Long testId) {
        return studentTestRepository.findByTestId(testId);
    }

        @Override
    public StudentTest finishTest(Long studentTestId, Map<String, String> answersStr) {
    StudentTest st = studentTestRepository.findById(studentTestId).orElse(null);
    if (st != null) {
        int correct = 0;
        int wrong = 0;
        int blank = 0;

        
        Map<Long, String> answers = new java.util.HashMap<>();
        for (Map.Entry<String, String> entry : answersStr.entrySet()) {
            answers.put(Long.parseLong(entry.getKey()), entry.getValue());
        }

        
        for (Question q : st.getTest().getQuestions()) {
            String selected = answers.get(q.getId());
            if (selected == null || selected.isEmpty()) {
                blank++;
            } else if (selected.equals(q.getCorrectAnswer())) {
                correct++;
            } else {
                wrong++;
            }
        }

        double score = correct * 1.0 - wrong * 0.33;

        st.setCorrectCount(correct);
        st.setWrongCount(wrong);
        st.setBlankCount(blank);
        st.setScore(score);
        st.setAnswers(answers);
        st.setDateTaken(LocalDateTime.now());

        return studentTestRepository.save(st);
    }
    return null;
}


}
