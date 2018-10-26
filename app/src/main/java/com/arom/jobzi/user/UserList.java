package com.arom.jobzi.user;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arom.jobzi.R;

import java.util.List;

public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> users;

    public UserList(Activity context, List<User> users) {
        super(context, R.layout.user_item);

        this.context = context;
        this.users = users;

    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Nullable
    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View usernameListLayout = inflater.inflate(R.layout.user_item, null, true);

        User user = users.get(position);

        TextView usernameTextView = usernameListLayout.findViewById(R.id.usernameDisplayTextView);
        TextView emailTextView = usernameListLayout.findViewById(R.id.emailDisplayTextView);

        usernameTextView.setText(user.getUsername());
        emailTextView.setText(user.getEmail());

        return usernameListLayout;
    }

}
