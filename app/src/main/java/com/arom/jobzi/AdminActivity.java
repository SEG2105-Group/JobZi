package com.arom.jobzi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.arom.jobzi.fragment.AdminPagerAdapter;
import com.arom.jobzi.fragment.ServiceListFragment;
import com.arom.jobzi.fragment.UserListFragment;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ViewPager viewPager = findViewById(R.id.viewPager);
        
        AdminPagerAdapter adminPagerAdapter = new AdminPagerAdapter(getSupportFragmentManager());

        UserListFragment userListFragment = new UserListFragment();
        ServiceListFragment serviceListFragment = new ServiceListFragment();

        adminPagerAdapter.addFragment(userListFragment, getText(R.string.users_label));
        adminPagerAdapter.addFragment(serviceListFragment, getText(R.string.services_label));

        viewPager.setAdapter(adminPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        // TODO: Move to fragment.
        FloatingActionButton addServiceFloatingButton = findViewById(R.id.addServiceFloatingButton);

        addServiceFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminActivity.this.addService();
            }
        });
        
    }

    private void addService() {

        final Service service = new Service();

        service.setName("");
        service.setRate(0);

        Util.getInstance().addService(service, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {

                    Intent toServiceEditorIntent = new Intent(AdminActivity.this, ServiceEditorActivity.class);
                    toServiceEditorIntent.putExtra(ServiceEditorActivity.SERVICE, service);
                    startActivityForResult(toServiceEditorIntent, 0);

                } else {
                    Toast.makeText(AdminActivity.this, "A new service could not be added.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
