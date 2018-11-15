package com.arom.jobzi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AvailibilitiesPopupActivity extends AppCompatActivity {
    private Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availibilities_popup);

        addButton = findViewById(R.id.popupAddButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AvailibilitiesPopupActivity.this, AvailableTimeSlotEditorActivity.class);
            }
        });

    }
}
