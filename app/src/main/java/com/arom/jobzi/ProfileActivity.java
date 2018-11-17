package com.arom.jobzi;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.arom.jobzi.fragment.ProfileFragment;
import com.arom.jobzi.user.User;
import com.arom.jobzi.util.Util;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();

        User user = (User) bundle.get(Util.USER_BUNDLE_ARG);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.extraProfileInformationFragment, ProfileFragment.newInstance(user)).commit();

    }

}
