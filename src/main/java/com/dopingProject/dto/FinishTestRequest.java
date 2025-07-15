package com.dopingProject.dto;

import java.util.Map;

public class FinishTestRequest {
    private Long studentTestId;
    private Map<String, String> answers;

    public Long getStudentTestId() { return studentTestId; }
    public void setStudentTestId(Long studentTestId) { this.studentTestId = studentTestId; }

    public Map<String, String> getAnswers() { return answers; }
    public void setAnswers(Map<String, String> answers) { this.answers = answers; }
}
