package com.arom.jobzi.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.arom.jobzi.LoginActivity;
import com.arom.jobzi.LandingActivity;
import com.arom.jobzi.service.Service;
import com.arom.jobzi.user.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public final class Util {
	
	public static final String ACCOUNTS_NODE = "accounts";
	public static final String SERVICES_NODE = "services";
	
	public static final String ARG_USER = "user";
	
	private static Util instance;
	
	private FirebaseAuth firebaseAuth;
	
	private DatabaseReference accountsDatabase;
	private DatabaseReference servicesDatabase;
	
	private Util() {
	}
	
	public FirebaseUser getCurrentUser() {
		firebaseAuth = FirebaseAuth.getInstance();
		return firebaseAuth.getCurrentUser();
	}
	
	public Task<AuthResult> createUser(String email, String password) {
		
		firebaseAuth = FirebaseAuth.getInstance();
		return firebaseAuth.createUserWithEmailAndPassword(email, password);
		
	}
	
	/**
	 * This method is called when it has been detected that the user has logged in (either when starting the application with a user already logged in or when a user manually logs in).
	 * @param firebaseUser
	 */
	public void onUserLogin(final Activity activity, final FirebaseUser firebaseUser) {
		
		addSingleValueAccountsListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
				
				User user = dataSnapshot.child(firebaseUser.getUid()).getValue(User.class);
				
				if(user != null) {
					gotoLanding(activity, user);
				}
				
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			
			}
		});
		
	}
	
	public void gotoLanding(Activity activity, User user) {
		
		Intent toUserIntent = new Intent(activity, LandingActivity.class);
		
		toUserIntent.putExtra(ARG_USER, user);
		
		activity.startActivity(toUserIntent);
		activity.finish();
		
	}
	
	public void logout(Activity activity) {
		
		firebaseAuth = FirebaseAuth.getInstance();
		firebaseAuth.signOut();
		
		Intent toLoginIntent = new Intent(activity, LoginActivity.class);
		activity.startActivity(toLoginIntent);
		activity.finish();
		
	}
	
	public void addUserListListener(final BaseAdapter adapterToUpdate, final List<User> userList) {
		
		accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
		
		accountsDatabase.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				
				User user = dataSnapshot.getValue(User.class);
				userList.add(user);
				
				adapterToUpdate.notifyDataSetChanged();
				
			}
			
			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				
				User userChanged = dataSnapshot.getValue(User.class);
				
				userList.set(userList.indexOf(userChanged), userChanged);
				
				adapterToUpdate.notifyDataSetChanged();
				
			}
			
			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
				
				User userRemoved = dataSnapshot.getValue(User.class);
				
				userList.remove(userRemoved);
				
				adapterToUpdate.notifyDataSetChanged();
				
			}
			
			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
			
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			
			}
		});
		
	}
	
	public void addServiceListListener(final BaseAdapter adapterToUpdate, final List<Service> serviceList) {
		
		servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
		
		servicesDatabase.addChildEventListener(new ChildEventListener() {
			
			@Override
			public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				
				Service service = dataSnapshot.getValue(Service.class);
				serviceList.add(service);
				
				adapterToUpdate.notifyDataSetChanged();
				
			}
			
			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
				
				Service serviceChanged = dataSnapshot.getValue(Service.class);
				
				serviceList.set(serviceList.indexOf(serviceChanged), serviceChanged);
				
				adapterToUpdate.notifyDataSetChanged();
				
			}
			
			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
				
				Service serviceRemoved = dataSnapshot.getValue(Service.class);
				
				serviceList.remove(serviceRemoved);
				
				adapterToUpdate.notifyDataSetChanged();
				
			}
			
			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
			
			}
			
			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {
			
			}
		});
		
	}
	
	public void deleteService(Service service) {
		servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
		servicesDatabase.child(service.getId()).removeValue();
	}
	
	public void addSingleValueAccountsListener(ValueEventListener accountsListener) {
		
		accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
		accountsDatabase.addListenerForSingleValueEvent(accountsListener);
		
	}
	
	public void updateUser(User user) {
		
		accountsDatabase = FirebaseDatabase.getInstance().getReference().child(ACCOUNTS_NODE);
		accountsDatabase.child(user.getId()).setValue(user);
		
	}
	
	public void updateService(Service service) {
		
		servicesDatabase = FirebaseDatabase.getInstance().getReference().child(SERVICES_NODE);
		
		if (service.getId() == null) {
			service.setId(servicesDatabase.push().getKey());
		}
		
		servicesDatabase.child(service.getId()).setValue(service);
		
	}
	
	public static Util getInstance() {
		
		if (instance == null) {
			instance = new Util();
		}
		
		return instance;
	}

	public List<String> getServiceList() {
		List<String> serviceList = new ArrayList<>();
		serviceList.add((servicesDatabase.child(SERVICES_NODE).child("name")).toString());
		return serviceList;
	}


}
