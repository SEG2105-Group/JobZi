package com.arom.jobzi.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.profile.ServiceProviderProfile;
import com.arom.jobzi.profile.UserProfile;
import com.arom.jobzi.user.User;

import java.util.regex.Pattern;

public final class UserProfileUtil {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");
    
    private static UserProfileUtil instance;
    
    private UserProfileUtil() {
    }
    
    public static UserProfileUtil getInstance() {
        
        if (instance == null) {
            instance = new UserProfileUtil();
        }
        
        return instance;
        
    }
    
    public Class<? extends UserProfile> getClassByAccountType(AccountType accountType) {
        switch (accountType) {
            case SERVICE_PROVIDER:
                return ServiceProviderProfile.class;
            default:
                return null;
        }
    }
    
    public User createUser(String username, String email, String firstName, String lastName, AccountType accountType) {
        
        User user = new User();
    
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAccountType(accountType);
    
        return user;
        
    }
    
    public ServiceProviderProfile createServiceProviderProfile(String address, String companyName, String phoneNumber, String description, boolean licensed) {
    
        ServiceProviderProfile serviceProviderProfile = new ServiceProviderProfile();
    
        serviceProviderProfile.setAddress(address);
        serviceProviderProfile.setCompanyName(companyName);
        serviceProviderProfile.setPhoneNumber(phoneNumber);
        serviceProviderProfile.setDescription(description);
        serviceProviderProfile.setRating(RatingsUtil.DEFAULT_RATING);
        serviceProviderProfile.setLicensed(licensed);

        return serviceProviderProfile;
        
    }
    
    public ValidationResult validateUserInfo(User user, @Nullable String password) {
        
        if (user.getEmail().isEmpty()) {
            return new ValidationResult(ValidatedField.EMAIL, null);
        }
        
        // When null, do not check the password (for updating profile without changing password).
        if(password != null && password.isEmpty()) {
            return new ValidationResult(ValidatedField.PASSWORD, null);
        }
        
        if (user.getFirstName().isEmpty()) {
            return new ValidationResult(ValidatedField.FIRST_NAME, null);
        }
        
        if (user.getLastName().isEmpty()) {
            return new ValidationResult(ValidatedField.LAST_NAME, null);
        }
        
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            return new ValidationResult(null, ValidatedField.EMAIL);
        }
        
        if (!NAME_PATTERN.matcher(user.getFirstName()).matches()) {
            return new ValidationResult(null, ValidatedField.FIRST_NAME);
        }
        
        if (!NAME_PATTERN.matcher(user.getLastName()).matches()) {
            return new ValidationResult(null, ValidatedField.LAST_NAME);
        }
        
        if (user.getUserProfile() != null) {
            
            UserProfile userProfile = user.getUserProfile();
            
            if (user.getAccountType().equals(AccountType.SERVICE_PROVIDER) && userProfile instanceof ServiceProviderProfile) {
                
                return validateServiceProviderProfile((ServiceProviderProfile) userProfile);
                
            }
            
        }
        
        return new ValidationResult(null, null);
        
    }
    
    public ValidationResult validateServiceProviderProfile(ServiceProviderProfile profile) {
        
        if (profile.getAddress().isEmpty()) {
            return new ValidationResult(ValidatedField.ADDRESS, null);
        }
        
        if (profile.getPhoneNumber().isEmpty()) {
            return new ValidationResult(ValidatedField.PHONE_NUMBER, null);
        }
        
        if(profile.getCompanyName().isEmpty()) {
            return new ValidationResult(ValidatedField.COMPANY_NAME, null);
        }
        
        if (!ADDRESS_PATTERN.matcher(profile.getAddress()).matches()) {
            return new ValidationResult(null, ValidatedField.ADDRESS);
        }
        
        if (!PHONE_NUMBER_PATTERN.matcher(profile.getPhoneNumber()).matches()) {
            return new ValidationResult(null, ValidatedField.PHONE_NUMBER);
        }
        
        return new ValidationResult(null, null);
        
    }
    
    /**
     * Validates the <code>user</code> and <code>password</code> provided using {@link #validateUserInfo(User, String)}
     * <i>and</i> uses the provided <code>context</code> to display an appropriate <code>Toast</code> detailing the
     * exact error.
     *
     * @param context
     * @param user
     * @param password
     * @return <code>true</code> if the information in <code>user</code> is valid and <code>false</code> otherise.
     */
    public boolean validateUserInfoWithError(Context context, User user, String password) {
        
        ValidationResult validationResult = validateUserInfo(user, password);
        
        if (validationResult.getEmptyField() != null) {
            
            UserProfileUtil.ValidatedField emptyField = validationResult.getEmptyField();
            
            // I know this is bad. And I know there is a much better way to do this with string resources.
            String specificError;
            
            switch (emptyField) {
                case USERNAME:
                    specificError = "a username";
                    break;
    
                case PASSWORD:
                    specificError = "a password";
                    break;
                    
                case EMAIL:
                    specificError = "an email";
                    break;
                
                case FIRST_NAME:
                    specificError = "a first name";
                    break;
                
                case LAST_NAME:
                    specificError = "a last name";
                    break;
                
                case ADDRESS:
                    specificError = "an address";
                    break;
                
                case COMPANY_NAME:
                    specificError = "a company name";
                    break;
                    
                case PHONE_NUMBER:
                    specificError = "a phone number";
                    break;
                
                default:
                    specificError = emptyField.name();
                    break;
                
            }
            
            Toast.makeText(context, "Please provide " + specificError + ".", Toast.LENGTH_LONG).show();
            
            return false;
            
        }
        
        if (validationResult.getInvalidField() != null) {
            
            UserProfileUtil.ValidatedField invalidField = validationResult.getInvalidField();
            
            String specificError;
            
            switch (invalidField) {
                
                case USERNAME:
                    specificError = "username";
                    break;
                    
                case EMAIL:
                    specificError = "email";
                    break;
                
                case FIRST_NAME:
                    specificError = "first name";
                    break;
                
                case LAST_NAME:
                    specificError = "last name";
                    break;
                
                case ADDRESS:
                    specificError = "address";
                    break;
                case PHONE_NUMBER:
                    specificError = "phone number";
                    break;
                
                default:
                    specificError = invalidField.name();
                    break;
            }
            
            Toast.makeText(context, "The " + specificError + " you provided is not valid.", Toast.LENGTH_LONG).show();
            
            return false;
            
        }
        
        return true;
        
    }
    
    public enum ValidatedField {
        // General user field common to all users.
        USERNAME, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME,
        
        // Fields only found in the service provider's profile.
        ADDRESS, PHONE_NUMBER, COMPANY_NAME,
        
    }
    
    public static class ValidationResult {
        
        private final ValidatedField emptyField;
        private final ValidatedField invalidField;
        
        public ValidationResult(ValidatedField emptyField, ValidatedField invalidField) {
            this.emptyField = emptyField;
            this.invalidField = invalidField;
        }
        
        public ValidatedField getEmptyField() {
            return emptyField;
        }
        
        public ValidatedField getInvalidField() {
            return invalidField;
        }
        
    }
    
}
