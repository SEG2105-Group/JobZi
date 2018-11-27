package com.arom.jobzi.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arom.jobzi.R;
import com.arom.jobzi.service.Availability;
import com.arom.jobzi.user.User;

import java.util.ArrayList;
import java.util.List;

public class SearchUtil extends AppCompatActivity {

    private Availability avalibility;
    private List<User> userList = new ArrayList<>();
    private List<User> userSPList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_util);

        userList = (List) Util.getInstance().getAccountsDatabase();

    }



    public void getAvailibilties(){

    }

}
