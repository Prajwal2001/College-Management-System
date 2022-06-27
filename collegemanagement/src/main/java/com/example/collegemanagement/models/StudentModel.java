package com.example.collegemanagement.models;

public class StudentModel {

    String fName, lName, USN, address, dob;

    public StudentModel(String fName, String lName, String USN, String address, String dob) {
        this.fName = fName;
        this.lName = lName;
        this.USN = USN;
        this.address = address;
        this.dob = dob;
    }

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

    public String getUSN() {
        return USN;
    }

    public void setUSN(String USN) {
        this.USN = USN;
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

    @Override
    public String toString() {
        return "fName: " + fName + "\n" +
                "lName: " + lName + "\n" +
                "USN: " + USN + "\n" +
                "address: " + address + "\n" +
                "dob: " + dob + "\n";
    }
}
