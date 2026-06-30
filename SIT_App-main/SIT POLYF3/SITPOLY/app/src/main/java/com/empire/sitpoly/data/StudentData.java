package com.empire.sitpoly.data;

public class StudentData {
    private String name, enrollNo,classTeacher,department, verification, year;

    public StudentData() {
    }

    public StudentData(String name, String enrollNo, String classTeacher, String department, String verification, String year) {
        this.name = name;
        this.enrollNo = enrollNo;
        this.classTeacher = classTeacher;
        this.department = department;
        this.verification = verification;
        this.year = year;
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

    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
