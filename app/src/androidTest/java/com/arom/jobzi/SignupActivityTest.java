package com.arom.jobzi;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
@LargeTest
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
	public void checkUserName() throws Exception {
		
		assertNotNull(suActivity.findViewById(R.id.usernameEditText));
		text = suActivity.findViewById(R.id.usernameEditText);
		text.setText("user1");
		String name = text.getText().toString();
		assertEquals("user1", name);
	}
	
	@Test
	@UiThreadTest
	public void checkPassWord() throws Exception {
		
		assertNotNull(suActivity.findViewById(R.id.passwordEditText));
		text = suActivity.findViewById(R.id.passwordEditText);
		text.setText("password123");
		String name = text.getText().toString();
		assertEquals("password123", name);
	}
	
	@Test
	@UiThreadTest
	public void checkFirstName() throws Exception {
		
		assertNotNull(suActivity.findViewById(R.id.firstNameEditText));
		text = suActivity.findViewById(R.id.firstNameEditText);
		text.setText("Miguel");
		String name = text.getText().toString();
		assertEquals("Miguel", name);
	}
	
	@Test
	@UiThreadTest
	public void checkLastName() throws Exception {
		
		assertNotNull(suActivity.findViewById(R.id.lastNameEditText));
		text = suActivity.findViewById(R.id.lastNameEditText);
		text.setText("Garzon");
		String name = text.getText().toString();
		assertEquals("Garzon", name);
	}
	
	@Test
	@UiThreadTest
	public void checkEmail() throws Exception {
		
		assertNotNull(suActivity.findViewById(R.id.emailEditText));
		text = suActivity.findViewById(R.id.lastNameEditText);
		text.setText("myemail@uottawa.ca");
		String name = text.getText().toString();
		assertEquals("myemail@uottawa.ca", name);
	}



}
