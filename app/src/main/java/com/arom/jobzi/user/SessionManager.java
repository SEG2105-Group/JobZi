package com.arom.jobzi.user;

public final class SessionManager {
    
    private static SessionManager instance;
    
    private User user;
    
    private SessionManager() {
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
    
    public static SessionManager getInstance() {
        
        if (instance == null) {
            instance = new SessionManager();
        }
        
        return instance;
        
    }
    
}
