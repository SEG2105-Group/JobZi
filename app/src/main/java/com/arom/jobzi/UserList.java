package com.arom.jobzi;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> users;

    public UserList(Activity context, List<User> users) {
        super(context, R.layout.username_list);

        this.context = context;
        this.users = users;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View usernameListLayout = inflater.inflate(R.layout.username_list, null, true);

        User user = users.get(position);

        // TODO: Add user information here and have where it is to be set in the username_list layout file.

        return usernameListLayout;

    }
}
