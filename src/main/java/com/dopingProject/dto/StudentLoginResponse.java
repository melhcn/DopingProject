package com.dopingProject.dto;

public class StudentLoginResponse {
    private Long id;
    private String name;
    private String surname;
    private String studentNo;
    private String email;

    public StudentLoginResponse() {}

    public StudentLoginResponse(Long id, String name, String surname, String studentNo, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.studentNo = studentNo;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
