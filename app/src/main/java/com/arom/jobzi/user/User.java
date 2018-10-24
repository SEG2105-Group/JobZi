package com.arom.jobzi.user;

import com.arom.jobzi.account.AccountType;

public class User {

    private String email;
    private String firstName;
    private String lastName;
    private String id;

    private AccountType accountType;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

}

