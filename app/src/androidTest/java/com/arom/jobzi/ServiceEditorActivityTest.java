package com.arom.jobzi;

import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.ActivityTestRule;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ServiceEditorActivityTest {


    @Rule
    public ActivityTestRule<ServiceEditorActivity> suActivityTestRule = new ActivityTestRule<ServiceEditorActivity>(ServiceEditorActivity.class);
    private ServiceEditorActivity suActivity = null;
    private TextView text;

    @Before
    public void setUp() throws Exception {

        suActivity = suActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkServiceName() throws Exception {

        assertNotNull(suActivity.findViewById(R.id.serviceNameTextEdit));
        text = suActivity.findViewById(R.id.serviceNameTextEdit);
        text.setText("Carpentry");
        String name = text.getText().toString();
        assertEquals("Carpentry", name);
    }

    @Test
    @UiThreadTest
    public void checkserviceRate() throws Exception {

        assertNotNull(suActivity.findViewById(R.id.serviceRateTextEdit));
        text = suActivity.findViewById(R.id.serviceRateTextEdit);
        text.setText("50");
        String name = text.getText().toString();
        assertEquals("50", name);
    }


}