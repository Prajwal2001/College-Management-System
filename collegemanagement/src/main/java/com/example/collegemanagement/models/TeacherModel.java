package com.example.collegemanagement.models;

public class TeacherModel {

    private String fName, lName, address, dob;

    public TeacherModel(String fName, String lName, String address, String dob, int salary) {
        this.fName = fName;
        this.lName = lName;
        this.address = address;
        this.dob = dob;
        this.salary = salary;
    }
    private int salary;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "fName: " + fName + "\n" +
                "lName: " + lName + "\n" +
                "address: " + address + "\n" +
                "dob: " + dob + "\n" +
                "Salary: " + salary;
    }
}
