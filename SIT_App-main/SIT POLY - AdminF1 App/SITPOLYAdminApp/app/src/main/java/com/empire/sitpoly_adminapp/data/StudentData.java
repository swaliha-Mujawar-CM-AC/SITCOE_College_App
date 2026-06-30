package com.empire.sitpoly_adminapp.data;

public class StudentData {

    private String name, enrollNo, department, year, classTeacher, verification, studentId;

    public StudentData() {
    }

    public StudentData(String name, String enrollNo, String department, String year, String classTeacher, String verification, String studentId) {
        this.name = name;
        this.enrollNo = enrollNo;
        this.department = department;
        this.year = year;
        this.classTeacher = classTeacher;
        this.verification = verification;
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnrollNo() {
        return enrollNo;
    }

    public void setEnrollNo(String enrollNo) {
        this.enrollNo = enrollNo;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}