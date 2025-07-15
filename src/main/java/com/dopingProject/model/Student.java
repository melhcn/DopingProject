package com.dopingProject.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String studentNo;
    private String email;
    private String password;

    @OneToMany(mappedBy = "student")
    private List<StudentTest> studentTests;

    public Student() {}

    public Student(Long id, String name, String surname, String studentNo, String email, String password) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.studentNo = studentNo;
    this.email = email;
    this.password = password;
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

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<StudentTest> getStudentTests() { return studentTests; }
    public void setStudentTests(List<StudentTest> studentTests) { this.studentTests = studentTests; }
}
