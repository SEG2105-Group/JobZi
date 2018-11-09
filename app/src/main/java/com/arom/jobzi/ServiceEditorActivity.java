package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;

import java.util.regex.Pattern;

public class ServiceEditorActivity extends AppCompatActivity {

    public static final String SERVICE_BUNDLE_ARG = "service";

    private static final Pattern VALID_PRICE_PATTERN = Pattern.compile("^[0-9]+");
    private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z]+");

    private Service service;

    private EditText serviceNameTextEdit;
    private EditText serviceRateTextEdit;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor);

        Bundle extras = getIntent().getExtras();

        service = (Service) extras.get(SERVICE_BUNDLE_ARG);

        serviceNameTextEdit = findViewById(R.id.serviceNameTextEdit);
        serviceRateTextEdit = findViewById(R.id.serviceRateTextEdit);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveButton.setEnabled(false);

                String name = serviceNameTextEdit.getText().toString();
                String rate = serviceRateTextEdit.getText().toString();

                if (name.isEmpty() || rate.isEmpty()) {
                    Toast.makeText(ServiceEditorActivity.this, "You are missing some info.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!VALID_PRICE_PATTERN.matcher(rate).matches()) {
                    Toast.makeText(ServiceEditorActivity.this, "Email is not valid.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!VALID_PATTERN.matcher(name).matches()) {
                    Toast.makeText(ServiceEditorActivity.this, "First name is not valid.", Toast.LENGTH_LONG).show();
                    return;
                }

                Util.getInstance().updateService(service);

                ServiceEditorActivity.this.finish();

            }
        });
    }
}

