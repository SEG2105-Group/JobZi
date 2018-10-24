package com.arom.jobzi.account;

public enum AccountType {

    ADMIN("Admin"), HOME_OWNER("A Home Owner"), SERVICE_PROVIDER("A Service Provider");

    private final String name;

    private AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
