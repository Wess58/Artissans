package com.wess58.artissans.models;

public class User {
    public String id;
    public String fName;
    public String lName;
    public String userPhone;
    public String userEmail;

    public User(){


    }

    public User(String userId, String firstName, String lastName, String phone, String Email ){
        this.id = userId;
        this.fName = firstName;
        this.lName = lastName;
        this.userPhone = phone;
        this.userEmail = Email;
    }

    public String getUserId() {
        return id;
    }

    public String getFirstName() {
        return fName;
    }

    public String getLastName() {
        return lName;
    }

    public String getPhone() {
        return userPhone;
    }

    public String getEmail() {
        return userEmail;
    }
}
