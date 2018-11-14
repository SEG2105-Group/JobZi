package com.arom.jobzi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.arom.jobzi.service.Service;
import com.arom.jobzi.service.ServiceArrayAdapter;
import com.arom.jobzi.util.Util;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceAvailibility extends AppCompatActivity {

    private static final String TAG = "ServiceAvailibility";
    private TextView dateDisplayer;
    private DatePickerDialog.OnDateSetListener dateListener;
    private Button saveButton;
    private List<Service> serviceList;
    private Spinner spinnerServicesList;
    private DatabaseReference servicesDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_service);
        dateDisplayer = findViewById(R.id.dateSelector);
        dateDisplayer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ServiceAvailibility.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy: " + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                dateDisplayer.setText(date);
            }
        };


        serviceList = new ArrayList<>();
        spinnerServicesList = findViewById(R.id.spinner_serviceType);
        ServiceArrayAdapter serviceArrayAdapter = new ServiceArrayAdapter(this, serviceList);
        serviceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Util.getInstance().addServiceListListener(serviceArrayAdapter, serviceList);
        spinnerServicesList.setAdapter(serviceArrayAdapter);


        saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save (){
        Intent intent = new Intent(this, ServiceProviderActivity.class);
        startActivity(intent);
    }
}
