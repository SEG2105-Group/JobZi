package com.arom.jobzi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arom.jobzi.util.AccountsManager;

public class MainActivity extends AppCompatActivity {

    private AccountsManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
