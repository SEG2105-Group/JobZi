package com.arom.jobzi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;
import com.google.firebase.database.DatabaseReference;

import java.util.regex.Pattern;

public class ServiceEditorActivity extends AppCompatActivity {
	
	public static final String SERVICE_BUNDLE_ARG = "service";
	/**
	 * This is used to store a boolean. When the boolean is true, this activity
	 * will behave as though the service is newly being added (as opposed to editing
	 * an existing service, which is the case when the boolean is false).
	 */
	public static final String NEW_SERVICE_MODE_BUNDLE_ARG = "edit_or_add";
	
	private static final Pattern SERVICE_NAME_PATTERN = Pattern.compile("^[a-zA-Z ]+");
	
	private Service service;

	private Button saveButton;

	private EditText serviceNameTextEdit;
	private EditText serviceRateTextEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_editor);
		
		Bundle extras = getIntent().getExtras();
		
		service = (Service) extras.getSerializable(SERVICE_BUNDLE_ARG);

		TextView serviceBannerTextView = findViewById(R.id.serviceBannerTextView);

		if (extras.getBoolean(NEW_SERVICE_MODE_BUNDLE_ARG)) {
			serviceBannerTextView.setText(getString(R.string.add_service_label));
        } else {
			serviceBannerTextView.setText(getString(R.string.update_service_label, service.getName()));
		}
		
		saveButton = findViewById(R.id.saveButton);
		serviceNameTextEdit = findViewById(R.id.serviceNameTextEdit);
		serviceRateTextEdit = findViewById(R.id.serviceRateTextEdit);
		
		serviceNameTextEdit.setText(service.getName());
		
		if (service.getRate() > 0) {
			serviceRateTextEdit.setText(String.format("%.2f", service.getRate()));
		}
		
		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				saveButton.setEnabled(false);

				ServiceEditorActivity.this.updateService();

				saveButton.setEnabled(true);

			}
		});
		
		Button cancelButton = findViewById(R.id.cancelButton);
		
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	private void updateService() {

        String name = serviceNameTextEdit.getText().toString();
        String rate = serviceRateTextEdit.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(ServiceEditorActivity.this, "Please enter a name for this service.", Toast.LENGTH_LONG).show();
            return;
        }

        if (rate.isEmpty()) {
            Toast.makeText(ServiceEditorActivity.this, "Please enter a rate for this service.", Toast.LENGTH_LONG).show();
            return;
        }

        double rateDouble;

        try {
            rateDouble = Double.parseDouble(rate);
        } catch (NumberFormatException ex) {
            Toast.makeText(ServiceEditorActivity.this, "Please make sure the rate is a decimal number.", Toast.LENGTH_LONG).show();
            return;
        }

        if (rateDouble <= 0) {
            Toast.makeText(ServiceEditorActivity.this, "Please make sure the rate is some price greater than zero.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!SERVICE_NAME_PATTERN.matcher(name).matches()) {
            Toast.makeText(ServiceEditorActivity.this, "Service name is not valid; must only include letters and spaces.", Toast.LENGTH_LONG).show();
            return;
        }

        service.setName(name);
        service.setRate(rateDouble);

		DatabaseReference servicesDatabase = Util.getInstance().getServicesDatabase();
		servicesDatabase.child(service.getId()).setValue(service);
        
        ServiceEditorActivity.this.finish();

    }
}

