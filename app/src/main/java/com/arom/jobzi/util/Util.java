package com.arom.jobzi.util;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.arom.jobzi.AdminActivity;
import com.arom.jobzi.HomeOwnerActivity;
import com.arom.jobzi.ServiceProviderActivity;
import com.arom.jobzi.user.User;

public final class Util {

    public static final String ARG_USER = "user";

    private static Util instance;

    private Util() {}

    public void gotoLanding(Activity activity, User user) {

        Class<?> landingActivity = null;

        switch(user.getAccountType()) {
            case ADMIN:
                landingActivity = AdminActivity.class;
                break;
            case HOME_OWNER:
                landingActivity = HomeOwnerActivity.class;
                break;
            case SERVICE_PROVIDER:
                landingActivity = ServiceProviderActivity.class;
                break;
            default:
                Toast.makeText(activity, "The account type does not have a corresponding activity.", Toast.LENGTH_LONG).show();
                return;
        }

        Intent toLandingIntent = new Intent(activity, landingActivity);

        toLandingIntent.putExtra(ARG_USER, user);

        activity.startActivity(toLandingIntent);
        activity.finish();

    }

    public static Util getInstance() {

        if(instance == null) {
            instance = new Util();
        }

        return instance;

    }

}
