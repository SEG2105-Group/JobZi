package com.arom.jobzi.profile;

import java.io.Serializable;

public abstract class UserProfile implements Serializable {

    private static float serialVersionUID = 1F;
    
    /**
     * Copies non-object data from the provided <code>userProfile</code> object
     * into <code>this</code>. Returns <code>true</code> when copying was successful
     * and <code>false</code> otherwise, such as when the types of the two user
     * profiles does not match.
     *
     * @param userProfile
     * @return
     */
    public abstract boolean copyFrom(UserProfile userProfile);
    
}
