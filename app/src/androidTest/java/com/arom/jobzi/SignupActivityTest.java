package com.arom.jobzi;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> suActivityTestRule = new ActivityTestRule<SignupActivity>(SignupActivity.class);
     private SignupActivity suActivity = null;
     private TextView text;


    @Before
    public void setUp() throws Exception {

        suActivity = suActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkUserName() throws Exception{

        assertNotNull( suActivity.findViewById(R.id.usernameTextView));
        text =  suActivity.findViewById(R.id.usernameTextView);
        text.setText("user1");
        String name = text.getText().toString();
        assertEquals("user1", name);
    }

    @Test
    @UiThreadTest
    public void checkPassWord() throws Exception{

        assertNotNull( suActivity.findViewById(R.id.passwordTextView));
        text =  suActivity.findViewById(R.id.passwordTextView);
        text.setText("password123");
        String name = text.getText().toString();
        assertEquals("password123", name);
    }

    @Test
    @UiThreadTest
    public void checkFirstName() throws Exception{

        assertNotNull( suActivity.findViewById(R.id.firstNameTextView));
        text =  suActivity.findViewById(R.id.firstNameTextView);
        text.setText("Miguel");
        String name = text.getText().toString();
        assertEquals("Miguel", name);
    }

    @Test
    @UiThreadTest
    public void checkLastName() throws Exception{

        assertNotNull( suActivity.findViewById(R.id.lastNameTextView));
        text =  suActivity.findViewById(R.id.lastNameTextView);
        text.setText("Garzon");
        String name = text.getText().toString();
        assertEquals("Garzon", name);
    }

    @Test
    @UiThreadTest
    public void checkEmail() throws Exception{

        assertNotNull( suActivity.findViewById(R.id.lastNameTextView));
        text =  suActivity.findViewById(R.id.lastNameTextView);
        text.setText("myemail@uottawa.ca");
        String name = text.getText().toString();
        assertEquals("myemail@uottawa.ca", name);
    }
//Miguel Garzon

}
