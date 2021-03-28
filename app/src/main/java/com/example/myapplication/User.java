package com.example.myapplication;

public class User {
    public static User currentUser;
    private int id;
    private String username;
    private String fullName;
    private boolean isDiagnostic;
    private int age;

    public User(int id, String username, String fullName, boolean isDiagnostic, int age){
        this.id=id;
        this.username=username;
        this.fullName=fullName;
        this.isDiagnostic=isDiagnostic;
        this.age=age;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean isDiagnostic() {
        return isDiagnostic;
    }

    public int getAge() {
        return age;
    }




}
