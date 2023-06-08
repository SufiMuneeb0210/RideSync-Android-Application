package com.example.ridesync;

public class _9_ModelUserData {
    String Name;
    String Email;
    String Phone;
    String Password;
    String Gender;
    String Job;


    public _9_ModelUserData() {
    }



    public _9_ModelUserData(String name, String email, String phone, String password, String gender, String job, String status) {
        Name = name;
        Email = email;
        Phone = phone;
        Password = password;
        Gender = gender;
        Job = job;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }
}
