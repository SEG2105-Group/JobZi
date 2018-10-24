package com.arom.jobzi;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Type type;

    public User(String firstName, String lastName, String email, String address, String password,
                Type type){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.password = address;
        this.type = type;
    }

    public String getUserName (){
        return firstName + " " + lastName;
    }

}
