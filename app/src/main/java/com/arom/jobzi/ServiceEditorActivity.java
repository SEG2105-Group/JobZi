package com.arom.jobzi;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arom.jobzi.service.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class ServiceEditorActivity extends AppCompatActivity {

    public static final String SERVICE = "service";

    private static final String VALID_PRICE = "^[0-9]+";
    private static String VALID_LETTERS = "^[a-zA-Z]+";

    private EditText serviceNameTextEdit;
    private EditText serviceRateTextEdit;

    private Button save;
    private DatabaseReference accountsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_editor);

        serviceNameTextEdit = findViewById(R.id.serviceNameTextEdit);
        serviceRateTextEdit = findViewById(R.id.serviceRateTextEdit);
        save.setEnabled(true);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save.setEnabled(false);
                accountsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String name = serviceNameTextEdit.getText().toString();
                        String rate = serviceRateTextEdit.getText().toString();

                        if(name.isEmpty() || rate.isEmpty()) {
                            Toast.makeText(ServiceEditorActivity.this, "You are missing some info.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Pattern pat = Pattern.compile(VALID_PRICE);
                        if (!pat.matcher(rate).matches()) {
                            Toast.makeText(ServiceEditorActivity.this, "Email is not valid.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        pat = Pattern.compile(VALID_LETTERS);

                        if(!pat.matcher(name).matches()){
                            Toast.makeText(ServiceEditorActivity.this, "First name is not valid.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        double doubleRate = Double.parseDouble(rate);

                        Service service = new Service();

                        service.setName(name);
                        service.setRate(doubleRate);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}

