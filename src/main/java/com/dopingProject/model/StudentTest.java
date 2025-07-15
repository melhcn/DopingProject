package com.dopingProject.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "student_tests")
public class StudentTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int correctCount;
    private int wrongCount;
    private int blankCount;   
    private double score;     
    private int rank;
    private LocalDateTime dateTaken;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnoreProperties("studentTests")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ElementCollection
    @CollectionTable(name = "student_test_answers", joinColumns = @JoinColumn(name = "student_test_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "selected_answer")
    private Map<Long, String> answers;

    // --- GETTERS & SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }

    public int getWrongCount() { return wrongCount; }
    public void setWrongCount(int wrongCount) { this.wrongCount = wrongCount; }

    public int getBlankCount() { return blankCount; }
    public void setBlankCount(int blankCount) { this.blankCount = blankCount; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public LocalDateTime getDateTaken() { return dateTaken; }
    public void setDateTaken(LocalDateTime dateTaken) { this.dateTaken = dateTaken; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Test getTest() { return test; }
    public void setTest(Test test) { this.test = test; }

    public Map<Long, String> getAnswers() { return answers; }
    public void setAnswers(Map<Long, String> answers) { this.answers = answers; }
}
