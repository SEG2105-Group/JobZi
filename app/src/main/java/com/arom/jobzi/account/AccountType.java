package com.arom.jobzi.account;

public enum AccountType {

    ADMIN("Admin"), HOME_OWNER("Home Owner"), SERVICE_PROVIDER("Service Provider");

    private final String name;

    private AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
